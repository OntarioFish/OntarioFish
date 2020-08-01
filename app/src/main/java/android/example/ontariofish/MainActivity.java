package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private Button mFishInfoButton, mViewRegsButton ,mMapsButton, mLogbookButton;

    private List<RegulationSample> regulationSamples= new ArrayList<>();
    private List<ExceptionSample> exceptionSamples= new ArrayList<>();

    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.statusBarColor));

        //Database object is initialized - see DatabaseHelper class for functions
        MyDb = new DatabaseHelper(this);

        mFishInfoButton = (Button)findViewById(R.id.view_fish);
        mMapsButton = (Button)findViewById(R.id.view_map);
        mViewRegsButton = (Button)findViewById(R.id.regulations_button);
        mLogbookButton = (Button)findViewById(R.id.logbook_button);


        /* Creates a test Array to see if the database has already been created,
         in order to avoid needless processing */
        String[] checker = MyDb.getRegulationsInfo("1", "Brook Trout");
        if (checker[0].equals("0")) {
            readDataFishInfo();
        }

        String[] checker2 = MyDb.getExceptionsInfo("2", "Brook Trout", "Blue Lake");
        if (checker2[0].equals("0")) {
            readDataFishExceptions();
        }


        mFishInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FishInfo.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        mViewRegsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FishRegulations.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapStart = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapStart);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        mLogbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Logbook.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }


    /* Function reads lines of data from speciesregulations.txt, which can be found in the "raw" folder
    under the resource files folder */
    public void readDataFishInfo(){

        InputStream is = getResources().openRawResource(R.raw.speciesregulations);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        //Reads the whole file, and adds each line to an object belonging to the FishRegulation class
        try{
            while((line = reader.readLine()) != null){

                String[] tokens = line.split("//");

                RegulationSample sample = new RegulationSample();
                sample.setRegion(tokens[0]);
                sample.setName(tokens[1]);
                sample.setSeason(tokens[2]);
                sample.setLimits(tokens[3]);

                regulationSamples.add(sample);

            }
        } catch (IOException e){
            e.printStackTrace();
        }

        //Separates the object's attributes into strings, which are inserted into the "FishInfo" table
        for (int i = 0; i < regulationSamples.size(); i++){

            String currentRegion = regulationSamples.get(i).getRegion();
            String currentName = regulationSamples.get(i).getName();
            String currentSeason = regulationSamples.get(i).getSeason();
            String currentLimits = regulationSamples.get(i).getLimits();

            MyDb.insertDataFishRegulations(currentRegion, currentName, currentSeason, currentLimits);
        }
    }

    public void readDataFishExceptions(){

        InputStream is = getResources().openRawResource(R.raw.speciesexceptions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        //Reads the whole file, and adds each line to an object belonging to the FishRegulation class
        try{
            while((line = reader.readLine()) != null){

                String[] tokens = line.split("//");

                ExceptionSample sample = new ExceptionSample();
                sample.setRegion(tokens[0]);
                sample.setName(tokens[1]);
                sample.setLake(tokens[2]);
                sample.setInfo(tokens[3]);
                sample.setSeason(tokens[4]);
                sample.setLimits(tokens[5]);

                exceptionSamples.add(sample);

            }
        } catch (IOException e){
            e.printStackTrace();
        }

        //Separates the object's attributes into strings, which are inserted into the "FishInfo" table
        for (int i = 0; i < exceptionSamples.size(); i++){

            String currentRegion = exceptionSamples.get(i).getRegion();
            String currentName = exceptionSamples.get(i).getName();
            String currentLake = exceptionSamples.get(i).getLake();
            String currentInfo = exceptionSamples.get(i).getInfo();
            String currentSeason = exceptionSamples.get(i).getSeason();
            String currentLimits = exceptionSamples.get(i).getLimits();

            MyDb.insertDataFishExceptions(currentRegion, currentName, currentLake, currentInfo, currentSeason, currentLimits);
        }

    }

}
