package org.me.gcu.equakestartercode;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    static final String KEY_LAT = "lat";
    static final String KEY_LONG = "long";
    static final String KEY_MAG = "magnitude";
    double lati;
    double longi;
    float magnitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent in = getIntent();

        // Get XML values from previous intent
        //double d= Double.parseDouble(yourString)
        lati = Double.parseDouble(in.getStringExtra(KEY_LAT));
        longi = Double.parseDouble(in.getStringExtra(KEY_LONG));
        magnitude = Float.parseFloat(in.getStringExtra(KEY_MAG));

        Log.e("lati_map", lati+"");
        Log.e("longi_map", longi+"");
        Log.e("magni3", magnitude+"");

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
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(55.775, -5.629);
        //Log.e("lati_map", lati+"");
        //Log.e("longi_map", longi+"");
        LatLng sydney = new LatLng(lati, longi);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Earthquake location"));

        float top = Float.parseFloat("2.5");
        float divi = magnitude/top;
        float diff = 1 - divi;
        float final_color = 70*diff;

        Marker melbourne = mMap.addMarker(new MarkerOptions().position(sydney)
                //.icon(getMarkerIcon("#ffcccc"))
                .icon(BitmapDescriptorFactory.defaultMarker(final_color))
                //.alpha(0.1f)
        );


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    // method definition
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
