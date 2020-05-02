package com.z3t4z00k.c19meter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class dashboard extends AppCompatActivity {

    String URL = "https://c19meterphp.herokuapp.com/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final TextView death = findViewById(R.id.deaths);
        final TextView recov = findViewById(R.id.recoveries);
        final TextView cases = findViewById(R.id.cases);

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

                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                    if (obj.getInt("cases")>0) {
                        cases.setText(String.valueOf(obj.getString("cases")));
                        recov.setText(String.valueOf(obj.getString("recov")));
                        death.setText(String.valueOf(obj.getString("death")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(dashboard.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }

        Login login = new Login();
        login.execute();


        /*LineChart casesChart = findViewById(R.id.casesChart);
        List<Entry> cases = new ArrayList<>();

        cases.add(new Entry(1, 4));
        cases.add(new Entry(2, 3));
        cases.add(new Entry(3, 6));

        LineDataSet caseDataSet = new LineDataSet(cases, "");
        caseDataSet.setValueTextColor(R.color.colorDarkGray);
        caseDataSet.setDrawValues(false);
        caseDataSet.setDrawCircles(false);
        caseDataSet.setDrawFilled(true);
        caseDataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_corners_5_grad1));
        caseDataSet.setDrawIcons(false);
        
        LineData caseData = new LineData(caseDataSet);
        casesChart.setData(caseData);
        casesChart.getLegend().setEnabled(false);
        casesChart.setDescription(null);
        casesChart.getAxisLeft().setDrawLabels(false);
        casesChart.getAxisRight().setDrawLabels(false);
        casesChart.getAxisLeft().setDrawGridLines(false);
        casesChart.getXAxis().setDrawGridLines(false);
        casesChart.getAxisLeft().setEnabled(false);;
        casesChart.getXAxis().setEnabled(false);;
        casesChart.getAxisRight().setEnabled(false);
        casesChart.getAxisRight().setGridColor(R.color.colorPrimary);
        casesChart.invalidate();


        LineChart recoveriesChart = findViewById(R.id.recoveriesChart);
        List<Entry> recoveries = new ArrayList<>();

        recoveries.add(new Entry(1, 4));
        recoveries.add(new Entry(2, 3));
        recoveries.add(new Entry(3, 6));

        LineDataSet recoveryDataSet = new LineDataSet(recoveries, "");
        recoveryDataSet.setValueTextColor(R.color.colorDarkGray);
        recoveryDataSet.setDrawValues(false);
        recoveryDataSet.setDrawCircles(false);
        recoveryDataSet.setDrawFilled(true);
        recoveryDataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_corners_5_grad2));
        recoveryDataSet.setDrawIcons(false);


        LineData recoveryData = new LineData(recoveryDataSet);
        recoveriesChart.setData(recoveryData);
        recoveriesChart.getLegend().setEnabled(false);
        recoveriesChart.setDescription(null);
        recoveriesChart.getAxisLeft().setDrawLabels(false);
        recoveriesChart.getAxisRight().setDrawLabels(false);
        recoveriesChart.getAxisLeft().setDrawGridLines(false);
        recoveriesChart.getXAxis().setDrawGridLines(false);
        recoveriesChart.getAxisLeft().setEnabled(false);;
        recoveriesChart.getXAxis().setEnabled(false);;
        recoveriesChart.getAxisRight().setEnabled(false);
        recoveriesChart.getAxisRight().setGridColor(R.color.colorPrimary);
        recoveriesChart.invalidate();


        LineChart deathsChart = findViewById(R.id.deathsChart);
        List<Entry> deaths = new ArrayList<>();

        deaths.add(new Entry(1, 4));
        deaths.add(new Entry(2, 3));
        deaths.add(new Entry(3, 6));

        LineDataSet deathDataSet = new LineDataSet(deaths, "");
        deathDataSet.setValueTextColor(R.color.colorDarkGray);
        deathDataSet.setDrawValues(false);
        deathDataSet.setDrawCircles(false);
        deathDataSet.setDrawFilled(true);
        deathDataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_corners_5_grad3));
        deathDataSet.setDrawIcons(false);


        LineData deathData = new LineData(deathDataSet);
        deathsChart.setData(deathData);
        deathsChart.getLegend().setEnabled(false);
        deathsChart.setDescription(null);
        deathsChart.getAxisLeft().setDrawLabels(false);
        deathsChart.getAxisRight().setDrawLabels(false);
        deathsChart.getAxisLeft().setDrawGridLines(false);
        deathsChart.getXAxis().setDrawGridLines(false);
        deathsChart.getAxisLeft().setEnabled(false);;
        deathsChart.getXAxis().setEnabled(false);;
        deathsChart.getAxisRight().setEnabled(false);
        deathsChart.getAxisRight().setGridColor(R.color.colorPrimary);
        deathsChart.invalidate();*/
    }
}
