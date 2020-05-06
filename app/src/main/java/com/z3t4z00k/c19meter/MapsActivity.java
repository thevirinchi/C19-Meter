package com.z3t4z00k.c19meter;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int COUNTRY = 0, STATE = 1;
    private Boolean jk = false;

    private String getAddress(LatLng latLng, int code){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (code){
            case 0:
                String country;
                try {
                    country = Objects.requireNonNull(addresses).get(0).getCountryName();
                }
                catch (Exception e){
                    return ("India");
                }
                return country;
            case 1:
                return (Objects.requireNonNull(addresses).get(0).getAdminArea());
            default:
                return "Error";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng delhi = new LatLng(28.38, 77.12);
        mMap.addMarker(new MarkerOptions().position(delhi).title("Marker in Delhi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String country = getAddress(latLng, COUNTRY);
                Log.d("MapsActivity", "Country= " + country);
                String state = "";
                if(country == null || country.equals("India")) {
                    if(country == null)
                        jk = true;
                    if(jk)
                        state = "Jammu & Kashmir";
                    else
                        state = getAddress(latLng, STATE);
                    Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
