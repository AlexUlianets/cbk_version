package com.oplao.service;

import com.oplao.model.GeoLocation;
import com.oplao.model.TextWeatherMapping;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TextWeatherService {

    public TextWeatherMapping getTextWeatherMapping(DateTime dateTime){
        GeoLocation geoLocation = GeoIPv4.getLocation("94.126.240.2");
        URL url = null;

        try {
            url = new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=6&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + geoLocation.getCity());
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
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> currentCondition = (ArrayList<HashMap>)map.get("current_condition");
        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
        HashMap weatherData = weather.get(0);
        ArrayList<HashMap> hourly = (ArrayList<HashMap>)weatherData.get("hourly");
        HashMap hourlyHm = hourly.get(0);
        HashMap currentConditions = currentCondition.get(0);

        int maxTempC = Integer.parseInt(String.valueOf(weatherData.get("maxtempC")));
        int maxTempF = Integer.parseInt(String.valueOf(weatherData.get("maxtempF")));
        int minTempC = Integer.parseInt(String.valueOf(weatherData.get("mintempC")));
        int minTempF = Integer.parseInt(String.valueOf(weatherData.get("mintempF")));


        //TODO discover if precips should be hourly included instead of getting max by days...!!!
        HashMap precipMM = hourly.stream().max(Comparator.comparing(hashMap ->
                Double.parseDouble(String.valueOf(hashMap.get("precipMM")))
         )).orElse(new HashMap());

        double maxPrecipMM = Double.parseDouble(String.valueOf(precipMM.get("precipMM")));

        HashMap precipMM1 = hourly.stream().min(Comparator.comparing(hashMap ->
                Double.parseDouble(String.valueOf(hashMap.get("precipMM")))
        )).orElse(new HashMap());

        double minPrecipMM = Double.parseDouble(String.valueOf(precipMM1.get("precipMM")));

        HashMap maxWindMiles = hourly.stream().max(Comparator.comparing(hashMap ->
                Double.parseDouble(String.valueOf(hashMap.get("windspeedMiles"))))).orElse(new HashMap());

        double maxWindMS = Double.parseDouble(String.valueOf(maxWindMiles.get("windspeedMiles")));


        HashMap maxWindKm = hourly.stream().max(Comparator.comparing(hashMap ->
                Double.parseDouble(String.valueOf(hashMap.get("windspeedKmph"))))).orElse(new HashMap());

        double maxWindKmPh = Double.parseDouble(String.valueOf(maxWindKm.get("windspeedKmph")));


        return TextWeatherMapping.create(maxTempC, maxTempF, minTempC, minTempF, maxPrecipMM, minPrecipMM, 0,0, maxWindMS,maxWindKmPh,  false);
    }
}
