package com.oplao.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.oplao.Utils.AddressGetter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SearchService {
    public static final String cookieName = "lastCitiesVisited";
    public List<HashMap> findSearchOccurences(String searchRequest){
        List list = null;
        if((Character.isDigit(searchRequest.trim().charAt(0)) || Character.isDigit(searchRequest.trim().charAt(1))) && searchRequest.contains(",")){
            String [] parsedRequest = searchRequest.split(",");
            String lat = "";
            String lon = "";
            if(parsedRequest.length==4){
                lat= parsedRequest[0].trim() + "." + parsedRequest[1].replaceAll("°", "").trim();
                lon = parsedRequest[2].trim() +"." +parsedRequest[3].replaceAll("°", "").trim();
            }else {
                lat = parsedRequest[0].replace(",", ".").replaceAll("°", "").trim();
                try {
                    lon = parsedRequest[1].replaceAll(",", ".").replaceAll("°", "").trim();
                }catch (ArrayIndexOutOfBoundsException e){
                }
            }

            if(lat.contains(".")&& lon.contains(".")) {
                list = findByCoordinates(lat, lon);
            }else {
                return null;
            }
        }else if(searchRequest.length()==3 && Objects.equals(searchRequest, searchRequest.toUpperCase())) {
            list = findByAirports(searchRequest);
        }
        else {
            try {
                list = findByCity(searchRequest);
            }catch (NullPointerException e){
            }
        }
        try {
            List<HashMap> maps = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                maps.add((HashMap) ((JSONObject) list.get(i)).toMap());
            }
            return maps;
        }catch (NullPointerException e){
        }
        return new ArrayList<>();
    }


    public HttpStatus selectCity(int geonameId, String currentCookieValue, HttpServletRequest request, HttpServletResponse response){
        List<JSONObject> list = null;
        JSONObject city = null;
        try{
            try {
                list = findByGeonameId(geonameId);
                city = list.get(0);
            }catch (Exception e){
                list = findByGeonameIdAirports(geonameId);
                city = list.get(0);
            }

            city.put("status", "selected");
            JSONArray arr = new JSONArray(currentCookieValue);
            for (int i = 0; i < arr.length(); i++) {
                arr.getJSONObject(i).put("status", "unselected");
            }

            if(checkDuplicateCookie(request, response,city) != 0) {
                if(arr.length()>4){
                    arr.remove(4);
                    arr.put(4, city);
                }else{
                    arr.put(city);
                }
                clearCookies(request, response);

                Cookie c = new Cookie(SearchService.cookieName, arr.toString());
                c.setMaxAge(60 * 60 * 24);
                c.setPath("/");
                response.addCookie(c);

                return HttpStatus.OK;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return HttpStatus.OK;
    }


    private int checkDuplicateCookie(HttpServletRequest request, HttpServletResponse response,
                                     JSONObject city){
        for (int i = 0; i < request.getCookies().length; i++) {
            if(request.getCookies()[i].getName().equals(SearchService.cookieName)){
                JSONArray array = new JSONArray(request.getCookies()[i].getValue());
                for (int j = 0; j < array.length(); j++) {
                    if(Objects.equals("" + (array.getJSONObject(j)).get("geonameId"),
                            "" + city.get("geonameId"))){
                        setCitySelected(array, j);
                        clearCookies(request, response);

                        Cookie c = new Cookie(SearchService.cookieName, array.toString());
                        c.setMaxAge(60 * 60 * 24);
                        c.setPath("/");
                        response.addCookie(c);

                        return 0;
                    }
                }
            }
        }
        return 1;
    }

    private void setCitySelected(JSONArray array, int index){

        for (int i = 0; i < array.length(); i++) {
            array.getJSONObject(i).put("status", "unselected");
        }

        array.getJSONObject(index).put("status", "selected");
    }

    private static List<JSONObject> findByOccurences(String url) throws IOException, JSONException {
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

    private List<JSONObject> findByCoordinates(String lat, String lon){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&lat=" + lat + "&lng=" + lon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<JSONObject> findByGeonameIdAirports(int geonameId){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&geonameId=" + geonameId + "&featureClass=S");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<JSONObject> findByGeonameId(int geonameId){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&geonameId=" + geonameId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    private List<JSONObject> findByAirports(String name){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts="+name.trim()+"&featureClass=S");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<JSONObject> findByCity(String city){
        List<JSONObject> list = null;
        try {
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + city.replaceAll(" ", "%20"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
   private void clearCookies(HttpServletRequest request, HttpServletResponse response){
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
           CityResponse cityResponse = null;
          try {
              File database = new File("src\\main\\resources\\GeoLite2-City.mmdb");
              DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
              cityResponse = dbReader.city(InetAddress.getByName(AddressGetter.getCurrentIpAddress(request)));
           } catch (IOException | GeoIp2Exception e) {
               e.printStackTrace();
           }

           List<JSONObject> list = null;
           try {
               list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + cityResponse.getCity().getName());
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
            data.add(getRecentCityInfo(array.getJSONObject(i)));
        }
       return data;
    }


    private HashMap getRecentCityInfo(JSONObject city){

        String cityName = city.getString("asciiName");
        if(cityName.contains("'")){
            cityName = cityName.replace("'", "");
        }
        cityName = cityName.replace(" ", "%20");
        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        JSONObject jsonObject = null;
        try {
            jsonObject = WeatherService.readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date=" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() + "&q=" + cityName);
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        HashMap hourly = ((HashMap)((ArrayList)((HashMap)((ArrayList)map.get("weather")).get(0)).get("hourly")).get(dateTime.getHourOfDay()));

        HashMap<String, Object> result = new HashMap<>();
        result.put("weatherCode",(WeatherService.EXT_STATES.get(Integer.parseInt((String)hourly.get("weatherCode")))));
        result.put("tempC", hourly.get("tempC"));
        result.put("tempF", hourly.get("tempF"));
        result.put("city", cityName.replace("%20", " "));
        result.put("countryCode", city.getString("countryCode"));
        result.put("geonameId", city.getInt("geonameId"));
        result.put("hours", dateTime.getHourOfDay());
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
