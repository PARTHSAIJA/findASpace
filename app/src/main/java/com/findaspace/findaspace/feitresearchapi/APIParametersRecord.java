package com.findaspace.findaspace.feitresearchapi;

public class APIParametersRecord {

    public String rFromDate;
    public String rToDate;
    public String rFamily;
    public String rSensor;
    public String rSubSensor;

    public APIParametersRecord(String rFromDate, String rToDate, String rFamily, String rSensor, String rSubSensor) {
        this.rFromDate = rFromDate;
        this.rToDate = rToDate;
        this.rFamily = rFamily;
        this.rSensor = rSensor;
        this.rSubSensor = rSubSensor;
    }

    public String getrFromDate(String rFromDate) {
        return rFromDate;
    }

    public void setrFromDate(String rFromDate) {
        this.rFromDate = rFromDate;
    }

    public String getrToDate() {
        return rToDate;
    }

    public void setrToDate(String rToDate) {
        this.rToDate = rToDate;
    }

    public String getrFamily() {
        return rFamily;
    }

    public void setrFamily(String rFamily) {
        this.rFamily = rFamily;
    }

    public String getrSensor() {
        return rSensor;
    }

    public void setrSensor(String rSensor) {
        this.rSensor = rSensor;
    }

    public String getrSubSensor() {
        return rSubSensor;
    }

    public void setrSubSensor(String rSubSensor) {
        this.rSubSensor = rSubSensor;
    }

}
