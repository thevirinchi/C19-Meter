package com.z3t4z00k.c19meter;

import android.util.Log;

public class ResourceModel {
    private String cit, org, des, web, pho;

    public ResourceModel (String c, String o, String d, String w, String p){
        this.cit = c;
        this.org = o;
        this.des = d;
        this.web = w;
        if(p.contains(",\n"))
            this.pho = p.substring(0, p.indexOf(","));
        else
            this.pho = p;
        Log.d("ResourceModel", "C-" + this.cit+ " O-" + this.org + " D-" + this.des + " W-" + this.web + " P-" + this.pho);
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPho() {
        return pho;
    }

    public void setPho(String pho) {
        this.pho = pho;
    }

    public String getCit() {
        return cit;
    }

    public void setCit(String cit) {
        this.cit = cit;
    }
}
