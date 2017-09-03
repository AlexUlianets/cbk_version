package com.oplao.service;

import com.oplao.Utils.CityToTimeZoneConverter;
import com.oplao.Utils.DateConstants;
import com.oplao.Utils.MoonPhase;
import com.oplao.model.DetailedForecastGraphMapping;
import com.oplao.model.GeoLocation;
import com.oplao.model.OutlookWeatherMapping;
import com.oplao.model.WeeklyWeatherReportMapping;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherService {


    public static String convertDayOfWeekShort(int day) {

        switch (day) {
            case DateTimeConstants.MONDAY
                    :
                return "Mon";
            case DateTimeConstants.TUESDAY
                    :
                return "Tue";

            case DateTimeConstants.WEDNESDAY
                    :
                return "Wed";

            case DateTimeConstants.THURSDAY
                    :
                return "Thu";

            case DateTimeConstants.FRIDAY
                    :
                return "Fri";

            case DateTimeConstants.SATURDAY
                    :
                return "Sat";

            case DateTimeConstants.SUNDAY
                    :
                return "Sun";
            default:
                return "wrong value for field 'day of week' ";

        }
    }
    List<Integer> dayTimeValues = Arrays.asList(600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700);
    List<Integer> nightTimeValues = Arrays.asList(0,100,200,300,400,500,1800,1900,2000,2100,2200,2300);



    public HashMap<Integer, HashMap<String,WeeklyWeatherReportMapping>> getWeeklyWeatherReport(){
        GeoLocation geoLocation = GeoIPv4.getLocation("94.126.240.2");

        DateTime dateTime = new DateTime();

       HashMap<Integer, HashMap<String, WeeklyWeatherReportMapping>> result = new HashMap();

        for (int day = dateTime.getDayOfMonth(), count = 0; day < dateTime.getDayOfMonth()+7 ; day++, count++) {
            result.put(count, getOneDayReportMapping(dateTime.plusDays(
                    count),
                    geoLocation.getCity(),count));
        }


        return result;
    }

    private HashMap<String, WeeklyWeatherReportMapping> getOneDayReportMapping(DateTime dateTime, String city, int count ){
        URL url = null;
        try {
            url = new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + dateTime.getDayOfMonth()  + "&q=" + city);
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
        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
        HashMap weatherData = weather.get(count);
        ArrayList<HashMap> hourly = (ArrayList<HashMap>)weatherData.get("hourly");


        int maxDayC = getMaxIntParam(hourly, "tempC", dayTimeValues);
        int maxDayF = getMaxIntParam(hourly,"tempF", dayTimeValues);
        int maxNightC = getMaxIntParam(hourly,"tempC", nightTimeValues);
        int maxNightF = getMaxIntParam(hourly,"tempF", nightTimeValues);
        int maxFeelLikeDayC = getMaxIntParam(hourly,"FeelsLikeC", dayTimeValues);
        int maxFeelLikeDayF = getMaxIntParam(hourly,"FeelsLikeF", dayTimeValues);
        int maxFeelLikeNightC = getMaxIntParam(hourly,"FeelsLikeC", nightTimeValues);
        int maxFeelLikeNightF = getMaxIntParam(hourly,"FeelsLikeF", nightTimeValues);
        int precipeChanceDay = getAVGIntParam(hourly, "chanceofrain",dayTimeValues);
        int precipeChanceNight = getAVGIntParam(hourly, "chanceofrain",nightTimeValues);
        double precipDayMM  = getMaxDoubleParam(hourly,"precipMM", dayTimeValues);
        double precipNightMM = getMaxDoubleParam(hourly,"precipMM", nightTimeValues);
        int avgWindMDay = getAVGIntParam(hourly,"windspeedMiles", dayTimeValues);
        int avgWindKmhDay = getAVGIntParam(hourly,"windspeedKmph", dayTimeValues);
        int avgWindMNight = getAVGIntParam(hourly,"windspeedMiles", nightTimeValues);
        int avgWindKmhNight = getAVGIntParam(hourly, "windspeedKmph" ,nightTimeValues);
        int maxGustMDay = getMaxIntParam(hourly, "WindGustMiles", dayTimeValues);
        int maxGustKmhDay = getMaxIntParam(hourly,"WindGustKmph", dayTimeValues);
        int maxGustMNight = getMaxIntParam(hourly, "WindGustMiles", nightTimeValues);
        int maxGustKmhNight = getMaxIntParam(hourly, "WindGustKmph", nightTimeValues);
        int avgPressureDay = getAVGIntParam(hourly,"pressure", dayTimeValues);
        int avgPressureNight = getAVGIntParam(hourly, "pressure", nightTimeValues);
        String weaherIcon = String.valueOf(((HashMap)((ArrayList)(map.get("current_condition"))).get(0)).get("weatherIconUrl"));


        //TODO weather icon validation add here!!!
        HashMap<String, WeeklyWeatherReportMapping> result = new HashMap<>();
        result.put("day", new WeeklyWeatherReportMapping(
                dateTime.getDayOfMonth(), convertMonthOfYear(dateTime.getMonthOfYear()),
                convertDayOfWeek(dateTime.getDayOfWeek()),
                "Day", weaherIcon, maxDayC, maxDayF, maxFeelLikeDayC,
                maxFeelLikeDayF, precipeChanceDay, precipDayMM, avgWindMDay, avgWindKmhDay, maxGustMDay,
                maxGustKmhDay, avgPressureDay)
        );

        result.put("night", new WeeklyWeatherReportMapping(
                dateTime.getDayOfMonth(), convertMonthOfYear(dateTime.getMonthOfYear()),
                convertDayOfWeek(dateTime.getDayOfWeek()), "Night", weaherIcon, maxNightC,
                maxNightF, maxFeelLikeNightC, maxFeelLikeNightF,
                precipeChanceNight, precipNightMM, avgWindMNight, avgWindKmhNight, maxGustMNight,
                maxGustKmhNight, avgPressureNight)
        );


        return result;
    }

    private Integer getAVGIntParam(List<HashMap> hourly, String paramName, List<Integer> dayTimeValues){
        final int[] precipChance = {0};

        hourly = hourly.stream().filter(hashMap ->
                dayTimeValues.contains(parseInt(hashMap.get("time"))))
                .collect(Collectors.toList());

        hourly.stream().forEach(hashMap ->
                precipChance[0] += parseInt(hashMap.get(paramName)));

        return precipChance[0]/dayTimeValues.size(

        );
    }
    private Double getMaxDoubleParam(ArrayList<HashMap> hourly, String paramName, List<Integer> dayTimeValues){
        List<HashMap> hourlyDay = hourly.stream().filter(
                hashMap -> dayTimeValues.contains(parseInt(hashMap.get("time"))
                )).collect(Collectors.toList());


        double param = parseDouble(hourlyDay.stream().max(
                Comparator.comparing(hashMap -> parseDouble(hashMap.get(paramName)))
        ).get().get(paramName));
        return param;
    }


    private Integer getMaxIntParam(ArrayList<HashMap> hourly, String paramName, List<Integer> dayTimeValues){
        List<HashMap> hourlyDay = hourly.stream().filter(
                hashMap -> dayTimeValues.contains(parseInt(hashMap.get("time"))
                )).collect(Collectors.toList());


        int param = parseInt(hourlyDay.stream().max(
                Comparator.comparing(hashMap -> parseInt(hashMap.get(paramName)))
        ).get().get(paramName));
        return param;
    }
    private Integer parseInt(Object o){
        return Integer.parseInt(String.valueOf(o));
    }
    private Double parseDouble(Object o){
        return Double.parseDouble(String.valueOf(o));
    }
    public static String convertDayOfWeek(int day) {


        switch (day) {
            case DateTimeConstants.MONDAY
                    : return "Monday";
            case DateTimeConstants.TUESDAY
                    : return "Tuesday";

            case DateTimeConstants.WEDNESDAY
                    : return "Wednesday";

            case DateTimeConstants.THURSDAY
                    : return "Thursday";

            case DateTimeConstants.FRIDAY
                    : return "Friday";

            case DateTimeConstants.SATURDAY
                    : return "Saturday";

            case DateTimeConstants.SUNDAY
                    : return "Sunday";
            default:return "wrong value for field 'day of week' ";

        }
    }
    public static String convertMonthOfYear(int month) {


        switch (month) {
            case DateConstants.JANUARY
                    :
                return "January";

            case DateConstants.FEBRUARY
                    :
                return "February";
            case DateConstants.MARCH
                    :
                return "March";
            case DateConstants.APRIL
                    :
                return "April";
            case DateConstants.MAY
                    :
                return "May";
            case DateConstants.JUNE
                    :
                return "June";
            case DateConstants.JULY
                    :
                return "July";
            case DateConstants.AUGUST
                    :
                return "August";
            case DateConstants.SEPTEMBER
                    :
                return "September";
            case DateConstants.OCTOBER
                    :
                return "October";
            case DateConstants.NOVEMBER
                    :
                return "November";
            case DateConstants.DECEMBER
                    :
                return "December";
            default:
                return "Wrong value for field 'month'";
        }
    }



    private float roundFloat(float num){
        String pattern = "##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return Float.parseFloat(decimalFormat.format(num).replace(',', '.'));
    }
    private HashMap getCoordinates(){

        GeoLocation geoLocation = GeoIPv4.getLocation("94.126.240.2");
        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        LocalDateTime ldt = LocalDateTime.of(zdt.getYear(),
                zdt.getMonth(), zdt.getDayOfMonth(),
                zdt.getHour(), zdt.getMinute());

        String hoursBetween = "" + ChronoUnit.HOURS.between(ldt, ZonedDateTime.now
                (ZoneId.of(CityToTimeZoneConverter.convert(geoLocation.getCity()))));

        if(Integer.parseInt(hoursBetween)>0){
            hoursBetween = "+ " + hoursBetween;
        }

        HashMap result = new HashMap();

        result.put("city", geoLocation.getCity());
        result.put("country", geoLocation.getCountryName());
        result.put("latitude", roundFloat(geoLocation.getLatitude()));
        result.put("longitude", roundFloat(geoLocation.getLongitude()));
        result.put("hours_between", hoursBetween);


        return result;

    }

    public HashMap getAstronomy(){

        HashMap result = new HashMap<>();

        result.put("coordinates", getCoordinates());
        result.put("moon_phase_index", getMoonPhase());
        return result;
    }

    private int getMoonPhase(){
        return new MoonPhase(Calendar.getInstance()).getPhaseIndex();
    }
        public OutlookWeatherMapping getRemoteData(){
        GeoLocation geoLocation = GeoIPv4.getLocation("94.126.240.2");
            URL url = null;
            DateTime dateTime = new DateTime();

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

            return
                    OutlookWeatherMapping.create(geoLocation.getCountryName(), geoLocation.getCity(), dateTime,
                            Integer.parseInt(String.valueOf(currentConditions.get("temp_C"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("temp_F"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("FeelsLikeC"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("FeelsLikeF"))),
                            Double.parseDouble(String.valueOf(currentConditions.get("precipMM"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("windspeedMiles"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("windspeedKmph"))),
                            Integer.parseInt(String.valueOf(hourlyHm.get("WindGustMiles"))),
                            Integer.parseInt(String.valueOf(hourlyHm.get("WindGustKmph"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("pressure"))));

    }


    public List<DetailedForecastGraphMapping> getDetailedForecastMapping(){
            List<DetailedForecastGraphMapping> result = new ArrayList<>();

            DateTime dateTime = new DateTime();
        for (int day = 0; day < 10; day++) {
            result.add(getSingleDetailedForecastMapping(dateTime.plusDays(day)));
        }
        return result;
    }
    private DetailedForecastGraphMapping getSingleDetailedForecastMapping(DateTime dateTime){
        GeoLocation geoLocation = GeoIPv4.getLocation("94.126.240.2");
        URL url = null;

        try {
            url = new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=24&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + geoLocation.getCity());
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
        HashMap currentConditions = (HashMap)((ArrayList)((HashMap)(((ArrayList)map.get("weather"))).get(0)).get("hourly")).get(0);


        int tempC = parseInt(currentConditions.get("tempC"));
        int tempF = parseInt(currentConditions.get("tempF"));
        double precip = parseDouble(currentConditions.get("precipMM"));

        String month = dateTime.getMonthOfYear() > 9? "" + dateTime.getMonthOfYear():"0" + dateTime.getMonthOfYear();
        String day = dateTime.getDayOfMonth() > 9 ? "" + dateTime.getDayOfMonth():"0" + dateTime.getDayOfMonth();
        String date = dateTime.getYear() + "-" +
                month + "-" +
                day;



            return new DetailedForecastGraphMapping(tempC,tempF,date, precip);
    }
}
