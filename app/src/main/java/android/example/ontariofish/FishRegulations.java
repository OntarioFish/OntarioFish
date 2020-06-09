package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FishRegulations extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private TextView zoneTitle, zoneSeasonInfo, zoneLimitInfo, exceptionInfo,exceptionLocation;
    private LinearLayout exceptionLayout;
    private Spinner fishSelect;
    private Fish currentFish;
    private FloatingActionButton fabFish;
    private AutoCompleteTextView lakeList;
    private int regionNumber;
    private String[] fishInfo = new String[2];
    private String[] lakeException = new String[3];
    private List<String> listLake = new ArrayList<>();

    private DatabaseHelper DB;

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
        String zoneName = extras.getString("ZONE");
        exceptionLocation = (TextView) findViewById(R.id.exception_location);
        exceptionLayout = (LinearLayout) findViewById(R.id.exception_layout);
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
        fabFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAB clicked");
                System.out.println(currentFish.getResourceName());
                Intent intent = new Intent(FishRegulations.this, FishDetails.class);
                intent.putExtra("FISHES", currentFish);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("HELLo");
        lakeException = DB.getExceptionsInfo(Integer.toString(regionNumber), currentFish.getName(), (String)parent.getItemAtPosition(position));
        exceptionLocation.setText(String.format("%s: %s", parent.getItemAtPosition(position), lakeException[0]));
        if(lakeException[2].equals("Unchanged")){
            exceptionInfo.setText(String.format("%s\n\n", lakeException[1]));
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
        currentFish = new Fish((String)parent.getItemAtPosition(position), 0, resourceName);
        System.out.println(currentFish.getResourceName());
        listLake = DB.getExceptionsLake(Integer.toString(regionNumber), (String)parent.getItemAtPosition(position));
        lakeList.getText().clear();



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
