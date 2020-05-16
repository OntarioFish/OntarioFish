package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FishDetails extends AppCompatActivity {

    private TextView title, overview, fishAppearance, fishSize, fishHabitat;
    private ImageView fishPhoto, fishRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

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


        title.setText(fish.getName());


        fishPhoto.setImageResource(getResourceId("drawable", "_info", fish));
        overview.setText(getResourceId("string", "_overview", fish));
        fishAppearance.setText(getResourceId("string", "_appearance", fish));
        fishSize.setText(getResourceId("string", "_size", fish));
        fishRange.setImageResource(getResourceId("drawable", "_range", fish));
        fishHabitat.setText(getResourceId("string", "_habitat", fish));
    }

    public int getResourceId(String type, String info, Fish fish){
        int id = getResources().getIdentifier(fish.getResourceName() + info, type, getPackageName());
        return id;
    }


}
