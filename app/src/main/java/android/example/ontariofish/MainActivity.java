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


public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private Button mFishInfoButton, mViewRegsButton,mMapsButton;

    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDb = new DatabaseHelper(this);

        mFishInfoButton = (Button)findViewById(R.id.view_fish);
        mMapsButton = (Button)findViewById(R.id.view_map);
        mVideoView = (VideoView) findViewById(R.id.background_video);
        mViewRegsButton = (Button) findViewById(R.id.regulations_button);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));

        Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.output);
        mVideoView.setVideoURI(uri);

        MyDb.insertDataFishInfo("dog", "cat");


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


}
