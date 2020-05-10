package com.z3t4z00k.c19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

public class State extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String s = Objects.requireNonNull(bundle).getString("s");
        Log.d("State", Objects.requireNonNull(s));


    }
}
