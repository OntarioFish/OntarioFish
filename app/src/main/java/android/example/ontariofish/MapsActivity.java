package android.example.ontariofish;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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
        Polygon zone1 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(56.84661, -88.99081), new LatLng(56.84563, -88.98662), new LatLng(56.84343, -88.98131),
                     new LatLng(56.84050, -88.95991), new LatLng(56.84033, -88.95444), new LatLng(56.83817, -88.94079),
                     new LatLng(56.83963, -88.91582), new LatLng(56.83982, -88.91127), new LatLng(56.84442, -88.90869),
                     new LatLng(56.84625, -88.90947), new LatLng(56.85710, -88.89426), new LatLng(56.85827, -88.87624),
                     new LatLng(56.84724, -88.86337), new LatLng(56.82964, -88.86259), new LatLng(56.82691, -88.85804),
                     new LatLng(56.82997, -88.84714), new LatLng(56.83682, -88.85521), new LatLng(56.83907, -88.82320),
                     new LatLng(56.81684, -88.81266), new LatLng(56.80938, -88.83972), new LatLng(56.78352, -88.81165),
                     new LatLng(56.73591, -88.69784), new LatLng(56.69872, -88.66734), new LatLng(56.65422, -88.52555),
                     new LatLng(56.60021, -88.42908), new LatLng(56.51166, -88.18120), new LatLng(56.48058, -88.02567),
                     new LatLng(56.45573, -87.96559), new LatLng(56.38567, -87.92268), new LatLng(56.22415, -87.72046),
                     new LatLng(56.11750, -87.63944), new LatLng(56.09414, -87.62982), new LatLng(56.03588, -87.47052),
                     new LatLng(55.99442, -87.42520), new LatLng(55.91408, -86.90473), new LatLng(55.76371, -86.37052),
                     new LatLng(55.70262, -86.25173), new LatLng(55.65539, -85.90360), new LatLng(55.55920, -85.64473),
                     new LatLng(55.38328, -85.33025), new LatLng(55.26765, -85.00523), new LatLng(55.23243, -84.74430),
                     new LatLng(55.29268, -83.89272), new LatLng(55.19835, -83.32074), new LatLng(55.21167, -82.93347),
                     new LatLng(55.12931, -82.79821), new LatLng(55.05190, -82.26880), new LatLng(54.77008, -82.21456),
                     new LatLng(54.21292, -82.42604), new LatLng(54.02741, -82.23241), /*shared with zone 3 */ new LatLng(54.0001, -82.22209),
                     /*shared with zone 2, 3 */ new LatLng(54.00007, -86.58804), /* shr. zone2 */ new LatLng(53.99994, -89.00029),
                     /*shr. zone2*/new LatLng(55.09438, -91.74136)));


        mMap.setLatLngBoundsForCameraTarget(OntarioRestrict);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);





        mMap.addMarker(new MarkerOptions().position(Ontario).title("Marker in Ontario"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ontario));
    }

}
