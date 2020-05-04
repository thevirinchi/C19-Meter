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

public class MainActivity extends AppCompatActivity {

    String URL = "https://c19meterphp.herokuapp.com/index.php";
    public static final String MY_PREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        /*if(ParseUser.getCurrentUser().getUsername() == null)
            ParseUser.logInInBackground("", "", new LogInCallback(){
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null)
                        Log.d("MainActivity", "ParseUser- " + user + " Logged in!");
                    else
                        Log.d("MainActivity", "ParseUser- Error- " + e.getMessage());
                }
            });*/

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

        Login login = new Login();
        login.execute();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, onBoardingOne.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 3000);*/
    }
}
