package com.z3t4z00k.c19meter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.github.mikephil.charting.data.LineData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.TimeZone;

public class statewise extends AppCompatActivity {

    private String URL = "https://zetazook.club/c19/allstates.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statewise);

        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("India"));
        final SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final ImageView back = findViewById(R.id.back);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<StateModal> stateModals = new ArrayList<>();
        final stateListAdapter stateListAdapter = new stateListAdapter(this, stateModals);
        recyclerView.setAdapter(stateListAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(statewise.this, dashboard.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
                JSONObject obj;
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(s);
                    obj = jsonArray.getJSONObject(0);
                    if (!obj.getString("s").equals("")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        for (int i = 0; i<33; i++) {
                            obj = jsonArray.getJSONObject(i);
                            Log.d("statewise", "Response- " + obj);
                            stateModals.add(new StateModal(obj.getString("s"),obj.getString("t"),obj.getString("c"),obj.getString("d")));
                            stateListAdapter.notifyDataSetChanged();
                            editor.putString(i+"s", obj.getString("s"));
                            editor.putString(i+"t", obj.getString("t"));
                            editor.putString(i+"c", obj.getString("c"));
                            editor.putString(i+"d", obj.getString("d"));
                            editor.putString(i+"dd", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                            editor.putString(i+"mm", String.valueOf(calendar.get(Calendar.MONTH)));
                        }
                        editor.putString("lastUpdate_dd", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                        editor.putString("lastUpdate_mm", String.valueOf(calendar.get(Calendar.MONTH)));
                        editor.apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(statewise.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }
        if((calendar.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(sharedPreferences.getString("lastUpdate_dd", "00"))) || ((calendar.get(Calendar.MONTH)) > Integer.parseInt(sharedPreferences.getString("lastUpdate_mm", "00")))) {
            Log.d("statewise", "Current- " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH));
            Log.d("statewise", "Stored- " + sharedPreferences.getString("lastUpdate_dd", "00") + "/" + sharedPreferences.getString("lastUpdate_mm", "00"));
            Login login = new Login();
            login.execute();
        }
        else
            for (int i = 0; i <22; i++){
                stateModals.add(new StateModal(sharedPreferences.getString(i+"s", ""), sharedPreferences.getString(i+"t", ""), sharedPreferences.getString(i+"c", ""), sharedPreferences.getString(i+"d", "")));
                stateListAdapter.notifyDataSetChanged();
            }


    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(statewise.this, dashboard.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
