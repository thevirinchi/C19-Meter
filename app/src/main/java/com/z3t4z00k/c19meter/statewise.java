package com.z3t4z00k.c19meter;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.Objects;

public class statewise extends AppCompatActivity {

    private String URL = "https://zetazook.club/c19/allstates.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statewise);

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
                        for (int i = 0; i<33; i++) {
                            obj = jsonArray.getJSONObject(i);
                            Log.d("statewise", "Response- " + obj);
                            stateModals.add(new StateModal(obj.getString("s"),obj.getString("t"),obj.getString("c"),obj.getString("d")));
                            stateListAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(statewise.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }
        Login login = new Login();
        login.execute();

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(statewise.this, dashboard.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
