package com.z3t4z00k.c19meter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class State extends AppCompatActivity {

    private static final String URL_ZONE = "https://api.covid19india.org/zones.json";
    String TAG = "State";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final String URL = "https://api.covid19india.org/v2/state_district_wise.json";
        final TextView head = findViewById(R.id.heading);
        final TextView count = findViewById(R.id.count);
        final TextView cases = findViewById(R.id.cases);
        final TextView recov = findViewById(R.id.recoveries);
        final TextView death = findViewById(R.id.deaths);
        final ImageView back = findViewById(R.id.back);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<DistrictModal> districtModals = new ArrayList<>();
        final DistrictListAdapter  districtListAdapter = new DistrictListAdapter(this, districtModals);
        recyclerView.setAdapter(districtListAdapter);
        final TextView zones = findViewById(R.id.zoneInfo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String state = Objects.requireNonNull(bundle).getString("s");
        Log.d("State", Objects.requireNonNull(state));
        head.setText(state);

        zones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(State.this, Zone.class);
                intent.putExtra("s", state);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(State.this, statewise.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        final RequestQueue queue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(jsonArray!= null){
                            JSONObject jsonObject = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    jsonObject = (JSONObject) jsonArray.get(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(jsonObject != null){
                                    try {
                                        if(state.equals(jsonObject.getString("state"))){
                                            //Log.d(TAG, "onResponseMatch: "+"i= "+i+" "+ jsonObject);
                                            JSONArray districts = (JSONArray) jsonObject.get("districtData");
                                            int act = 0, con = 0, dec = 0, rec = 0;
                                            for(int j = 0; j < districts.length(); j++){
                                                JSONObject district = (JSONObject) districts.get(j);
                                                districtModals.add(new DistrictModal(district.getString("district"), district.getString("confirmed"), district.getString("recovered"), district.getString("deceased")));
                                                districtListAdapter.notifyDataSetChanged();
                                                act += district.getInt("active");
                                                con += district.getInt("confirmed");
                                                dec += district.getInt("deceased");
                                                rec += district.getInt("recovered");
                                            }
                                            Log.d(TAG, "onResponse: act="+act+" con="+con+" dec="+dec+" rec="+rec);
                                            count.setText(String.valueOf(con));
                                            cases.setText(String.valueOf(act));
                                            recov.setText(String.valueOf(rec));
                                            death.setText(String.valueOf(dec));
                                            break;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(stringRequest);

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(State.this, statewise.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
