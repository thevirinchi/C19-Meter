package com.z3t4z00k.c19meter;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Zone extends AppCompatActivity {

    private static final String URL_ZONE = "https://api.covid19india.org/zones.json";
    String TAG = "Zone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String state = Objects.requireNonNull(bundle).getString("s");
        final RequestQueue queue = Volley.newRequestQueue(this);
        final TextView header = findViewById(R.id.header);
        final RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<ZoneModal> zoneModals = new ArrayList<>();
        final ZoneListAdapter zoneListAdapter = new ZoneListAdapter(this, zoneModals);
        recycler.setAdapter(zoneListAdapter);

        header.setText(state);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL_ZONE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(jsonObject!= null){
                            JSONArray zones = null;
                            try {
                                zones = jsonObject.getJSONArray("zones");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Log.d(TAG, "onResponse2: "+zones);
                            if(zones!= null)
                                for (int i = 0; i < zones.length(); i++) {
                                    JSONObject zone = null;
                                    try {
                                        zone = zones.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if(zone != null){
                                        //Log.d(TAG, "onResponse21: "+zone);
                                        try {
                                            if(zone.getString("state").equals(state)){
                                                Log.d(TAG, "onResponse21: "+zone);
                                                zoneModals.add(new ZoneModal(zone.getString("district"), zone.getString("zone")));
                                                zoneListAdapter.notifyDataSetChanged();
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
                Log.d(TAG, "onErrorResponse2: " + error.getMessage());
            }
        });

        queue.add(stringRequest1);
    }

}
