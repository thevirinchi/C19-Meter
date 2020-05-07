package com.z3t4z00k.c19meter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
                DD = 34, DL = 35, LD = 36, NL = 37, SK = 38;
    private String URL = "https://zetazook.club/c19/states.php?id=";
    private String res;

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

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stats.setVisibility(View.GONE);
                mMap.clear();
            }
        });

        LatLng delhi = new LatLng(28.38, 77.12);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));
        mMap.setBuildingsEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
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
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MapsActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                Login login = new Login();
                login.execute();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Cases in " + state));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                stats.setVisibility(View.VISIBLE);
            }
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
