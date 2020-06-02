package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FishRegulations extends AppCompatActivity {

    private TextView zoneTitle;
    private Spinner fishSelect;

    DatabaseHelper DB;

    public List<String> regulationFish= new ArrayList<>();
    public List<String> regulationFishTemp= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_regulations);

        DB = new DatabaseHelper(this);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));

        Bundle extras = getIntent().getExtras();
        String zoneName = extras.getString("ZONE");

        zoneTitle = (TextView)findViewById(R.id.zone_title);
        fishSelect = (Spinner)findViewById(R.id.fish_spinner);
        regulationFish = setTitle(zoneName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fish_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fishSelect.setAdapter(adapter);

    }

    public List<String> setTitle(String zoneName){
        switch(zoneName){
            case "#zone1":
                zoneTitle.setText("Zone 1");
                regulationFishTemp = DB.getRegulationsFish("1");
                break;
            case "#zone2":
                zoneTitle.setText("Zone 2");
                regulationFishTemp = DB.getRegulationsFish("2");
                break;
            case "#zone3":
                zoneTitle.setText("Zone 3");
                regulationFishTemp = DB.getRegulationsFish("3");
                break;
            case "#zone4":
                zoneTitle.setText("Zone 4");
                regulationFishTemp = DB.getRegulationsFish("4");
                break;
            case "#zone5":
                zoneTitle.setText("Zone 5");
                regulationFishTemp = DB.getRegulationsFish("5");
                break;
            case "#zone6":
                zoneTitle.setText("Zone 6");
                regulationFishTemp = DB.getRegulationsFish("6");
                break;
            case "#zone7":
                zoneTitle.setText("Zone 7");
                regulationFishTemp = DB.getRegulationsFish("7");
                break;
            case "#zone8":
                zoneTitle.setText("Zone 8");
                regulationFishTemp = DB.getRegulationsFish("8");
                break;
            case "#zone9":
                zoneTitle.setText("Zone 9");
                regulationFishTemp = DB.getRegulationsFish("9");
                break;
            case "#zone10":
                zoneTitle.setText("Zone 10");
                regulationFishTemp = DB.getRegulationsFish("10");
                break;
            case "#zone11":
                zoneTitle.setText("Zone 11");
                regulationFishTemp = DB.getRegulationsFish("11");
                break;
            case "#zone12":
                zoneTitle.setText("Zone 12");
                regulationFishTemp = DB.getRegulationsFish("12");
                break;
            case "#zone13":
                zoneTitle.setText("Zone 13");
                regulationFishTemp = DB.getRegulationsFish("13");
                break;
            case "#zone14":
                zoneTitle.setText("Zone 14");
                regulationFishTemp = DB.getRegulationsFish("14");
                break;
            case "#zone15":
                zoneTitle.setText("Zone 15");
                regulationFishTemp = DB.getRegulationsFish("15");
                break;
            case "#zone16":
                zoneTitle.setText("Zone 16");
                regulationFishTemp = DB.getRegulationsFish("16");
                break;
            case "#zone17":
                zoneTitle.setText("Zone 17");
                regulationFishTemp = DB.getRegulationsFish("17");
                break;
            case "#zone18":
                zoneTitle.setText("Zone 18");
                regulationFishTemp = DB.getRegulationsFish("18");
                break;
            case "#zone19":
                zoneTitle.setText("Zone 19");
                regulationFishTemp = DB.getRegulationsFish("19");
                break;
            case "#zone20":
                zoneTitle.setText("Zone 20");
                regulationFishTemp = DB.getRegulationsFish("20");
                break;
        }

        return regulationFishTemp;
    }
}
