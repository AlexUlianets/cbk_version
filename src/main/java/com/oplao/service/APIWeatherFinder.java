package com.oplao.service;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

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

        URL url = null;
        UrlBuilder urlBuilder = new UrlBuilder();
        try {
            url = new URL(urlBuilder.buildUrl(this));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection con = null;
        String body = "";
        try {
            con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            body = IOUtils.toString(in, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject(body);

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