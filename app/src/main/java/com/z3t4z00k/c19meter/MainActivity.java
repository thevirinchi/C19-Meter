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
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseSession;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity {

    //String URL = "https://c19meterphp.herokuapp.com/index.php";
    String URL = "https://zetazook.club/c19/index.php";
    String URL_UPDATES = "https://zetazook.club/c19/updates.php";
    public static final String MY_PREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();


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
        }

        final SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        if(!String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).equals(sharedPreferences.getString("dd", "00")) && !String.valueOf(calendar.get(Calendar.MONTH)).equals(sharedPreferences.getString("mm", "00"))) {
            Updates updates = new Updates();
            updates.execute();
            Login login = new Login();
            login.execute();
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
