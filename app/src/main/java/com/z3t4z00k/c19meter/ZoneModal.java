package com.z3t4z00k.c19meter;

import android.util.Log;

public class ZoneModal {
    private String district;
    private String color;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ZoneModal (String d, String c){
        this.district = d;
        this.color = c;
        Log.d("ZoneModel", "D- " + this.district + " C- " + this.color);
    }
}
