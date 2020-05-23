package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FishDetails extends AppCompatActivity {

    private TextView title, overview, fishAppearance, fishSize, fishHabitat;
    private ImageView fishPhoto, fishRange;

    DatabaseHelper FishDetailsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

        //Database object is initialized - see DatabaseHelper class for functions
        FishDetailsDB = new DatabaseHelper(this);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));

        overview = (TextView) findViewById(R.id.fish_overview);
        title = (TextView) findViewById(R.id.title_main);
        fishPhoto = (ImageView) findViewById(R.id.fish_picture);
        fishAppearance = (TextView) findViewById(R.id.fish_appearance);
        fishSize = (TextView) findViewById(R.id.fish_size);
        fishRange = (ImageView) findViewById(R.id.fish_range);
        fishHabitat = (TextView) findViewById(R.id.fish_habitat);

        Bundle data = getIntent().getExtras();
        Fish fish = (Fish)data.getParcelable("FISHES");

        //Array is created, with each entry corresponding to title, overview, appearance, size, and habitat
        String[] FishInfoArray = FishDetailsDB.getInfo(fish.getName());

        title.setText(FishInfoArray[0]);

        fishPhoto.setImageResource(getResourceId("drawable", "_info", fish));
        overview.setText(FishInfoArray[1]);
        fishAppearance.setText(FishInfoArray[2]);
        fishSize.setText(FishInfoArray[3]);
        fishRange.setImageResource(getResourceId("drawable", "_range", fish));
        fishHabitat.setText(FishInfoArray[4]);
    }

    public int getResourceId(String type, String info, Fish fish){
        int id = getResources().getIdentifier(fish.getResourceName() + info, type, getPackageName());
        return id;
    }


}
