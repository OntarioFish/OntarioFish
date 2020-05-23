package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private Button mFishInfoButton, mViewRegsButton,mMapsButton;

    public List<FishSample> fishSamples= new ArrayList<>();

    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database object is initialized - see DatabaseHelper class for functions
        MyDb = new DatabaseHelper(this);

        mFishInfoButton = (Button)findViewById(R.id.view_fish);
        mMapsButton = (Button)findViewById(R.id.view_map);
        mVideoView = (VideoView) findViewById(R.id.background_video);
        mViewRegsButton = (Button) findViewById(R.id.regulations_button);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));

        Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.output);
        mVideoView.setVideoURI(uri);

        /* Creates a test Array to see if the database has already been created,
         in order to avoid needless processing */
        String[] checker = MyDb.getInfo("Walleye");
        if (checker[0].equals("0")) {
            readDataFishInfo();
        }

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0,0);
            }
        });

        mFishInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FishInfo.class));
            }
        });

        mViewRegsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FishRegulations.class));
            }
        });
        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    /* Function reads lines of data from fishinfo.txt, which can be found in the "raw" folder
    under the resource files folder */
    public void readDataFishInfo(){

        InputStream is = getResources().openRawResource(R.raw.fishinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        //Reads the whole file, and adds each line to an object belonging to the FishSample class
        try{
            while((line = reader.readLine()) != null){

                String[] tokens = line.split("//");

                FishSample sample = new FishSample();
                sample.setName(tokens[0]);
                sample.setDescription(tokens[1]);
                sample.setAppearance(tokens[2]);
                sample.setSize(tokens[3]);
                sample.setHabran(tokens[4]);

                fishSamples.add(sample);

            }
        } catch (IOException e){
            e.printStackTrace();
        }

        //Separates the object's attributes into strings, which are inserted into the "FishInfo" table
        for (int i = 0; i < fishSamples.size(); i++){

            String currentName = fishSamples.get(i).getName();
            String currentOverview = fishSamples.get(i).getDescription();
            String currentAppearance = fishSamples.get(i).getAppearance();
            String currentSize = fishSamples.get(i).getSize();
            String currentHabran = fishSamples.get(i).getHabran();

            MyDb.insertDataFishInfo(currentName, currentOverview, currentAppearance, currentSize, currentHabran);
        }

    }

}
