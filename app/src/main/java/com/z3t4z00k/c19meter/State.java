package com.z3t4z00k.c19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Objects;

public class State extends AppCompatActivity {

    String TAG = "State";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final String URL = "https://api.covid19india.org/v2/state_district_wise.json";

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String state = Objects.requireNonNull(bundle).getString("s");
        Log.d("State", Objects.requireNonNull(state));

        final RequestQueue queue = Volley.newRequestQueue(this);
// Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Log.d(TAG, "onResponse: "+response);
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
                                            Log.d(TAG, "onResponseMatch: "+"i= "+i+" "+ jsonObject);
                                            break;
                                        }
                                        else
                                            Log.d(TAG, "onResponseNotMatch: "+"i= "+i+" "+ jsonObject.getString("state"));
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

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
