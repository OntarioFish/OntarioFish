package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private List<RegulationSample> regulationSamples= new ArrayList<>();
    private List<ExceptionSample> exceptionSamples= new ArrayList<>();
    private List<WaterbodyExceptionSample> waterbodySamples = new ArrayList<>();
    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Database object is initialized - see DatabaseHelper class for functions
        MyDb = new DatabaseHelper(this);

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

        String[] checker3 = MyDb.getExceptionsInfo("2", "Brook Trout", "Blue Lake");
        if (checker2[0].equals("0")) {
            readWaterbodyExceptions();
        }

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
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

    public void readWaterbodyExceptions(){

        InputStream is = getResources().openRawResource(R.raw.exceptionswaterbody);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        try{
            while((line = reader.readLine()) != null){

                String[] tokens = line.split("//");

                WaterbodyExceptionSample sample = new WaterbodyExceptionSample();
                sample.setInfo1(tokens[0]);
                sample.setInfo2(tokens[1]);
                sample.setInfo3(tokens[2]);
                sample.setInfo4(tokens[3]);
                sample.setInfo5(tokens[4]);
                sample.setInfo6(tokens[5]);
                sample.setInfo7(tokens[6]);
                sample.setInfo8(tokens[7]);
                sample.setInfo9(tokens[8]);
                sample.setInfo10(tokens[9]);
                sample.setInfo11(tokens[10]);
                sample.setInfo11(tokens[11]);
                sample.setInfo11(tokens[12]);
                sample.setInfo11(tokens[13]);
                sample.setInfo11(tokens[14]);

                waterbodySamples.add(sample);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(WaterbodyExceptionSample sample : waterbodySamples){
            String info1 = sample.getInfo1();
            String info2 = sample.getInfo2();
            String info3 = sample.getInfo3();
            String info4 = sample.getInfo4();
            String info5 = sample.getInfo5();
            String info6 = sample.getInfo6();
            String info7 = sample.getInfo7();
            String info8 = sample.getInfo8();
            String info9 = sample.getInfo9();
            String info10 = sample.getInfo10();
            String info11 = sample.getInfo11();
            String info12 = sample.getInfo12();
            String info13 = sample.getInfo13();
            String info14 = sample.getInfo14();
            String info15 = sample.getInfo15();

            MyDb.insertDataWaterbodyExceptions(info1, info2, info3, info4, info5, info6, info7, info8, info9, info10, info11, info12, info13, info14, info15);


        }
    }
}
