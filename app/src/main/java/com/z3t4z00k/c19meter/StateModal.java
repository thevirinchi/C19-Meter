package com.z3t4z00k.c19meter;

import android.util.Log;

public class StateModal {
    private String state, t, c, d;

    StateModal(String state, String t, String c, String d){
        this.state = state;
        this.t = t;
        this.c = c;
        this.d = d;
        Log.d("StateModal", "Object created- " + this.state + " | " + this.t + " | " + this.c + " | " + this.d );
    }

    String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
