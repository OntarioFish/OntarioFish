package android.example.ontariofish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FishRegulations extends AppCompatActivity  {

    private TextView zoneTitle;
    private Button helpButton;
    private Button favouriteButton;

    private int regionNumber;
    private boolean favouriteSelected;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Fragment speciesFragment;
    private Fragment waterbodyFragment;

    public static final String SHARED_PREFS = "favoriteZonesPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_regulations);



        Toast.makeText(this, "Select a fish from dropdown menu!", Toast.LENGTH_SHORT).show();

        Bundle extras = getIntent().getExtras();
        final String zoneName = extras.getString("ZONE");
        favouriteButton = (Button) findViewById(R.id.favourite_button);
        helpButton = (Button) findViewById(R.id.help_button_regulation);
        zoneTitle = (TextView)findViewById(R.id.zone_title);
        setTitle(zoneName);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        speciesFragment = new SpeciesRegulations();
        waterbodyFragment = new WaterbodyExceptions();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(speciesFragment, "Species Regulations");
        viewPagerAdapter.addFragment(waterbodyFragment, "Waterbody Regulations");
        viewPager.setAdapter(viewPagerAdapter);


        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        favouriteSelected = sharedPreferences.getBoolean(zoneName, false);
        if(favouriteSelected)
            favouriteButton.setBackground(getDrawable(R.drawable.ic_baseline_star));



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
                    favouriteSelected = false;

                } else {
                    favouriteButton.setBackground(getDrawable(R.drawable.ic_baseline_star));
                    sharedPreferences.edit().putBoolean(zoneName, true).apply();
                    favouriteSelected = true;
                }
            }
        });

    }

    public void setTitle(String zoneName){

        switch(zoneName){
            case "#zone1":
                zoneTitle.setText("Zone 1");
                regionNumber = 1;
                break;
            case "#zone2":
                zoneTitle.setText("Zone 2");
                regionNumber = 2;
                break;
            case "#zone3":
                zoneTitle.setText("Zone 3");
                regionNumber = 3;
                break;
            case "#zone4":
                zoneTitle.setText("Zone 4");
                regionNumber = 4;
                break;
            case "#zone5":
                zoneTitle.setText("Zone 5");
                regionNumber = 5;
                break;
            case "#zone6":
                zoneTitle.setText("Zone 6");
                regionNumber = 6;
                break;
            case "#zone7":
                zoneTitle.setText("Zone 7");
                regionNumber = 7;
                break;
            case "#zone8":
                zoneTitle.setText("Zone 8");
                regionNumber = 8;
                break;
            case "#zone9":
                zoneTitle.setText("Zone 9");
                regionNumber = 9;
                break;
            case "#zone10":
                zoneTitle.setText("Zone 10");
                regionNumber = 10;
                break;
            case "#zone11":
                zoneTitle.setText("Zone 11");
                regionNumber = 11;
                break;
            case "#zone12":
                zoneTitle.setText("Zone 12");
                regionNumber = 12;
                break;
            case "#zone13":
                zoneTitle.setText("Zone 13");
                regionNumber = 13;
                break;
            case "#zone14":
                zoneTitle.setText("Zone 14");
                regionNumber = 14;
                break;
            case "#zone15":
                zoneTitle.setText("Zone 15");
                regionNumber = 15;
                break;
            case "#zone16":
                zoneTitle.setText("Zone 16");
                regionNumber = 16;
                break;
            case "#zone17":
                zoneTitle.setText("Zone 17");
                regionNumber = 17;
                break;
            case "#zone18":
                zoneTitle.setText("Zone 18");
                regionNumber = 18;
                break;
            case "#zone19":
                zoneTitle.setText("Zone 19");
                regionNumber = 19;
                break;
            case "#zone20":
                zoneTitle.setText("Zone 20");
                regionNumber = 20;
                break;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment f = fragments.get(position);
            Bundle args = new Bundle();
            args.putInt("ZONE", regionNumber);
            f.setArguments(args);
            return f;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}
