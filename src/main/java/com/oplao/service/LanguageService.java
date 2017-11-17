package com.oplao.service;

import com.oplao.Utils.LanguageUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class LanguageService {

    public HashMap generateLanguageContent(String languageCode, String path, JSONObject currentCity){
        Locale locale = new Locale(languageCode, LanguageUtil.getCountryCode(languageCode));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_"+languageCode, locale);

        if(path.contains("widgets")){
            return generateWidgetContent(resourceBundle);
        }else if(path.equals("/") || path.split("/").length==4&&!path.contains("widgets")){
          return generateFrontPageContent(resourceBundle, currentCity.getString("countryName"));
        }else if(path.contains("outlook")){
            return generateOutlookContent(resourceBundle, currentCity.getString("name"));
        }else if(path.contains("today")|| path.contains("tomorrow")){
            return generateTodayTomorrowContent(resourceBundle, path.contains("today"));
        }else if(path.contains("history")){
            return generatePastWeatherContent(resourceBundle);
        }else if(path.contains("hour-by-hour")){
            return generateHourlyContent(resourceBundle);
        } else if(path.contains("3")||path.contains("7")||path.contains("14")){
            return generateUniversalDaysContent(resourceBundle, path.contains("3"), path.contains("7"));
        }else if(path.contains("5")||path.contains("10")){
            return generateNotUniversalDaysContent(resourceBundle, path.contains("5"));
        } else if (path.contains("map")) {
            return generateTemperatureMapContent(resourceBundle);
        }
        return null;
    }

    private HashMap<String, String> generateWidgetContent(ResourceBundle bundle){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("widgetsHeader", encode(bundle.getString("widgetsHeader")));
        map.put("chooseLocation", encode(bundle.getString("chooseLocation")));
        map.put("chooseWidgetSize", encode(bundle.getString("chooseWidgetSize")));
        map.put("selectLanguage", encode(bundle.getString("selectLanguage")));
        map.put("customizeParameters", encode(bundle.getString("customizeParameters")));
        map.put("addTheseNumbers", encode(bundle.getString("addTheseNumbers")));
        map.put("refreshNumbers", encode(bundle.getString("refreshNumbers")));
        map.put("copyCode", encode(bundle.getString("copyCode")));
        map.put("getTheCode", encode(bundle.getString("getTheCode")));
        map.put("adoptingTheTermsOfUse", encode(bundle.getString("adoptingTheTermsOfUse")));
        return map;
    }
    private HashMap generateHourlyContent(ResourceBundle bundle){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("aboveGraph", encode(bundle.getString("aboveGraphHourly")));
        map.put("aboveTable", encode(bundle.getString("aboveTableHourly")));
        map.put("oneHour", encode(bundle.getString("oneHour")));
        map.put("threeHour", encode(bundle.getString("threeHour")));
        return map;
    }
    private HashMap generatePastWeatherContent(ResourceBundle bundle){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("aboveTable", encode(bundle.getString("aboveTablePastWeather")));
        map.put("aboveGraph", encode(bundle.getString("aboveGraphPastWeather")));
        map.put("pickADate", encode(bundle.getString("pickDate")));
        map.put("oneHour", encode(bundle.getString("oneHour")));
        map.put("threeHour", encode(bundle.getString("threeHour")));
        return map;
    }
    private HashMap generateTemperatureMapContent(ResourceBundle bundle){
        HashMap map = generateMainContent(bundle);
        map.put("aboveTable", encode(bundle.getString("aboveTableTempMap")));
        return map;
    }
    private HashMap generateNotUniversalDaysContent(ResourceBundle bundle, boolean is5){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("aboveTable", encode(is5?bundle.getString("aboveTable5Days"):bundle.getString("aboveTable10Days")));
        map.put("aboveGraph", encode(is5?bundle.getString("aboveGraph5Days"):bundle.getString("aboveGraph10Days")));
        return map;
    }
    private HashMap generateUniversalDaysContent(ResourceBundle bundle, boolean is3, boolean is7){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("aboveTable", encode(is3?bundle.getString("aboveTable3Days")
                :is7?bundle.getString("aboveTable7Days")
                :bundle.getString("aboveTable14Days")));
        map.put("aboveGraph", encode(is3?bundle.getString("aboveGraph3Days")
                :is7?bundle.getString("aboveGraph7Days")
                :bundle.getString("aboveGraph14Days")));
        return map;
    }
    private HashMap generateTodayTomorrowContent(ResourceBundle bundle, boolean isToday){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("sunrise", encode(bundle.getString("sunrise")));
        map.put("sunset", encode(bundle.getString("sunset")));
        map.put("aboveTable", encode(isToday?bundle.getString("aboveTableToday"):bundle.getString("aboveTableTomorrow")));
        map.put("aboveGraph", encode(isToday?bundle.getString("aboveGraphToday"):""));

        return map;
    }
    private HashMap generateFrontPageContent(ResourceBundle bundle, String country){

        HashMap<String, String> map = generateMainContent(bundle);
        map.put("viewMap", encode(bundle.getString("viewMap")));
        String locWeather = encode(bundle.getString("locationWeather"));
        map.put("locationWeather", MessageFormat.format(locWeather,country));
        map.put("holidayWeather", encode(bundle.getString("holidayWeather")));
        map.put("topHolidayDestinations", encode(bundle.getString("topHolidayDestinations")));
        return map;
    }

    private HashMap<String, String> generateOutlookContent(ResourceBundle bundle, String city){
        HashMap<String, String> map = generateMainContent(bundle);
        map.put("longTermForecast", encode(bundle.getString("longTermForecast")));
        map.put("date", encode(bundle.getString("date")));
        map.put("aboveGraph", encode(bundle.getString("aboveGraphOutlook")));
        map.put("aboveTable", encode(bundle.getString("aboveTableOutlook")));
        String climIn = encode(bundle.getString("climateInWeather"));
        map.put("climateIn", MessageFormat.format(climIn, city));
        map.put("coordinates", encode(bundle.getString("coordinates")));
        map.put("last5YearWeatherData", encode(bundle.getString("last5YearWeatherData")));
        map.put("uvIndex", encode(bundle.getString("uvIndex")));
        map.put("uvIndex1", encode(bundle.getString("uvIndex.1")));
        map.put("uvIndex2", encode(bundle.getString("uvIndex.2")));
        map.put("uvIndex3", encode(bundle.getString("uvIndex.3")));
        map.put("uvIndex4", encode(bundle.getString("uvIndex.4")));
        map.put("uvIndex5", encode(bundle.getString("uvIndex.5")));

        return map;
    }
    private HashMap<String, String> generateMainContent(ResourceBundle bundle){
        HashMap<String, String> map = new HashMap<>();
        map.put("searchTip", encode(bundle.getString("searchTip")));
        map.put("feelsLike", encode(bundle.getString("feelsLike")));
        map.put("humidity", encode(bundle.getString("humidity")));
        map.put("pressure", encode(bundle.getString("pressure")));
        map.put("wind", encode(bundle.getString("wind")));
        map.put("time", encode(bundle.getString("time")));
        map.put("precipChance", encode(bundle.getString("precipChance")));
        map.put("precip", encode(bundle.getString("precip")));
        map.put("gust", encode(bundle.getString("gust")));
        map.put("cloud", encode(bundle.getString("cloud")));
        map.put("temperature", encode(bundle.getString("temperature")));
        map.put("fullForecast", encode(bundle.getString("fullForecast")));
        map.put("androidApps", encode(bundle.getString("androidApps")));
        map.put("googleChromeWeatherExtension", encode(bundle.getString("googleChromeWeatherExtension")));
        map.put("firefoxWeatherExtension", encode(bundle.getString("firefoxWeatherExtension")));
        map.put("operaWeatherExtension", encode(bundle.getString("operaWeatherExtension")));
        map.put("about", encode(bundle.getString("about")));
        map.put("information", encode(bundle.getString("information")));
        map.put("blog", encode(bundle.getString("blog")));
        map.put("feedback", encode(bundle.getString("feedback")));
        map.put("oplaoWeatherExtension", encode(bundle.getString("oplaoWeatherExtension")));
        map.put("threeDaysWeatherForecast", encode(bundle.getString("3DaysWeatherForecast")));
        map.put("fiveDaysWeatherForecast", encode(bundle.getString("5DaysWeatherForecast")));
        map.put("sevenDaysWeatherForecast", encode(bundle.getString("7DaysWeatherForecast")));
        map.put("fourteenDaysWeatherForecast", encode(bundle.getString("14DaysWeatherForecast")));
        map.put("hourlyWeather", encode(bundle.getString("hourlyWeather")));
        map.put("applications", encode(bundle.getString("applications")));
        map.put("weather", encode(bundle.getString("weather")));
        map.put("weatherMap", encode(bundle.getString("weatherMap")));
        map.put("weatherHistory", encode(bundle.getString("weatherHistory")));
        map.put("widgets", encode(bundle.getString("widgets")));
        map.put("alerts", encode(bundle.getString("alerts")));
        map.put("outlook", encode(bundle.getString("outlook")));
        map.put("today", encode(bundle.getString("today")));
        map.put("tomorrow", encode(bundle.getString("tomorrow")));
        map.put("pastWeather", encode(bundle.getString("pastWeather")));
        map.put("temperatureMap", encode(bundle.getString("temperatureMap")));
        map.put("threeDay", encode(bundle.getString("3day")));
        map.put("fiveDay", encode(bundle.getString("5day")));
        map.put("sevenDay", encode(bundle.getString("7day")));
        map.put("tenDay", encode(bundle.getString("10day")));
        map.put("fourteenDay", encode(bundle.getString("14day")));
        map.put("hourByHour", encode(bundle.getString("hourByHour")));
        map.put("weeklyWeatherSummary", encode(bundle.getString("weeklyWeatherSummary")));
        map.put("maxTemperature", encode(bundle.getString("maxTemperature")));
        map.put("minTemperature", encode(bundle.getString("minTemperature")));
        map.put("next7Days", encode(bundle.getString("next7Days")));
        map.put("averageMaxTemperature", encode(bundle.getString("averageMaxTemperature")));
        map.put("averageMinTemperature", encode(bundle.getString("averageMinTemperature")));
        map.put("totalRainfall", encode(bundle.getString("totalRainfall")));
        map.put("windiest", encode(bundle.getString("windiest")));
        map.put("astronomy", encode(bundle.getString("astronomy")));
        map.put("onMap", encode(bundle.getString("onMap")));
        map.put("checkPastWeather", encode(bundle.getString("checkPastWeather")));
        map.put("min", encode(bundle.getString("min")));
        map.put("max", encode(bundle.getString("max")));
        map.put("precipitation", encode(bundle.getString("precipitation")));
        map.put("timezone", encode(bundle.getString("timezone")));
        map.put("visibility", encode(bundle.getString("visibility")));
        return map;
    }


    public static String encode(String value) {

        try {
            return new String(value.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
