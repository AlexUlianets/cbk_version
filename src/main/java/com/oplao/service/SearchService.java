package com.oplao.service;

import com.oplao.Utils.AddressGetter;
import com.oplao.model.GeoLocation;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    public static List<JSONObject> cookiedMaps = new ArrayList<>();
    public static Cookie cookie = new Cookie("lastCitiesVisited", "");

    public static List<JSONObject> findByOccurences(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = WeatherService.readAll(rd);
            List<JSONObject> objects = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(jsonText);
            for (int i = 0; i < jsonArray.length(); i++) {
                objects.add((JSONObject)jsonArray.get(i));
            }
            return objects;
        } finally {
            is.close();
        }
    }

   public void clearCookies(HttpServletRequest request, HttpServletResponse response){
       for (Cookie c: request.getCookies()){
           c.setValue("");
           c.setPath("/");
           c.setMaxAge(0);
           response.addCookie(c);
       }
   }


   public void setCookieSelected(int elem){
       for (Object cookiedMap : SearchService.cookiedMaps) {
           ((JSONObject) cookiedMap).remove("status");
           ((JSONObject) cookiedMap).put("status", "unselected");
       }
       (SearchService.cookiedMaps.get(elem)).remove("status");
       (SearchService.cookiedMaps.get(elem)).put("status", "selected");
   }

   public void setCurrentLocationCookie(HttpServletRequest request, HttpServletResponse response){
       GeoLocation geoLocation = GeoIPv4.getLocation(AddressGetter.getCurrentIpAddress(request));

       List list = null;
       try {
           list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=ru&max=10&nameStarts=" + geoLocation.getCity());
       } catch (IOException e) {
           e.printStackTrace();
       }
       if(!list.isEmpty()) {

           HashMap city = (HashMap) ((JSONObject) list.get(0)).toMap();
           if(!city.containsKey("status")){
               city.put("status", "selected");
           }else {
               city.replace("status", "selected");
           }

           JSONObject object = new JSONObject(city);
           if(!SearchService.cookiedMaps.isEmpty()){
               SearchService.cookiedMaps.remove(0);
               SearchService.cookiedMaps.add(0, object);
           }else {
               SearchService.cookiedMaps.add(0, object);
           }

           if(!SearchService.cookiedMaps.isEmpty()) {
               for (int i = 1; i < SearchService.cookiedMaps.size(); i++) {
                   (cookiedMaps.get(i)).remove("status");
                   (cookiedMaps.get(i)).put("status", "unselected");
               }
           }
           if(request.getCookies()!=null) {
               clearCookies(request, response);
           }

           SearchService.cookie.setValue(SearchService.cookiedMaps.toString());
           response.addCookie(SearchService.cookie);
       }
   }

   public JSONObject getSelectedCity(){

           JSONObject selectedCity = cookiedMaps.stream().filter(jsonObject ->
                   jsonObject.get("status").equals("selected")).collect(Collectors.toList()).get(0);

       return selectedCity;
   }


    public List<HashMap> createRecentCitiesTabs(){

       List<HashMap> data = new ArrayList<>();

        for (int i = 0; i < cookiedMaps.size(); i++) {
            data.add(getRecentCityInfo((String) cookiedMaps.get(i).toMap().get("asciiName"),(String) cookiedMaps.get(i).toMap().get("countryCode")));
        }
       return data;
    }


    private HashMap getRecentCityInfo(String city, String coutryCode){

        if(city.contains("'")){
            city = city.replace("'", "");
        }
        DateTime dateTime = new DateTime();
        JSONObject jsonObject = null;
        try {
            jsonObject = WeatherService.readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=24&date=" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() + "&q=" + city);
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        HashMap hourly = ((HashMap)((ArrayList)((HashMap)((ArrayList)map.get("weather")).get(0)).get("hourly")).get(0));

        HashMap<String, Object> result = new HashMap<>();
        result.put("weatherCode",(WeatherService.EXT_STATES.get(Integer.parseInt((String)hourly.get("weatherCode")))));
        result.put("tempC", hourly.get("tempC"));
        result.put("tempF", hourly.get("tempF"));
        result.put("city", city);
        result.put("countryCode", coutryCode);
        return result;
    }
}
