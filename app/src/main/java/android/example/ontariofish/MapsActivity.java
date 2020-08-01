package android.example.ontariofish;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button help;
    private Handler mapHandler = new Handler();
    private KmlLayer zones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));

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
    // bounds of the desired area

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
}
