package android.example.ontariofish;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

        // Add a marker in Sydney and move the camera
        LatLng Ontario = new LatLng(51.833568, -86.997632);
        LatLng Southwest = new LatLng(41.721325, -95.150434);
        LatLng NorthEast = new LatLng(57.910221, -74.343067);
        LatLngBounds OntarioRestrict = new LatLngBounds(Southwest, NorthEast);

        KmlLayer zone1 = addLayer(R.raw.zone1);
        KmlLayer zone2 = addLayer(R.raw.zone2);
        KmlLayer zone3 = addLayer(R.raw.zone3);
        KmlLayer zone4 = addLayer(R.raw.zone4);

        zone1.addLayerToMap();
        zone2.addLayerToMap();
        zone3.addLayerToMap();
        zone4.addLayerToMap();



        mMap.setLatLngBoundsForCameraTarget(OntarioRestrict);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        mMap.addMarker(new MarkerOptions().position(Ontario).title("Marker in Ontario"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ontario));
    }

    public KmlLayer addLayer(int resourceId){

        try {
            return new KmlLayer(mMap, resourceId, this);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
