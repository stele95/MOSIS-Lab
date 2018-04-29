package rs.elfak.mosis.stele.myplaces;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import rs.elfak.mosis.stele.myplaces.classes.MyPlace;
import rs.elfak.mosis.stele.myplaces.classes.MyPlacesData;

public class MyPlacesMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final int SHOW_MAP=0;
    public static final int CENTER_PLACE_ON_MAP = 1;
    public static final int SELECT_COORDINATES = 2;

    private int state = 0;
    private boolean selCoorsEnabled = false;

    private LatLng placeLoc;

    private HashMap<Marker,Integer> markerPlaceIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


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


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        else
            mMap.setMyLocationEnabled(true);


        try
        {
            Intent mapIntent =getIntent();
            Bundle mapBundle = mapIntent.getExtras();

            if(mapBundle != null)
            {
                state = mapBundle.getInt("state");
                if(state == CENTER_PLACE_ON_MAP)
                {
                    String lon = mapBundle.getString("lon");
                    String lat = mapBundle.getString("lat");
                    placeLoc = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("onMapReady", "Error reading state");
        }

        if(state == SHOW_MAP)
            mMap.setMyLocationEnabled(true);
        else if (state == SELECT_COORDINATES)
        {

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if(state == SELECT_COORDINATES && selCoorsEnabled) {
                        String lon = Double.toString(latLng.longitude);
                        String lat = Double.toString(latLng.latitude);

                        Intent locIntent = new Intent();
                        locIntent.putExtra("lon", lon);
                        locIntent.putExtra("lat", lat);
                        setResult(Activity.RESULT_OK, locIntent);
                        finish();
                    }
                }
            });
        }
        else
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLoc,15));

        addMyPlacesMarkers();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MyPlacesMapsActivity.this, ViewMyPlaceActivity.class);
                int i = markerPlaceIdMap.get(marker);
                intent.putExtra("position", i);
                startActivity(intent);
                return true;
            }
        });

    }

    private void addMyPlacesMarkers()
    {
        ArrayList<MyPlace> places = MyPlacesData.getInstance().getMyPlaces();
        markerPlaceIdMap = new HashMap<>((int) ((double) places.size()*1.2));
        for(int i = 0; i < places.size(); i++)
        {
            MyPlace place = places.get(i);
            String lat = place.getLatitude();
            String lon = place.getLongitude();

            LatLng loc = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.myplace));
            markerOptions.title(place.getName());
            Marker marker = mMap.addMarker(markerOptions);
            markerPlaceIdMap.put(marker, i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);*/

        if(state == SELECT_COORDINATES && !selCoorsEnabled)
        {
            menu.add(0,1,1,"Select Coordinates");
            menu.add(0, 2, 2, "Cancel");
        }

        return super.onCreateOptionsMenu(menu);
    }


    static int NEW_PLACE=333;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.about_item:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                Toast.makeText(this,"About!",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.new_place_item:
                Toast.makeText(this,"New place!",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, EditMyPlaceActivity.class);
                startActivityForResult(intent2,NEW_PLACE);
                return true;*/

            case 1:
                selCoorsEnabled = true;
                Toast.makeText(this, "Select Coordinates", Toast.LENGTH_SHORT).show();
                return true;

            case 2:
                setResult(Activity.RESULT_CANCELED);
                finish();
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    mMap.setMyLocationEnabled(true);
                return;
        }
    }
}
