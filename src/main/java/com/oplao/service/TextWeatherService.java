package com.oplao.service;

import com.oplao.model.TextWeatherMapping;
import org.joda.time.DateTime;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TextWeatherService {

    public TextWeatherMapping getTextWeatherMapping(DateTime dateTime, JSONObject city){

        String cityName = ((String)city.get("asciiName"));
        if(cityName.contains("'")){
            cityName = cityName.replace("'", "");
        }

        cityName = cityName.replace(" ", "%20");
  APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, cityName,
          new DateTime().isAfter(dateTime), false, 3 );


  HashMap map = apiWeatherFinder.findWeatherByDate();

        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
        HashMap weatherData = weather.get(0);
        ArrayList<HashMap> hourly = (ArrayList<HashMap>)weatherData.get("hourly");

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
