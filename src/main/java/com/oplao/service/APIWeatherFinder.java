package com.oplao.service;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import static com.oplao.service.WeatherService.readJsonFromUrl;

public class APIWeatherFinder {

     static final String BASE_URL = "http://api.worldweatheronline.com/premium/v1/";
    static final String KEY = "?key=gwad8rsbfr57wcbvwghcps26";
    static final String FORMAT = "json";

    DateTime dateTime;
    String city;
    boolean weatherForPast;
    boolean currentConditions;
    int hourDifference;

    public APIWeatherFinder(DateTime dateTime, String city, boolean weatherForPast, boolean currentConditions, int hourDifference) {
        this.dateTime = dateTime;
        this.city = city;
        this.weatherForPast = weatherForPast;
        this.currentConditions = currentConditions;
        this.hourDifference = hourDifference;
    }
    public APIWeatherFinder(){

    }


    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getKEY() {
        return KEY;
    }

    public static String getFORMAT() {
        return FORMAT;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isWeatherForPast() {
        return weatherForPast;
    }

    public void setWeatherForPast(boolean weatherForPast) {
        this.weatherForPast = weatherForPast;
    }

    public boolean isCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(boolean currentConditions) {
        this.currentConditions = currentConditions;
    }

    public int getHourDifference() {
        return hourDifference;
    }

    public void setHourDifference(int hourDifference) {
        this.hourDifference = hourDifference;
    }

    public HashMap findWeatherByDate(){

        UrlBuilder urlBuilder = new UrlBuilder();
        JSONObject jsonObject = null;
            try {
                jsonObject = readJsonFromUrl(urlBuilder.buildUrl(this));
            }catch (IOException e){
                e.printStackTrace();
            }
        return (HashMap)jsonObject.toMap().get("data");



    }

}

class UrlBuilder{

    String buildUrl(APIWeatherFinder finder){
        String url = finder.getBaseUrl();

        StringBuilder builder = new StringBuilder();


        builder.append(url);
        if(finder.isWeatherForPast()){
            builder.append("past-weather.ashx");
        }else{
            builder.append("weather.ashx");
        }

        builder.append(finder.KEY);

        builder.append("&format=json");

        if(finder.isCurrentConditions()){
            builder.append("&cc=yes");
        }else {
            builder.append("&cc=no");
        }

        DateTime dt= finder.getDateTime();
        String year = dt.getYear() + "";
        String month = dt.getMonthOfYear()>9?dt.getMonthOfYear()+"":"0"+dt.getMonthOfYear();
        String day = dt.getDayOfMonth()>9?dt.getDayOfMonth()+"":"0"+dt.getDayOfMonth();
        builder.append("&tp=" + finder.getHourDifference());
        builder.append(
                "&date=" +
                        year+ "-"
                 +month + "-" + day
        );

        builder.append("&q=" + finder.getCity());



        return builder.toString();
    }
}