package com.z3t4z00k.c19meter;

import android.util.Log;

public class DistrictModal {
    private String district, t, c, d;

    DistrictModal(String district, String t, String c, String d){
        this.district = district;
        this.t = t;
        this.c = c;
        this.d = d;
        Log.d("DistrictModal", "Object created- " + this.district + " | " + this.t + " | " + this.c + " | " + this.d );
    }

    String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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