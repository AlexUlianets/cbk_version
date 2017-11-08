package com.oplao.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class LanguageService {

    public HashMap generateLanguageContent(String languageCode, String path, JSONObject currentCity){
        Locale locale = new Locale(languageCode, getCountryCode(languageCode));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);

        if(path.equals("/") || path.split("/").length==4){
          return generateFrontPageContent(resourceBundle, currentCity.getString("countryName"));
        }
        return null;
    }

    private String getCountryCode(String language){
        switch (language){
            case "en":
                return "US";
            case "us":
                return "US";
            case "ru":
                return "RU";
            case "ua":
                return "UA";
            case "by":
                return "BY";
            case "fr":
                return "FR";
            case "it":
                return "IT";
            case "de":
                return "DE";
        }
        return null;
    }

    private HashMap generateFrontPageContent(ResourceBundle bundle, String country){

        HashMap<String, String> map = new HashMap<>();
        map.put("searchTip", bundle.getString("searchTip"));
        map.put("feelsLike", bundle.getString("feelsLike"));
        map.put("humidity", bundle.getString("humidity"));
        map.put("pressure", bundle.getString("pressure"));
        map.put("wind", bundle.getString("wind"));
        map.put("fullForecast", bundle.getString("fullForecast"));



        map.put("temperatureMap", bundle.getString("temperatureMap"));
        map.put("viewMap", bundle.getString("viewMap"));
        map.put("locationWeather", MessageFormat.format(bundle.getString("locationWeather"), country));
        map.put("holidayWeather", bundle.getString("holidayWeather"));
        map.put("topHolidayDestinations", bundle.getString("topHolidayDestinations"));
        map.put("oplaoWeatherExtension", bundle.getString("oplaoWeatherExtension"));
        map.put("3DaysWeatherForecast", bundle.getString("3DaysWeatherForecast"));
        map.put("5DaysWeatherForecast", bundle.getString("5DaysWeatherForecast"));
        map.put("7DaysWeatherForecast", bundle.getString("7DaysWeatherForecast"));
        map.put("14DaysWeatherForecast", bundle.getString("14DaysWeatherForecast"));
        map.put("hourlyWeather", bundle.getString("hourlyWeather"));
        map.put("weatherMap", bundle.getString("weatherMap"));
        map.put("weatherHistory", bundle.getString("weatherHistory"));
        map.put("applications", bundle.getString("applications"));
        map.put("androidApps", bundle.getString("androidApps"));
        map.put("googleChromeWeatherExtension", bundle.getString("googleChromeWeatherExtension"));
        map.put("firefoxWeatherExtension", bundle.getString("firefoxWeatherExtension"));
        map.put("about", bundle.getString("about"));
        map.put("information", bundle.getString("information"));
        map.put("blog", bundle.getString("blog"));
        map.put("feedback", bundle.getString("feedback"));


        return map;
    }

}
