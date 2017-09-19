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
import java.util.Objects;

@Service
public class SearchService {
    public static final String cookieName = "lastCitiesVisited";

    public static List<JSONObject> findByOccurences(String url) throws IOException, JSONException {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
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

    public List<JSONObject> findByCoordinates(String lat, String lon){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&lat=" + lat + "&lng=" + lon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> findByGeonameIdAirports(int geonameId){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&geonameId=" + geonameId + "&featureClass=S");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> findByGeonameId(int geonameId){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&geonameId=" + geonameId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<JSONObject> findByAirports(String name){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts="+name.trim()+"&featureClass=S");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<JSONObject> findByCity(String city){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + city.replaceAll(" ", "%20"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
   public void clearCookies(HttpServletRequest request, HttpServletResponse response){
       for (int i = 0; i < request.getCookies().length; i++) {
               request.getCookies()[i].setPath("/");
               request.getCookies()[i].setValue("");
               request.getCookies()[i].setMaxAge(0);
               response.addCookie(request.getCookies()[i]);
       }
   }


   public JSONObject findSelectedCity(HttpServletRequest request, HttpServletResponse response, String currentCookieValue){

       if(!Objects.equals(currentCookieValue, "")){
       JSONArray array = new JSONArray(currentCookieValue);
       for (int i = 0; i < array.length(); i++) {
           if(array.getJSONObject(i).get("status").equals("selected")){
               return array.getJSONObject(i);
           }
       }
       }else {
           GeoLocation geoLocation = GeoIPv4.getLocation(AddressGetter.getCurrentIpAddress(request));

           List<JSONObject> list = null;
           try {
               list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + geoLocation.getCity());
               JSONObject obj = list.get(0);
               obj.put("status", "selected");
               JSONArray arr = new JSONArray("["+obj.toString()+"]");
               Cookie c = new Cookie(cookieName,arr.toString());
               c.setMaxAge(60 * 60 * 24);
               c.setPath("/");
               response.addCookie(c);
               return obj;
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return null;
   }


    public List<HashMap> createRecentCitiesTabs(String currentCookieValue){

       JSONArray array = new JSONArray(currentCookieValue);

       ArrayList<HashMap> data = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            data.add(getRecentCityInfo((String) array.getJSONObject(i).get("asciiName"),(String) array.getJSONObject(i).get("countryCode"), String.valueOf(array.getJSONObject(i).get("geonameId"))));
        }
       return data;
    }


    private HashMap getRecentCityInfo(String city, String coutryCode, String geonameId){

        if(city.contains("'")){
            city = city.replace("'", "");
        }
        city = city.replace(" ", "%20");
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
        result.put("city", city.replace("%20", " "));
        result.put("countryCode", coutryCode);
        result.put("geonameId", geonameId);
        return result;
    }

    public Cookie deleteCity(String currentCookieValue, String geonameId, HttpServletRequest request, HttpServletResponse response){

        JSONArray array = new JSONArray(currentCookieValue);

        for (int i = 0; i < array.length(); i++) {
            if(String.valueOf(array.getJSONObject(i).get("geonameId")).equals(geonameId)){
                array.remove(i);
                clearCookies(request, response);
                Cookie c = new Cookie(cookieName,array.toString());
                c.setMaxAge(60 * 60 * 24);
                c.setPath("/");
                return c;
            }
        }

        return null;

    }
}
