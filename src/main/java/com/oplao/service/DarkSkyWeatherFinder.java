package com.oplao.service;

import com.oplao.Application;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.HashMap;

import static com.oplao.service.WeatherService.readJsonFromUrl;

public class DarkSkyWeatherFinder {

    static final String BASE_URL = "https://api.darksky.net/forecast/bcc623e3068ad876a73a0dd4c8eedc65/";

    private String lat;
    private String lon;
    private long timeStamp;


    public DarkSkyWeatherFinder() {
    }

    public DarkSkyWeatherFinder(String lat, String lon, long timeStamp) {
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static JSONObject findWeatherByTimeStamp(String lat, String lon, long timeStamp){
        JSONObject jsonObject = null;
        try {
            jsonObject = readJsonFromUrl(BASE_URL + lat + "," + lon + "," + String.valueOf(timeStamp).substring(0,10));
        }catch (IOException e){
            Application.log.warning("url-generated request error");
            e.printStackTrace();
        }
        return jsonObject;
    }
}
