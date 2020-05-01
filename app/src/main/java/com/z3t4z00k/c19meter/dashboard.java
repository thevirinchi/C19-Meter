package com.z3t4z00k.c19meter;

import android.os.Bundle;

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

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        LineChart casesChart = findViewById(R.id.casesChart);
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
        deathsChart.invalidate();
    }
}
