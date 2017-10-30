package com.oplao.service;

import com.oplao.Application;
import com.oplao.Utils.AddressGetter;
import com.oplao.model.GeoLocation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class SearchService {
    public static final String cookieName = "lastCitiesVisited";
    private static final List<String> cities = Arrays.asList(new String[]{"Hong Kong", " Singapore", " Bangkok", " London", " Macau", " Kuala Lumpur", " Shenzhen", " New York City", " Antalya", " Paris", " Istanbul", " Rome", " Dubai", " Guangzhou", " Phuket", " Mecca", " Pattaya", " Prague", " Shanghai", " Las Vegas", " Miami", " Barcelona", " Moscow", " Beijing", " Los Angeles", " Budapest", " Vienna", " Amsterdam", " Sofia", " Madrid", " Orlando", " Ho Chi Minh City", " Lima", " Berlin", " Tokyo", " Warsaw", " Chennai", " Cairo", " Nairobi", " Hangzhou", " Milan", " San Francisco", " Buenos Aires", " Venice", " Mexico City", " Dublin", " Seoul", " Mugla", " Mumbai", " Denpasar", " Delhi", " Toronto", " Zhuhai", " Saint Petersburg", " Burgas", " Sydney", " Djerba", " Munich", " Johannesburg", " Cancun", " Edirne", " Suzhou", " Bucharest", " Punta Cana", " Agra", " Jaipur", " Brussels", " Nice", " Chiang Mai", " Sharm el-Sheikh", " Lisbon", " Porto", " Marrakech", " Jakarta", " Manama", " Honolulu", " Vietnam", " Manila", " Guilin", " Auckland", " Siem Reap", " Sousse", " Amman", " Vancouver", " Abu Dhabi", " Kiev", " Doha", " Florence", " Rio de Janeiro", " Melbourne", " Washington D.C.", " Riyadh", " Christchurch", " Frankfurt", " Baku", " Sao Paulo", " Harare", " Kolkata", " Nanjing", " Athens", " Copenhagen", " Edinburgh", " Stockholm", " Oslo", " Oxford", " Cannes", " Helsinki", " Bruges", " Hamburg", " Pisa", " Dubrovnik", " Tallinn", " Granada", " Salzburg", " Bilbao", " Strasbourg", " Reykjavik", " Naples", " Monaco", " Riga", " Liverpool", " Luxembourg", " Cologne", " Krakow", " Malaga", " Verona", " Thessaloniki", " Zurich", " Seville", " Geneva", " Marseille", " Palma De Mallorca", " Valencia", " Glasgow", " Mykonos", " Dresden", " Palermo", " Bali", " Crete", " Roatan", " Kathmandu", " Cusco", " Corsica", " Lyon", " Bordeaux", " Beaune", " Sorrento", " Hurghada", " Alexandria", " St. Moritz", " Madeira", " Faro", " Utrecht", " Rotterdam", " Goa", " Varanasi", " Rishikesh", " Kyoto", " Osaka", " Port Douglas", " Darwin", " Brisbane", " Perth", " Lhasa", " Split", " Budva", " Cape Town", " Vilamendhoo", " Hilo", " Victoria", " Costa Del Sol", " Bergen", " Whitsundays", " Bathsheba", " Hoi An", " Versailles", " Grindelwald", " Cascais", " Sao Miguel", " Luxor", " Bremen", " Larnaca", " Tel Aviv", " New Orleans", " Unawatuna", " Mirissa", "Tenerife"});
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
                }catch (ArrayIndexOutOfBoundsException ignored){
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
                list = findByCity(searchRequest);
        }
            List<HashMap> maps = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                maps.add((HashMap) ((JSONObject) list.get(i)).toMap());
            }
            return maps;
    }


    public HashMap selectCity(int geonameId, String currentCookieValue, HttpServletRequest request, HttpServletResponse response){
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
            JSONArray arr = null;
            if(currentCookieValue.equals("")){
                arr = new JSONArray("["+currentCookieValue+"]");
            }else{
                 arr = new JSONArray(currentCookieValue);
            }
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

                return (HashMap)city.toMap();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return (HashMap)city.toMap();
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
        }catch(NullPointerException e) {
            e.printStackTrace();
        }finally
        {
            if(is!=null) {
                is.close();
            }
        }
        return null;
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
           if(request.getCookies()[i].getName().equals(cookieName)) {
               request.getCookies()[i].setPath("/");
               request.getCookies()[i].setValue("");
               request.getCookies()[i].setMaxAge(0);
               response.addCookie(request.getCookies()[i]);
           }
       }
   }


   public JSONObject findSelectedCity(HttpServletRequest request, HttpServletResponse response, String currentCookieValue) {

       if (!Objects.equals(currentCookieValue, "")) {
           JSONArray array = new JSONArray(currentCookieValue);
           for (int i = 0; i < array.length(); i++) {
               if (array.getJSONObject(i).get("status").equals("selected")) {
                   return array.getJSONObject(i);
               }
           }
       } else {
           return findGeolocation(request,response);
//           GeoLocation geoLocation = GeoIPv4.getLocation(AddressGetter.getCurrentIpAddress(request));
//
//           List<JSONObject> list = null;
//           try {
//               list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + geoLocation.getCity());
//               JSONObject obj = list.get(0);
//               obj.put("status", "selected");
//               JSONArray arr = new JSONArray("["+obj.toString()+"]");
//               Cookie c = new Cookie(cookieName,arr.toString());
//               c.setMaxAge(60 * 60 * 24);
//               c.setPath("/");
//               response.addCookie(c);
//               return obj;
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
       }
       return null;
   }

    private JSONObject findGeolocation(HttpServletRequest request, HttpServletResponse response){
           JSONObject location = null;
           Application.log.info("generating location...");
           try {
               location = WeatherService.readJsonFromUrl("http://freegeoip.net/json/"+AddressGetter.getCurrentIpAddress(request));
           } catch (IOException e) {
               Application.log.warning(e.toString());
           }
           Application.log.info("generated.");
           List<JSONObject> list = null;
           try {
               list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + location.get("city"));
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
        return null;
       }


    public List<HashMap> createRecentCitiesTabs(String currentCookieValue) {

        if (!currentCookieValue.equals("")) {
            JSONArray array = new JSONArray(currentCookieValue);

            ArrayList<HashMap> data = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                data.add(getRecentCityInfo(array.getJSONObject(i)));
            }
            return data;
        }
        return null;
    }

    private HashMap getRecentCityInfo(JSONObject city){

        String cityName = city.getString("name");
        if(cityName.contains("'")){
            cityName = cityName.replace("'", "");
        }
        cityName = cityName.replace(" ", "%20");
        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        JSONObject jsonObject = null;
        try {
            jsonObject = WeatherService.readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date=" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() + "&q=" + String.valueOf(city.get("lat") + "," + String.valueOf(city.get("lng"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        HashMap currentCondition = ((HashMap)((ArrayList)map.get("current_condition")).get(0));
        HashMap<String, Object> result = new HashMap<>();
        result.put("weatherCode",(WeatherService.EXT_STATES.get(Integer.parseInt((String)currentCondition.get("weatherCode")))));
        result.put("tempC", currentCondition.get("temp_C"));
        result.put("tempF", currentCondition.get("temp_F"));
        result.put("city", cityName.replace("%20", " "));
        result.put("countryCode", city.getString("countryCode"));
        result.put("countryName", city.getString("countryName"));
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

    public List<String> getTopHolidaysDestinations(int numOfCities){

            return validateTopHolidaysDestinations(cities, numOfCities);
        }

    private List<String> validateTopHolidaysDestinations(List<String> destinations, int numOfCities){

        List<String> cities = new ArrayList<>();
        for (int i = 0; i < destinations.size(); i++) {
            cities.add(destinations.get(i).trim());
        }
        Random random = new Random();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < numOfCities; i++) {
            int index = random.nextInt(cities.size());
            result.add(cities.get(index));
            cities.remove(index);
        }
        return result;
    }

    public List<HashMap> getCountryWeather(JSONObject city) {

        JSONArray jsonArray = null;
        try {
            jsonArray = WeatherService.readJsonArrayFromUrl("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&countryCode=" + String.valueOf(city.get("countryCode")) + "&featureCode=PPLA");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DateTime dateTime = new DateTime(DateTimeZone.forID((String) ((JSONObject) city.get("timezone")).get("timeZoneId")));

        return jsonArray.length() >= 6 ? validateCountryWeather(jsonArray, dateTime, city, 6) : validateCountryWeather(jsonArray, dateTime, city, jsonArray.length());

    }

    private List<HashMap> validateCountryWeather(JSONArray jsonArray, DateTime dateTime, JSONObject city, int numOfCities) {

        List<HashMap> result = new ArrayList<>();
        for (int i = 0; i < numOfCities; i++) {
            HashMap map = (HashMap) jsonArray.getJSONObject(i).toMap();
            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, "",
                    false, true, 6, String.valueOf(map.get("lat")), String.valueOf(map.get("lng")));
            HashMap weather = apiWeatherFinder.findWeatherByDate();
            HashMap currentConditions = ((HashMap) ((ArrayList) weather.get("current_condition")).get(0));
            map.put("temp_C", currentConditions.get("temp_C"));
            map.put("temp_F", currentConditions.get("temp_F"));
            map.put("weatherCode", WeatherService.EXT_STATES.get(Integer.parseInt("" + (currentConditions.get("weatherCode")))));
            map.put("isDay", dateTime.getHourOfDay()>6 && dateTime.getHourOfDay()<18);
            result.add(map);
        }
        return result;
    }

    public List<HashMap> getHolidaysWeather(JSONObject city) {
        List<String> cities = getTopHolidaysDestinations(6);

        List<HashMap> result = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            HashMap hm = new HashMap();
            DateTime dateTime = new DateTime(DateTimeZone.forID((String) ((JSONObject) city.get("timezone")).get("timeZoneId")));
            try {
                hm = findSearchOccurences(cities.get(i)).get(0);
            }catch (IndexOutOfBoundsException ignored){
            }
            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, "",
                    false, true, 6, String.valueOf(hm.get("lat")), String.valueOf(hm.get("lng")));
            HashMap weather = apiWeatherFinder.findWeatherByDate();
            HashMap currentConditions = ((HashMap) ((ArrayList) weather.get("current_condition")).get(0));
            hm.put("temp_C", currentConditions.get("temp_C"));
            hm.put("temp_F", currentConditions.get("temp_F"));
            hm.put("weatherCode", WeatherService.EXT_STATES.get(Integer.parseInt("" + (currentConditions.get("weatherCode")))));
            hm.put("isDay", dateTime.getHourOfDay()>6 && dateTime.getHourOfDay()<18);
            result.add(hm);
        }



        return result;

    }

    public String generateUrlRequestWeather(String location, String currentCookieValue, HttpServletRequest request, HttpServletResponse response){

        if(!location.equals("undefined")) {
            boolean airport = false;
            if(location.contains("airport")||location.contains("Airport")){
                airport = true;
            }
            String city = location.substring(0, location.indexOf('_'));
            String countryCode = location.substring(location.indexOf('_') + 1, location.length());

            JSONArray jsonArray = null;
            try {
                jsonArray = WeatherService.readJsonArrayFromUrl("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&nameStarts=" + city.replaceAll(" ", "%20").concat(airport?"&featureClass=S":"&countryCode=" + countryCode));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject obj = null;
            if (jsonArray != null) {
                if (!jsonArray.toString().equals("[]")) {
                    List<JSONObject> results = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        results.add(jsonArray.getJSONObject(i));
                    }
                    if(!jsonArray.getJSONObject(0).getString("countryCode").equals(countryCode)) {
                        obj = results.stream().filter(jsonObject -> jsonObject.getString("countryCode").equals(countryCode)).findFirst().get();
                    }else{
                        obj = jsonArray.getJSONObject(0);
                    }
                } else {
                    obj = findByCity(city.substring(0, city.length() - 1)).get(0);
                }
            }

            if (obj != null) {
                selectCity(obj.getInt("geonameId"), currentCookieValue, request, response);
            }
        }

        return null;
    }

    public String getSelectedCity(String currentCookieValue){
        JSONArray array = new JSONArray(currentCookieValue);
        if(currentCookieValue!="") {
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).getString("status").equals("selected")) {

                    return array.getJSONObject(i).getString("name")+ "_" + array.getJSONObject(i).getString("countryCode");
                }
            }
        }
        return null;
    }


    public HashMap<String, String> generateMetaTitle(String cookieValue, String path){
        List<String> values = Arrays.asList(path.split("/"));
        if(cookieValue.equals("")){
            return null;
        }
        JSONObject selected = null;
        JSONArray array = new JSONArray(cookieValue);
        for (int i = 0; i < array.length(); i++) {
            if(array.getJSONObject(i).getString("status").equals("selected")){
                selected = array.getJSONObject(i);
            }
        }

        String location = selected.getString("name") + ", "+selected.getString("countryName");

        HashMap<String, String> res = new HashMap<>();
            if(values.size()==0||values.size()==4){
                res.put("title","Oplao.com – Professional weather forecast. Local to Global");
                res.put("description", "Oplao provides professional weather forecasts generated by own software. Animated temperature\n" +
                        "map, alerts, hourly forecast, interactive weather charts, accurate forecast up to 15 days, past weather\n" +
                        "starting 2008 July.");
                return res;
            } else if(values.contains("outlook")){
                res.put("title", "Oplao.com - 10 day Weather Forecast for "+ location);
                res.put("description", location + " current weather, 10 day weather forecast, weather chart, weekly weather summary,\n" +
                        "astronomy, climate in " + location + ", UV-index, weather history.");
                return res;
            }else if(values.contains("today")){
                res.put("title", "Oplao.com – Today Weather for "+location);
                res.put("description", "Weather for today in " + location + " with temperature, feels like, wind, gust, pressure, humidity,\n" +
                        "hourly weather chart with temperature, precipitation.");
                res.put("canonical", "https://oplao.com/en/forecast/hour-by-hour1/");
                return res;
            }else if(values.contains("tomorrow")){
                res.put("title", "Oplao.com – Tomorrow Weather for "+location);
                res.put("description", location + " tomorrow detailed weather with temperature, feels like, wind, gust, pressure, and\n" +
                        "humidity as well as 1 day more hourly weather with 3hr interval.");
                res.put("canonical", "https://oplao.com/en/forecast/today/");
                return res;
            }else if(values.contains("3")){
                res.put("title", "Oplao.com – 3 day weather for "+location);
                res.put("description", "Detailed 3 day weather for "+location+" with temperature, feels like, wind, gust, pressure and 3 day\n" +
                        "weather chart.");
                res.put("canonical", "https://oplao.com/en/forecast/10/");
                return res;
            }else if(values.contains("5")){
                res.put("title", "Oplao.com – 5 day weather forecast for "+location);
                res.put("description", "5 day weather for "+location+" with temperature, feels like, wind, gust, pressure and 5 day weather chart\n" +
                        "with.");
                res.put("canonical", "https://oplao.com/en/weather/outlook/");
                return res;
            }else if(values.contains("7")){
                res.put("title", "Oplao.com – 7 day weather forecast for "+location);
                res.put("description", location+" weekly weather with temperature, feels like, wind, gust, pressure, weekly weather\n" +
                        "summary and 7 day weather chart.");
                res.put("canonical", "https://oplao.com/en/forecast/10/");

                return res;
            }else if(values.contains("10")){
                res.put("title", "Oplao.com – 10 day weather forecast for "+location);
                res.put("description", location + " ten day weather forecast with temperature, feels like, wind, gust, pressure, weekly\n" +
                        "weather summary and 10 day weather chart.");
                res.put("canonical", "https://oplao.com/en/weather/outlook/");
                return res;
            }else if(values.contains("14")){
                res.put("title", "Oplao.com – 14 day weather forecast for "+location);
                res.put("description", location +   " 2 week weather forecast with temperature, feels like, wind, gust, pressure, weekly\n" +
                        "weather summary and 14 day weather chart.");
                res.put("canonical", "https://oplao.com/en/weather/outlook/");
                return res;
            }else if(values.contains("hour-by-hour1")||values.contains("hour-by-hour3")){
                char index = values.get(3).charAt(values.get(3).length()-1);
                res.put("title", "Oplao.com – Hourly weather for "+location);
                res.put("description", location + " hourly weather with temperature, feels like, wind, gust, pressure, weekly weather\n" +
                        "summary and detailed hour by hour weather chart. "+index+" hr interval detailed weather.");
                    if(index =='3'){
                        res.put("canonical", "https://oplao.com/en/forecast/hour-by-hour1/");
                    }
                return res;
            }else if(values.contains("history1")||values.contains("history3")){
                char index = values.get(3).charAt(values.get(3).length()-1);
                res.put("title", "Oplao.com – Weather History for "+location);
                res.put("description", "Hourly weather history and data archive for "+location+" with "+ index + " hour interval. Detailed weather\n" +
                        "history data provides temperature, wind, gust, humidity, pressure, precipitation chance as well as in\n" +
                        "inches.");
                if(index =='3'){
                    res.put("canonical", "https://oplao.com/en/weather/history1/");
                }
                return res;
            }
        return null;
    }
}
