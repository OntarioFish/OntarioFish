package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FishRegulations extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private TextView zoneTitle, zoneSeasonInfo, zoneLimitInfo, exceptionInfo,exceptionLocation;
    private Button helpButton;
    private Button favouriteButton;
    private ScrollView exceptionLayout;
    private Spinner fishSelect;
    private Fish currentFish;
    private FloatingActionButton fabFish;
    private AutoCompleteTextView lakeList;
    private int regionNumber;
    private String[] fishInfo = new String[2];
    private String[] lakeException = new String[3];
    private List<String> listLake = new ArrayList<>();
    private DatabaseHelper DB;

    public static final String SHARED_PREFS = "favoriteZonesPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_regulations);

        //Stores fish names based on region selected. Used for the fish spinner dropdown list
        List<String> regulationFish= new ArrayList<>();
        DB = new DatabaseHelper(this);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));
        Toast.makeText(this, "Select a fish from dropdown menu!", Toast.LENGTH_SHORT).show();

        Bundle extras = getIntent().getExtras();
        final String zoneName = extras.getString("ZONE");
        favouriteButton = (Button) findViewById(R.id.favourite_button);
        helpButton = (Button) findViewById(R.id.help_button_regulation);
        exceptionLocation = (TextView) findViewById(R.id.exception_location);
        exceptionLayout = (ScrollView) findViewById(R.id.scroll_exception);
        lakeList = (AutoCompleteTextView) findViewById(R.id.lake_list);
        zoneTitle = (TextView)findViewById(R.id.zone_title);
        fishSelect = (Spinner)findViewById(R.id.fish_spinner);
        zoneLimitInfo = (TextView)findViewById(R.id.zone_limit);
        zoneSeasonInfo = (TextView)findViewById(R.id.zone_season);
        fabFish = (FloatingActionButton) findViewById(R.id.fab_fish_info);
        exceptionInfo = (TextView) findViewById(R.id.exception_info);
        regulationFish = setTitle(zoneName);
        exceptionLayout.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regulationFish);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fishSelect.setAdapter(adapter);
        fishSelect.setOnItemSelectedListener(this);
        lakeList.setThreshold(1);
        lakeList.setOnItemClickListener(this);

        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final Boolean favouriteSelected = sharedPreferences.getBoolean(zoneName, false);
        if(favouriteSelected)
            favouriteButton.setBackground(getDrawable(R.drawable.ic_baseline_star));


        fabFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fishName = currentFish.getResourceName();

                if(fishName.equals("sunfish")) {
                    Intent intent = new Intent(FishRegulations.this, FishInfo.class);
                    intent.putExtra("SOURCE", "sunfish");
                    startActivity(intent);

                }else if(fishName.equals("crappie")) {
                    Intent intent = new Intent(FishRegulations.this, FishInfo.class);
                    intent.putExtra("SOURCE", "crappie");
                    startActivity(intent);

                }else if (fishName.equals("pacific_salmon")){
                    Intent intent = new Intent(FishRegulations.this, FishInfo.class);
                    intent.putExtra("SOURCE", "pacific_salmon");
                    startActivity(intent);

                } else {
                    System.out.println("FAB clicked");
                    System.out.println(fishName);
                    Intent intent = new Intent(FishRegulations.this, FishDetails.class);
                    intent.putExtra("FISHES", currentFish);
                    startActivity(intent);
                }
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder help = new AlertDialog.Builder(FishRegulations.this);
                help.setTitle("Regulations Help");
                help.setMessage(R.string.regulation_alert_dialog);
                help.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                help.show();
            }
        });

        favouriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(favouriteSelected){
                    favouriteButton.setBackground(getDrawable(R.drawable.ic_baseline_star_border));
                    sharedPreferences.edit().remove(zoneName).apply();

                } else {
                    favouriteButton.setBackground(getDrawable(R.drawable.ic_baseline_star));
                    sharedPreferences.edit().putBoolean(zoneName, true).apply();
                }
            }
        });

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lakeException = DB.getExceptionsInfo(Integer.toString(regionNumber), currentFish.getName(), (String)parent.getItemAtPosition(position));
        exceptionLocation.setText(String.format("%s: %s", parent.getItemAtPosition(position), lakeException[0]));
        if(lakeException[2].equals("Unchanged")){
            exceptionInfo.setText(String.format("%s", lakeException[1]));
        } else {
            exceptionInfo.setText(String.format("%s\n\n%s", lakeException[1], lakeException[2]));
        }

        exceptionLayout.setVisibility(View.VISIBLE);

        closeKeyboard();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerClick(parent, view, position);
        }

        //first position is the season, second position is the limits



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Handles actions in the even a fish is selected from the dropdown.
    public void onSpinnerClick(AdapterView<?> parent, View view, int position){
        fishInfo = DB.getRegulationsInfo(Integer.toString(regionNumber), (String)parent.getItemAtPosition(position));
        zoneLimitInfo.setText(fishInfo[1]);
        zoneSeasonInfo.setText(fishInfo[0]);

        String resourceName = (String)parent.getItemAtPosition(position);
        resourceName = resourceName.toLowerCase();
        resourceName = resourceName.replaceAll("\\s", "_");
        currentFish = new Fish((String)parent.getItemAtPosition(position), resourceName);
        System.out.println(currentFish.getResourceName());
        listLake = DB.getExceptionsLake(Integer.toString(regionNumber), (String)parent.getItemAtPosition(position));
        lakeList.getText().clear();
        exceptionLayout.setVisibility(View.INVISIBLE);


        if(listLake.isEmpty()){
            lakeList.setVisibility(View.INVISIBLE);
            exceptionLayout.setVisibility(View.INVISIBLE);
        } else {
            if(lakeList.getVisibility() == View.INVISIBLE)
                lakeList.setVisibility(View.VISIBLE);

            ArrayAdapter<String> lake = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listLake);
            lakeList.setAdapter(lake);
        }
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public List<String> setTitle(String zoneName){

        List<String> regulationFishTemp= new ArrayList<>();
        switch(zoneName){
            case "#zone1":
                zoneTitle.setText("Zone 1");
                regulationFishTemp = DB.getRegulationsFish("1");
                regionNumber = 1;
                break;
            case "#zone2":
                zoneTitle.setText("Zone 2");
                regulationFishTemp = DB.getRegulationsFish("2");
                regionNumber = 2;
                break;
            case "#zone3":
                zoneTitle.setText("Zone 3");
                regulationFishTemp = DB.getRegulationsFish("3");
                regionNumber = 3;
                break;
            case "#zone4":
                zoneTitle.setText("Zone 4");
                regulationFishTemp = DB.getRegulationsFish("4");
                regionNumber = 4;
                break;
            case "#zone5":
                zoneTitle.setText("Zone 5");
                regulationFishTemp = DB.getRegulationsFish("5");
                regionNumber = 5;
                break;
            case "#zone6":
                zoneTitle.setText("Zone 6");
                regulationFishTemp = DB.getRegulationsFish("6");
                regionNumber = 6;
                break;
            case "#zone7":
                zoneTitle.setText("Zone 7");
                regulationFishTemp = DB.getRegulationsFish("7");
                regionNumber = 7;
                break;
            case "#zone8":
                zoneTitle.setText("Zone 8");
                regulationFishTemp = DB.getRegulationsFish("8");
                regionNumber = 8;
                break;
            case "#zone9":
                zoneTitle.setText("Zone 9");
                regulationFishTemp = DB.getRegulationsFish("9");
                regionNumber = 9;
                break;
            case "#zone10":
                zoneTitle.setText("Zone 10");
                regulationFishTemp = DB.getRegulationsFish("10");
                regionNumber = 10;
                break;
            case "#zone11":
                zoneTitle.setText("Zone 11");
                regulationFishTemp = DB.getRegulationsFish("11");
                regionNumber = 11;
                break;
            case "#zone12":
                zoneTitle.setText("Zone 12");
                regulationFishTemp = DB.getRegulationsFish("12");
                regionNumber = 12;
                break;
            case "#zone13":
                zoneTitle.setText("Zone 13");
                regulationFishTemp = DB.getRegulationsFish("13");
                regionNumber = 13;
                break;
            case "#zone14":
                zoneTitle.setText("Zone 14");
                regulationFishTemp = DB.getRegulationsFish("14");
                regionNumber = 14;
                break;
            case "#zone15":
                zoneTitle.setText("Zone 15");
                regulationFishTemp = DB.getRegulationsFish("15");
                regionNumber = 15;
                break;
            case "#zone16":
                zoneTitle.setText("Zone 16");
                regulationFishTemp = DB.getRegulationsFish("16");
                regionNumber = 16;
                break;
            case "#zone17":
                zoneTitle.setText("Zone 17");
                regulationFishTemp = DB.getRegulationsFish("17");
                regionNumber = 17;
                break;
            case "#zone18":
                zoneTitle.setText("Zone 18");
                regulationFishTemp = DB.getRegulationsFish("18");
                regionNumber = 18;
                break;
            case "#zone19":
                zoneTitle.setText("Zone 19");
                regulationFishTemp = DB.getRegulationsFish("19");
                regionNumber = 19;
                break;
            case "#zone20":
                zoneTitle.setText("Zone 20");
                regulationFishTemp = DB.getRegulationsFish("20");
                regionNumber = 20;
                break;
        }

        return regulationFishTemp;
    }


}
