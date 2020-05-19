package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class FishRegulations extends AppCompatActivity {

    private ImageView mRegulationsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_regulations);

        mRegulationsMap = (ImageView)findViewById(R.id.map_overview);
        mRegulationsMap.setImageResource(R.drawable.fish_regulations_overlay);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));
    }
}
