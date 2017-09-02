package com.oplao.model;

public class DetailedForecastGraphMapping extends TempMapping{

    private int dayOfMonth;
    private int monthOfYear;
    private String dayOWeek;
    private double precip;

    public DetailedForecastGraphMapping(int tempC, int tempF, int dayOfMonth, int monthOfYear, String dayOWeek, double precip) {
        super(tempC, tempF);
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.dayOWeek = dayOWeek;
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
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public String getDayOWeek() {
        return dayOWeek;
    }

    public void setDayOWeek(String dayOWeek) {
        this.dayOWeek = dayOWeek;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }
}
