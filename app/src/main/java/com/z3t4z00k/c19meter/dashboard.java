package com.z3t4z00k.c19meter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class dashboard extends AppCompatActivity {

    String URL = "https://c19meterphp.herokuapp.com/topstates.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        final TextView count = findViewById(R.id.count);
        final TextView death = findViewById(R.id.deaths);
        final TextView recov = findViewById(R.id.recoveries);
        final TextView cases = findViewById(R.id.cases);
        final TextView s1c = findViewById(R.id.state1count);
        final TextView s2c = findViewById(R.id.state2count);
        final TextView s3c = findViewById(R.id.state3count);
        final TextView s1h = findViewById(R.id.state1Heading);
        final TextView s2h = findViewById(R.id.state2Heading);
        final TextView s3h = findViewById(R.id.state3Heading);
        final TextView t1 = findViewById(R.id.title1);
        final TextView d1 = findViewById(R.id.date1);
        final TextView t2 = findViewById(R.id.title2);
        final TextView d2 = findViewById(R.id.date2);
        final TextView t3 = findViewById(R.id.title3);
        final TextView d3 = findViewById(R.id.date3);
        final TextView t4 = findViewById(R.id.title4);
        final TextView d4 = findViewById(R.id.date4);
        final TextView t5 = findViewById(R.id.title5);
        final TextView d5 = findViewById(R.id.date5);
        final TextView l1 = findViewById(R.id.more1);
        final TextView l2 = findViewById(R.id.more2);
        final TextView l3 = findViewById(R.id.more3);
        final TextView l4 = findViewById(R.id.more4);
        final TextView l5 = findViewById(R.id.more5);
        final TextView topStates = findViewById(R.id.topStatesViewMore);
        final ImageView navButton = findViewById(R.id.navigationBarButton);
        final ImageView close = findViewById(R.id.close);
        final ConstraintLayout nav = findViewById(R.id.navigationDrawer);
        final TextView faq = findViewById(R.id.faq);
        final TextView state = findViewById(R.id.list);
        final TextView map = findViewById(R.id.mapView);
        final TextView viewMap = findViewById(R.id.map);
        final TextView myths = findViewById(R.id.myths);

        myths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, MythBusters.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, MapsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, MapsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, statewise.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mohfw.gov.in/pdf/FAQ.pdf")));
            }
        });

        nav.setVisibility(View.GONE);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.setVisibility(View.VISIBLE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.setVisibility(View.GONE);
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
                try {
                    obj = new JSONObject(s);
                    if (!obj.getString("S1").equals("")) {
                        s1h.setText(obj.getString("S1"));
                        s2h.setText(obj.getString("S2"));
                        s3h.setText(obj.getString("S3"));
                        s1c.setText(obj.getString("C1"));
                        s2c.setText(obj.getString("C2"));
                        s3c.setText(obj.getString("C3"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(dashboard.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }
        Login login = new Login();
        login.execute();

        count.setText(sharedPreferences.getString("confi", "0"));
        cases.setText(sharedPreferences.getString("cases", "0"));
        recov.setText(sharedPreferences.getString("recov", "0"));
        death.setText(sharedPreferences.getString("death", "0"));
        t1.setText(sharedPreferences.getString("t1", ""));
        t2.setText(sharedPreferences.getString("t2", ""));
        t3.setText(sharedPreferences.getString("t3", ""));
        t4.setText(sharedPreferences.getString("t4", ""));
        t5.setText(sharedPreferences.getString("t5", ""));
        d1.setText(sharedPreferences.getString("d1", ""));
        d2.setText(sharedPreferences.getString("d2", ""));
        d3.setText(sharedPreferences.getString("d3", ""));
        d4.setText(sharedPreferences.getString("d4", ""));
        d5.setText(sharedPreferences.getString("d5", ""));

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l1", "error"))));
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l2", "error"))));
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l3", "error"))));
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l4", "error"))));
            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l5", "error"))));
            }
        });

        topStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, statewise.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

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
