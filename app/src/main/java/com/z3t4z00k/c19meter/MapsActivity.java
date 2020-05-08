package com.z3t4z00k.c19meter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int COUNTRY = 0, STATE = 1;
    private Boolean jk = false;
    private ConstraintLayout stats;
    private TextView stat;
    private TextView t, c, d;
    private ImageView close;
    private ImageView back;
    private int AN = 1, AP = 2, AR = 3, AS = 4,
                BR = 5, CG = 7, CH = 6, GA = 10,
                GJ = 11, HR = 12, HP = 13, JK = 14,
                JH = 15, KA = 16, KL = 17, LA = 18,
                MP = 19, MH = 20, MN = 21, ML = 22,
                MZ = 23, OD = 24, PY = 25, PB = 26,
                RJ = 27, TN = 28, TS = 29, TR = 30,
                UK = 31, UP = 32, WB = 33,
                DD = 8, DL = 9, LD = 36, NL = 37, SK = 38;
    private String URL = "https://zetazook.club/c19/states.php?id=";
    private String res;

    private String getAddress(LatLng latLng, int code){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
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

    private void hideView(final View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("MapsActvitiy", "Hide animation started");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("MapsActvitiy", "Hide animation ended");
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        view.startAnimation(animation);
    }

    private void dispView(final View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("MapsActvitiy", "Display animation started");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("MapsActvitiy", "Display animation ended");
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        view.startAnimation(animation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        stats = findViewById(R.id.stats);
        stat = findViewById(R.id.state);
        close = findViewById(R.id.close);
        t = findViewById(R.id.t);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        back = findViewById(R.id.navigationBarButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, dashboard.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng delhi = new LatLng(24.7, 82.41);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hideView(stats);
                stats.setVisibility(View.GONE);
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));
                CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(4);
                mMap.animateCamera(cameraUpdate);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));
        final CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(4);
        mMap.animateCamera(cameraUpdate);
        mMap.setBuildingsEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("India"));
                final SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                CameraUpdate cameraUpdate1 = CameraUpdateFactory.zoomTo(6);
                mMap.animateCamera(cameraUpdate1);
                if(stats.getVisibility()==View.VISIBLE)
                    stats.setVisibility(View.GONE);
                t.setText("...");
                c.setText("...");
                d.setText("...");
                stat.setText(R.string.loading);
                mMap.clear();
                String country = getAddress(latLng, COUNTRY);
                Log.d("MapsActivity", "Country= " + country);
                String state = "";
                if(country == null || country.equals("India")) {
                    jk = country == null;
                    if(jk)
                        state = "Jammu & Kashmir";
                    else
                        state = getAddress(latLng, STATE);
                    final String id = state;
                    @SuppressLint("StaticFieldLeak")
                    class Login extends AsyncTask<Void, Void, String> {

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected String doInBackground(Void... voids) {
                            RequestHandler requestHandler = new RequestHandler();

                            HashMap<String, String> params = new HashMap<>();
                            params.put("name", "Archit");

                            return requestHandler.sendPostRequest(URL+ getId(id), params);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            res = s;
                            Log.d("MapsActivity", "State stats- " + s);
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(s);
                                t.setText(jsonObject.getString("t"));
                                c.setText(jsonObject.getString("c"));
                                d.setText(jsonObject.getString("d"));
                                stat.setText(jsonObject.getString("s"));

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(id+"s", jsonObject.getString("s"));
                                editor.putString(id+"t", jsonObject.getString("t"));
                                editor.putString(id+"c", jsonObject.getString("c"));
                                editor.putString(id+"d", jsonObject.getString("d"));
                                editor.putString(id+"dd", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                                editor.putString(id+"mm", String.valueOf(calendar.get(Calendar.MONTH)));
                                editor.apply();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(MapsActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    if(!String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).equals(sharedPreferences.getString(id+"dd", "00")) && !String.valueOf(calendar.get(Calendar.MONTH)).equals(sharedPreferences.getString(id+"mm", "00"))) {
                        Log.d("MapsActivity", "Current- " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH));
                        Log.d("MapsActivity", "Stored- " + sharedPreferences.getString(id+"dd", "00") + "/" + sharedPreferences.getString(id+"mm", "00"));
                        Log.d("MapsActivity", "State selected- "+id+" "+sharedPreferences.getString(id+"s", "null"));
                        Login login = new Login();
                        login.execute();
                    }
                    else{
                        Log.d("MapsActivity", "Current- " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH));
                        Log.d("MapsActivity", "Stored- " + sharedPreferences.getString(id+"dd", "00") + "/" + sharedPreferences.getString(id+"mm", "00"));
                        t.setText(sharedPreferences.getString(id+"t", "0"));
                        c.setText(sharedPreferences.getString(id+"c", "0"));
                        d.setText(sharedPreferences.getString(id+"d", "0"));
                        stat.setText(sharedPreferences.getString(id+"s", "0"));
                    }
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Cases in " + state));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //dispView(stats);
                    //stats.animate().alphaBy(0).alpha(1).setDuration(1000);
                    stats.setVisibility(View.VISIBLE);
                }
                else Toast.makeText(getApplicationContext(), "Region outside India!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getId(String id) {
        if(id.equals("Jammu & Kashmir"))
            return JK;
        else if(id.equals("Himachal Pradesh"))
            return HP;
        else if(id.equals("Uttarakhand"))
            return UK;
        else if(id.equals("Uttar Pradesh"))
            return UP;
        else if(id.equals("Haryana"))
            return HR;
        else if(id.equals("Chandigarh"))
            return CH;
        else if(id.equals("Punjab"))
            return PB;
        else if(id.equals("Delhi"))
            return DL;
        else if(id.equals("Rajasthan"))
            return RJ;
        else if(id.equals("Madhya Pradesh"))
            return MP;
        else if(id.equals("Bihar"))
            return BR;
        else if(id.equals("Sikkim"))
            return SK;
        else if(id.equals("Arunachal Pradesh"))
            return AR;
        else if(id.equals("Assam"))
            return AS;
        else if(id.equals("Meghalaya"))
            return ML;
        else if(id.equals("Mizoram"))
            return MZ;
        else if(id.equals("Nagaland"))
            return NL;
        else if(id.equals("Manipur"))
            return MN;
        else if(id.equals("Tripura"))
            return TR;
        else if(id.equals("West Bengal"))
            return WB;
        else if(id.equals("Jharkhand"))
            return JH;
        else if(id.equals("Odisha"))
            return OD;
        else if(id.equals("Chhattisgarh"))
            return CG;
        else if(id.equals("Gujarat"))
            return GJ;
        else if(id.equals("Maharashtra"))
            return MH;
        else if(id.equals("Goa"))
            return GA;
        else if(id.equals("Telangana"))
            return TS;
        else if(id.equals("Andhra Pradesh"))
            return AP;
        else if(id.equals("Karnataka"))
            return KA;
        else if(id.equals("Tamil Nadu"))
            return TN;
        else if(id.equals("Kerala"))
            return KL;
        else if(id.equals("Lakshadweep"))
            return LD;
        else return 0;
    }


}
