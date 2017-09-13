package com.oplao.Utils;

import com.oplao.model.GeoLocation;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;

import static com.oplao.service.WeatherService.readJsonFromUrl;

public class CityToTimeZoneConverter {

    public static String convert(GeoLocation geoLocation){
        JSONObject jsonObject = null;
        Timestamp timestamp = new Timestamp(new DateTime().getMillis());
        try{
jsonObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/timezone/json?location="+ geoLocation.getLatitude()+","+ geoLocation.getLongitude()+"&timestamp="+timestamp.getNanos());
        }catch (IOException e){
            e.printStackTrace();
        }
        return (String) jsonObject.get("timeZoneId");
    }
    public static String convert(JSONObject city){
        JSONObject jsonObject = null;
        Timestamp timestamp = new Timestamp(new DateTime().getMillis());
        try{
            jsonObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/timezone/json?location="+ city.getDouble("lat")+","+ city.getDouble("lng") +"&timestamp="+timestamp.getNanos());
        }catch (IOException e){
            e.printStackTrace();
        }
        return (String) jsonObject.get("timeZoneId");
    }
}
