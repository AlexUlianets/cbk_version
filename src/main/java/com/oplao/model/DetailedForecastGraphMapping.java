package com.oplao.model;

public class DetailedForecastGraphMapping extends TempMapping{

    private String date;
    private double precip;

    public DetailedForecastGraphMapping(int tempC, int tempF, String date, double precip) {
        super(tempC, tempF);
        this.date = date;
        this.precip = precip;
    }


    public int getTempC(){
        return super.getTempC();
    }

    public int getTempF(){
        return super.getTempF();
    }

    public void setTempC(int tempC){
        super.setTempC(tempC);
    }

    public void setTempF(int tempF){
         super.setTempF(tempF);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }
}
