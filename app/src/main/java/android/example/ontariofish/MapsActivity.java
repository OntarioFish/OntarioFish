package android.example.ontariofish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private Button help;
    private Handler mapHandler = new Handler();
    private KmlLayer zones;
    private DrawerLayout drawer;
    private boolean[] favouritesAdded = new boolean[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = findViewById(R.id.maps_toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.getItem(1).getSubMenu();
        SharedPreferences sharedPreferences = getSharedPreferences(FishRegulations.SHARED_PREFS, MODE_PRIVATE);
        Boolean favoriteSelected;


        for(int i = 1; i <= 20; i++){
            favoriteSelected = sharedPreferences.getBoolean("#zone"+i, false);
            if(favoriteSelected){
                subMenu.add(0, i, 0,"Zone " + i);
                favouritesAdded[i - 1] = true;
            } else {
                favouritesAdded[i - 1] = false;
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        help = (Button)findViewById(R.id.help_button_maps);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder help = new AlertDialog.Builder(MapsActivity.this);
                help.setTitle("Map Help");
                help.setMessage(R.string.map_alert_dialog);
                help.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                help.show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng Ontario = new LatLng(51.833568, -86.997632);
        LatLng Southwest = new LatLng(41.721325, -95.150434);
        LatLng NorthEast = new LatLng(57.910221, -74.343067);
        LatLngBounds OntarioRestrict = new LatLngBounds(Southwest, NorthEast);

        mMap.setLatLngBoundsForCameraTarget(OntarioRestrict);


        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ontario));
        mMap.setMinZoomPreference(5);

        MapRunnable runnable = new MapRunnable();
        runnable.run();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public KmlLayer addLayer(int resourceId){

        try {
            return new KmlLayer(mMap, resourceId, this);

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logbook_menu_item:
                Intent intent = new Intent(MapsActivity.this, Logbook.class);
                startActivity(intent);
                break;
            case R.id.fish_list_menu_item:
                intent = new Intent(MapsActivity.this, FishInfo.class);
                startActivity(intent);
                break;
        }

        for(int i = 1; i <= 20; i ++){
            if(item.getItemId() == i){
                Intent intent = new Intent(MapsActivity.this, FishRegulations.class);
                intent.putExtra("ZONE", "#zone" + i);
                startActivity(intent);
            }
        }
        return true;
    }

    class MapRunnable implements Runnable{
        @Override
        public void run() {
            zones= addLayer(R.raw.fish_zones);
            Log.i("Choreographer", "makeLayer");
            mapHandler.post(new Runnable(){
                @Override
                public void run(){
                    zones.addLayerToMap();
                    Log.i("Choreographer", "addLayer");
                    zones.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                        @Override
                        public void onFeatureClick(Feature feature) {
                            Intent intent = new Intent(MapsActivity.this, FishRegulations.class);
                            intent.putExtra("ZONE", feature.getId());
                            startActivity(intent);

                        }
                    });
                }
            });
        }
    }


    @Override
    protected void onRestart() {
        //detect changes to see if a user added any new zones
        super.onRestart();
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.getItem(1).getSubMenu();
        SharedPreferences sharedPreferences = getSharedPreferences(FishRegulations.SHARED_PREFS, MODE_PRIVATE);


        for(int i = 1; i <=20; i ++){
            Boolean favoriteChange = sharedPreferences.getBoolean("#zone" + i, false);
            if(favouritesAdded[i - 1] != favoriteChange){
                if(favoriteChange){
                    subMenu.add(0, i, 0, "Zone " + i);
                } else {
                    subMenu.removeItem(i);
                }
            }
            favouritesAdded[i - 1] = favoriteChange;
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        SharedPreferences sharedPreferences = getSharedPreferences(FishRegulations.SHARED_PREFS, MODE_PRIVATE);
//
//        for(int i = 1; i <= 20; i++){
//            boolean isFavorite = sharedPreferences.getBoolean("#zone" + i, false);
//            if(isFavorite) {
//                favouritesAdded[i - 1] = true;
//            } else {
//                favouritesAdded[i - 1] = false;
//            }
//            System.out.println(favouritesAdded[i - 1]);
//        }
//
//        System.out.println("*****************");
//    }
}
