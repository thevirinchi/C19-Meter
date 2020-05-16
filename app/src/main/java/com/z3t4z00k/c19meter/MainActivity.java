package com.z3t4z00k.c19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    //String URL = "https://c19meterphp.herokuapp.com/index.php";

    // String URL = "https://zetazook.club/c19/index.php";
    String URL_UPDATES = "https://zetazook.club/c19/updates.php";
    public static final String MY_PREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        final RequestQueue queue = Volley.newRequestQueue(this);


        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("India"));

        @SuppressLint("StaticFieldLeak")
        class Updates extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("name", "Archit");

                return requestHandler.sendPostRequest(URL_UPDATES, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("Main Activity", "Error while fetching updates- " + s);

                JSONObject obj;
                try {
                    obj = new JSONObject(s);
                    if (!obj.getString("dat0").equals("")) {
                        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("d1", obj.getString("dat0"));
                        editor.putString("d2", obj.getString("dat1"));
                        editor.putString("d3", obj.getString("dat2"));
                        editor.putString("d4", obj.getString("dat3"));
                        editor.putString("d5", obj.getString("dat4"));
                        editor.putString("t1", obj.getString("txt0"));
                        editor.putString("t2", obj.getString("txt1"));
                        editor.putString("t3", obj.getString("txt2"));
                        editor.putString("t4", obj.getString("txt3"));
                        editor.putString("t5", obj.getString("txt4"));
                        editor.putString("l1", obj.getString("url0"));
                        editor.putString("l2", obj.getString("url1"));
                        editor.putString("l3", obj.getString("url2"));
                        editor.putString("l4", obj.getString("url3"));
                        editor.putString("l5", obj.getString("url4"));
                        editor.putString("dd", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                        editor.putString("mm", String.valueOf(calendar.get(Calendar.MONTH)));
                        editor.apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Updates Exception: " + e, Toast.LENGTH_LONG).show();
                    Log.d("MainActivity", "Updates- " + e);
                }
            }
        }

        final StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL,
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
                            JSONObject jsonObject1 = null;
                            try {
                                jsonObject1 = jsonObject.getJSONArray("records").getJSONObject(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(jsonObject1!=null) {
                                SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                int confi = 0, cases = 0, recov = 0, death = 0, migra = 0;
                                try {
                                    cases = Integer.parseInt(jsonObject1.getString("active_cases"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    recov = Integer.parseInt(jsonObject1.getString("cured_discharged"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    death = Integer.parseInt(jsonObject1.getString("deaths"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    migra = Integer.parseInt(jsonObject.getString("migrated"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                confi = cases+recov+death+migra;
                                editor.putString("confi", String.valueOf(confi));
                                editor.putString("cases", String.valueOf(cases));
                                editor.putString("recov", String.valueOf(recov));
                                editor.putString("death", String.valueOf(death));
                                editor.apply();
                                if(!sharedPreferences.getBoolean("onBoard", false)) {
                                    startActivity(new Intent(MainActivity.this, onBoardingOne.class));
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this, dashboard.class));
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

        /*@SuppressLint("StaticFieldLeak")
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

                return requestHandler.sendPostRequest(URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("dashboard", "Error while fetching cases- " + s);

                JSONObject obj;
                try {
                    obj = new JSONObject(s);
                    if (obj.getInt("cases")>0) {
                        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("confi", obj.getString("confi"));
                        editor.putString("cases", obj.getString("cases"));
                        editor.putString("recov", obj.getString("recov"));
                        editor.putString("death", obj.getString("death"));
                        editor.apply();
                        if(!sharedPreferences.getBoolean("onBoard", false)) {
                            startActivity(new Intent(MainActivity.this, onBoardingOne.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        else {
                            startActivity(new Intent(MainActivity.this, dashboard.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }*/

        final SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        if(!String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).equals(sharedPreferences.getString("dd", "00")) && !String.valueOf(calendar.get(Calendar.MONTH)).equals(sharedPreferences.getString("mm", "00"))) {
            Updates updates = new Updates();
            updates.execute();
            queue.add(stringRequest1);
            //Login login = new Login();
            //login.execute();
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!sharedPreferences.getBoolean("onBoard", false))
                        startActivity(new Intent(MainActivity.this, onBoardingOne.class));
                    startActivity(new Intent(MainActivity.this, dashboard.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }, 3000);
        }


    }
}