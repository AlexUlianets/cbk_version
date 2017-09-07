package com.oplao.model;

public class DetailedForecastGraphMapping extends TempMapping{

    private String date;
    private double precip;
    private String weatherIcon;

    public DetailedForecastGraphMapping(int tempC, int tempF, String date, double precip, String weatherIcon) {
        super(tempC, tempF);
        this.date = date;
        this.precip = precip;
        this.weatherIcon = weatherIcon;
    }


    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
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
