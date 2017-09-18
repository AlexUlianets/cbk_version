package com.oplao.service;

import com.oplao.Utils.CityToTimeZoneConverter;
import com.oplao.Utils.DateConstants;
import com.oplao.Utils.MoonPhase;
import com.oplao.Utils.MyJsonHelper;
import com.oplao.model.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    public static final Map<Integer, WeatherStateExt> EXT_STATES = new HashMap<>();

    static {
        EXT_STATES.put(113, WeatherStateExt.Clear);
        EXT_STATES.put(116, WeatherStateExt.PartlyCloudy);
        EXT_STATES.put(119, WeatherStateExt.Cloudy);
        EXT_STATES.put(122, WeatherStateExt.Overcast);
        EXT_STATES.put(143, WeatherStateExt.Mist);
        EXT_STATES.put(176, WeatherStateExt.ModeratePatchyRain);
        EXT_STATES.put(179, WeatherStateExt.ModeratePatchySnow);
        EXT_STATES.put(182, WeatherStateExt.PatchySleet);
        EXT_STATES.put(185, WeatherStateExt.PatchySleet);
        EXT_STATES.put(200, WeatherStateExt.PatchyStorm);
        EXT_STATES.put(227, WeatherStateExt.Blizzard);
        EXT_STATES.put(230, WeatherStateExt.Blizzard);
        EXT_STATES.put(248, WeatherStateExt.Fog);
        EXT_STATES.put(260, WeatherStateExt.FreezingFog);
        EXT_STATES.put(263, WeatherStateExt.LightPatchyRain);
        EXT_STATES.put(266, WeatherStateExt.LightRain);
        EXT_STATES.put(281, WeatherStateExt.LightSleet);
        EXT_STATES.put(284, WeatherStateExt.HeavySleet);
        EXT_STATES.put(293, WeatherStateExt.LightPatchyRain);
        EXT_STATES.put(296, WeatherStateExt.LightRain);
        EXT_STATES.put(299, WeatherStateExt.ModeratePatchyRain);
        EXT_STATES.put(302, WeatherStateExt.ModerateRain);
        EXT_STATES.put(305, WeatherStateExt.HeavyPatchyRain);
        EXT_STATES.put(308, WeatherStateExt.HeavyRain);
        EXT_STATES.put(311, WeatherStateExt.FreezingRain);
        EXT_STATES.put(314, WeatherStateExt.HeavyFreezingRain);
        EXT_STATES.put(317, WeatherStateExt.LightSleet);
        EXT_STATES.put(320, WeatherStateExt.HeavySleet);
        EXT_STATES.put(323, WeatherStateExt.LightPatchySnow);
        EXT_STATES.put(326, WeatherStateExt.LightSnow);
        EXT_STATES.put(329, WeatherStateExt.ModeratePatchySnow);
        EXT_STATES.put(332, WeatherStateExt.ModerateSnow);
        EXT_STATES.put(335, WeatherStateExt.HeavyPatchySnow);
        EXT_STATES.put(338, WeatherStateExt.HeavySnow);
        EXT_STATES.put(350, WeatherStateExt.IcePellets);
        EXT_STATES.put(353, WeatherStateExt.LightRain);
        EXT_STATES.put(356, WeatherStateExt.RainShower);
        EXT_STATES.put(359, WeatherStateExt.RainShower);
        EXT_STATES.put(362, WeatherStateExt.PatchySleet);
        EXT_STATES.put(365, WeatherStateExt.PatchySleet);
        EXT_STATES.put(368, WeatherStateExt.LightPatchySnow);
        EXT_STATES.put(371, WeatherStateExt.ModeratePatchySnow);
        EXT_STATES.put(374, WeatherStateExt.IcePelletsShowers);
        EXT_STATES.put(377, WeatherStateExt.HeavyIcePelletsShowers);
        EXT_STATES.put(386, WeatherStateExt.PatchyStorm);
        EXT_STATES.put(389, WeatherStateExt.HeavyStorm);
        EXT_STATES.put(392, WeatherStateExt.LightSnowStorm);
        EXT_STATES.put(395, WeatherStateExt.HeavySnowStorm);
    }
    private String convertDayOfWeekShort(int day) {

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
    public List<HashMap> getYearSummary(JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));

        JSONObject jsonObject = null;

        try{
            jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&q=" + cityName + "&cc=no&fx=yes&num_of_days=1&tp=24&showlocaltime=no");
        }catch (IOException e){
            e.printStackTrace();
        }

        HashMap map = (HashMap) jsonObject.toMap().get("data");
        List months = ((ArrayList)((HashMap)((ArrayList)map.get("ClimateAverages")).get(0)).get("month"));
        List<HashMap> result = new ArrayList<>();

        for (int i = 0; i < months.size(); i++) {
            HashMap res = new HashMap();
            HashMap elem = ((HashMap)months.get(i));
            res.put("month", "" + String.valueOf(elem.get("name")).substring(0,3));
            res.put("maxtempC", elem.get("avgMaxTemp"));
            res.put("maxtempF", elem.get("avgMaxTemp_F"));
            res.put("mintempC", elem.get("avgMinTemp"));
            res.put("mintempF", elem.get("avgMinTemp_F"));
            res.put("precipp", elem.get("avgMonthlyRainfall"));
            result.add(res);
        }

        return result;

    }
    List<Integer> dayTimeValues = Arrays.asList(600,700,800,900,1000,1100,1200);
    List<Integer> nightTimeValues = Arrays.asList(0,100,200,300,400,500,1800);



    public HashMap<Integer, HashMap<String,HashMap>> getWeeklyWeatherReport(JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));


        DateTime dateTime = new DateTime();

       HashMap<Integer, HashMap<String,HashMap>> result = new HashMap<>();

        for (int day = dateTime.getDayOfMonth(), count = 0; day < dateTime.getDayOfMonth()+7 ; day++, count++) {
            result.put(count, getOneDayReportMapping(dateTime.plusDays(
                    count),
                    cityName));
        }


        return result;
    }

    private HashMap<String, HashMap> getOneDayReportMapping(DateTime dateTime, String city){

        JSONObject jsonObject = null;

        try{
            jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth()  + "&q=" + city);
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
        HashMap weatherData = weather.get(0);
        ArrayList<HashMap> hourly = (ArrayList<HashMap>)weatherData.get("hourly");


        int maxWholeDayC = parseInt(((HashMap)((ArrayList)map.get("weather")).get(0)).get("maxtempC"));
        int maxWholeDayF = parseInt(((HashMap)((ArrayList)map.get("weather")).get(0)).get("maxtempF"));
        int minWholeDayC =  parseInt(((HashMap)((ArrayList)map.get("weather")).get(0)).get("mintempC"));
        int minWholeDayF =  parseInt(((HashMap)((ArrayList)map.get("weather")).get(0)).get("mintempF"));
        int avgDayC = getAVGIntParam(hourly, "tempC", dayTimeValues);
        int avgDayF = getAVGIntParam(hourly, "tempF", dayTimeValues);
        int avgNightC = getAVGIntParam(hourly, "tempC", nightTimeValues);
        int avgNightF = getAVGIntParam(hourly, "tempF", nightTimeValues);
        int maxFeelLikeDayC = getAVGIntParam(hourly,"FeelsLikeC", dayTimeValues);
        int maxFeelLikeDayF = getAVGIntParam(hourly,"FeelsLikeF", dayTimeValues);
        int maxFeelLikeNightC = getAVGIntParam(hourly,"FeelsLikeC", nightTimeValues);
        int maxFeelLikeNightF = getAVGIntParam(hourly,"FeelsLikeF", nightTimeValues);
        int precipeChanceDay = getAVGIntParam(hourly, "chanceofrain",dayTimeValues);
        int precipeChanceNight = getAVGIntParam(hourly, "chanceofrain",nightTimeValues);
        double precipDayMM  = getSumDoubleParam(hourly,"precipMM", dayTimeValues);
        double precipNightMM = getSumDoubleParam(hourly,"precipMM", nightTimeValues);
        int avgWindMDay = getAVGIntParam(hourly,"windspeedMiles", dayTimeValues);
        int avgWindKmhDay = getAVGIntParam(hourly,"windspeedKmph", dayTimeValues);
        int avgWindMNight = getAVGIntParam(hourly,"windspeedMiles", nightTimeValues);
        int avgWindKmhNight = getAVGIntParam(hourly, "windspeedKmph" ,nightTimeValues);
        int maxGustMDay = getAVGIntParam(hourly, "WindGustMiles", dayTimeValues);
        int maxGustKmhDay = getAVGIntParam(hourly,"WindGustKmph", dayTimeValues);
        int maxGustMNight = getAVGIntParam(hourly, "WindGustMiles", nightTimeValues);
        int maxGustKmhNight = getAVGIntParam(hourly, "WindGustKmph", nightTimeValues);
        int avgPressureDay = getAVGIntParam(hourly,"pressure", dayTimeValues);
        int avgPressureNight = getAVGIntParam(hourly, "pressure", nightTimeValues);
        String weatherCode = "" + EXT_STATES.get(parseInt(hourly.get(0).get("weatherCode")));
        int windDegreeDay = getAVGIntParam(hourly, "winddirDegree", dayTimeValues) + 40;
        int windDegreeNight = getAVGIntParam(hourly, "winddirDegree", nightTimeValues) + 40;
        HashMap<String, HashMap> result = new HashMap<>();
        HashMap<String, Object> dayMap = new HashMap<>();
        HashMap<String, Object> wholeDayMap = new HashMap<>();
        wholeDayMap.put("dayOfMonth", dateTime.getDayOfMonth());
        wholeDayMap.put("monthOfYear",convertMonthOfYear(dateTime.getMonthOfYear()));
        wholeDayMap.put("dayOfWeek", convertDayOfWeek(dateTime.getDayOfWeek()));
        wholeDayMap.put("weatherCode", weatherCode);
        wholeDayMap.put("maxTemperatureC", maxWholeDayC);
        wholeDayMap.put("maxTemperatureF", maxWholeDayF);
        wholeDayMap.put("minTemperatureC", minWholeDayC);
        wholeDayMap.put("minTemperatureF", minWholeDayF);

        dayMap.put("avgTempC", avgDayC);
        dayMap.put("avgTempF", avgDayF);
        dayMap.put("time", "Day");
        dayMap.put("feelsLikeC", maxFeelLikeDayC);
        dayMap.put("feelsLikeF", maxFeelLikeDayF);
        dayMap.put("precipChance", precipeChanceDay);
        dayMap.put("precip", precipDayMM);
        dayMap.put("windM", avgWindMDay);
        dayMap.put("windKm", avgWindKmhDay);
        dayMap.put("gustM", maxGustMDay);
        dayMap.put("gustKmh", maxGustKmhDay);
        dayMap.put("pressure", avgPressureDay);
        dayMap.put("windDegree", windDegreeDay);

        HashMap<String, Object> nightMap = new HashMap<>();
        nightMap.put("time", "Night");
        nightMap.put("avgTempC", avgNightC);
        nightMap.put("avgTempF", avgNightF);
        nightMap.put("feelsLikeC", maxFeelLikeNightC);
        nightMap.put("feelsLikeF", maxFeelLikeNightF);
        nightMap.put("precipChance", precipeChanceNight);
        nightMap.put("precip", precipNightMM);
        nightMap.put("windM", avgWindMNight);
        nightMap.put("windKm", avgWindKmhNight);
        nightMap.put("gustM", maxGustMNight);
        nightMap.put("gustKmh", maxGustKmhNight);
        nightMap.put("pressure", avgPressureNight);
        nightMap.put("windDegree", windDegreeNight);

        result.put("wholeDay", wholeDayMap);
        result.put("Day", dayMap);
        result.put("Night", nightMap);

        return result;
    }



    private double getSumDoubleParam(List<HashMap> hourly, String paramName, List<Integer> dayTimeValues){

        final int[] avgParam = {0};

        hourly = hourly.stream().filter(hashMap ->
                dayTimeValues.contains(parseInt(hashMap.get("time"))))
                .collect(Collectors.toList());

        hourly.stream().forEach(hashMap ->
                avgParam[0] += parseDouble(hashMap.get(paramName)));

        return avgParam[0];

    }
    private Integer getAVGIntParam(List<HashMap> hourly, String paramName, List<Integer> dayTimeValues){
        final int[] avgParam = {0};

        hourly = hourly.stream().filter(hashMap ->
                dayTimeValues.contains(parseInt(hashMap.get("time"))))
                .collect(Collectors.toList());

        hourly.stream().forEach(hashMap ->
                avgParam[0] += parseInt(hashMap.get(paramName)));

        return avgParam[0]/dayTimeValues.size(

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

    private String convertMonthOfYearShort(int month) {


        switch (month) {
            case DateConstants.JANUARY
                    :
                return "Jan";

            case DateConstants.FEBRUARY
                    :
                return "Feb";
            case DateConstants.MARCH
                    :
                return "Mar";
            case DateConstants.APRIL
                    :
                return "Apr";
            case DateConstants.MAY
                    :
                return "May";
            case DateConstants.JUNE
                    :
                return "Jun";
            case DateConstants.JULY
                    :
                return "Jul";
            case DateConstants.AUGUST
                    :
                return "Aug";
            case DateConstants.SEPTEMBER
                    :
                return "Sep";
            case DateConstants.OCTOBER
                    :
                return "Oct";
            case DateConstants.NOVEMBER
                    :
                return "Nov";
            case DateConstants.DECEMBER
                    :
                return "Dec";
            default:
                return "Wrong value for field 'month'";
        }
    }


    private float roundFloat(float num){
        String pattern = "##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return Float.parseFloat(decimalFormat.format(num).replace(',', '.'));
    }
    public HashMap getCoordinates(JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));
        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        LocalDateTime ldt = LocalDateTime.of(zdt.getYear(),
                zdt.getMonth(), zdt.getDayOfMonth(),
                zdt.getHour(), zdt.getMinute());

        String hoursBetween = "" + ChronoUnit.HOURS.between(ldt, ZonedDateTime.now
                (ZoneId.of(CityToTimeZoneConverter.convert(city))));

        if(Integer.parseInt(hoursBetween)>0){
            hoursBetween = "+ " + hoursBetween;
        }

        HashMap result = new HashMap();

        result.put("city", cityName);
        result.put("country", city.get("countryName"));
        result.put("latitude", roundFloat((float) city.getDouble("lat")));
        result.put("longitude", roundFloat((float) city.getDouble("lng")));
        result.put("hours_between", hoursBetween);
        return result;
    }

    private String validateCityName(String cityName){
        if(cityName.contains("'")){
            cityName = cityName.replace("'", "");
        }

        cityName = cityName.replaceAll(" ", "%20");
        return cityName;
    }
    public HashMap getAstronomy(JSONObject city){
        String cityName = validateCityName((String)city.get("asciiName"));


        DateTime dateTime = new DateTime();
        JSONObject jsonObject = null;

        try{
           jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=6&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + cityName);
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        HashMap result = new HashMap<>();


        HashMap dateMap = new HashMap();
        dateMap.put("dayOfMonth", dateTime.getDayOfMonth());
        dateMap.put("monthOfYear", convertMonthOfYearShort(dateTime.getMonthOfYear()));
        dateMap.put("year", dateTime.getYear());
        result.put("date", dateMap);
        result.put("sunrise", ((HashMap)((ArrayList)((HashMap)((ArrayList)map.get("weather")).get(0)).get("astronomy")).get(0)).get("sunrise"));
        result.put("sunset", ((HashMap)((ArrayList)((HashMap)((ArrayList)map.get("weather")).get(0)).get("astronomy")).get(0)).get("sunset"));
        result.put("moon_phase_index", getMoonPhase());
        return result;
    }

    private int getMoonPhase(){
        return new MoonPhase(Calendar.getInstance()).getPhaseIndex();
    }

        public OutlookWeatherMapping getRemoteData(JSONObject city){


            String cityName = validateCityName((String)city.get("asciiName"));


            DateTime dateTime = new DateTime(DateTimeZone.forID(CityToTimeZoneConverter.convert(city)));

            JSONObject jsonObject = null;
        try {
          jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=24&date=" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() + "&q=" + cityName);
        }catch (IOException e){
            e.printStackTrace();
        }
            HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> currentCondition = (ArrayList<HashMap>)map.get("current_condition");
        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
            HashMap weatherData = weather.get(0);
            ArrayList<HashMap> hourly = (ArrayList<HashMap>)weatherData.get("hourly");
            HashMap hourlyHm = hourly.get(0);
        HashMap currentConditions = currentCondition.get(0);

            return
                    OutlookWeatherMapping.create((String)city.get("countryName"), cityName.replaceAll("%20", " "), dateTime,
                            parseInt(currentConditions.get("temp_C")),
                            parseInt(currentConditions.get("temp_F")),
                            parseInt(currentConditions.get("FeelsLikeC")),
                            parseInt(currentConditions.get("FeelsLikeF")),
                            parseDouble(currentConditions.get("precipMM")),
                            parseInt(currentConditions.get("windspeedMiles")),
                            parseInt(currentConditions.get("windspeedKmph")),
                            parseInt(hourlyHm.get("WindGustMiles")),
                            parseInt(hourlyHm.get("WindGustKmph")),
                            parseInt(currentConditions.get("pressure")),
                            ""+(EXT_STATES.get(parseInt(hourlyHm.get("weatherCode")))));

    }


    public List<DetailedForecastGraphMapping> getDetailedForecastMapping(JSONObject city){
            List<DetailedForecastGraphMapping> result = new ArrayList<>();

            DateTime dateTime = new DateTime();
        for (int day = 0; day < 10; day++) {
            result.add(getSingleDetailedForecastMapping(dateTime.plusDays(day), city));
        }
        return result;
    }
    private DetailedForecastGraphMapping getSingleDetailedForecastMapping(DateTime dateTime, JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));
        
        JSONObject jsonObject = null;
        try {
            jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=24&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + cityName);
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        HashMap currentConditions = (HashMap)((ArrayList)((HashMap)(((ArrayList)map.get("weather"))).get(0)).get("hourly")).get(0);


        int tempC = parseInt(currentConditions.get("tempC"));
        int tempF = parseInt(currentConditions.get("tempF"));
        double precip = parseDouble(currentConditions.get("precipMM"));
        String weatherIconCode = "" + EXT_STATES.get(parseInt(currentConditions.get("weatherCode")));

        String month = dateTime.getMonthOfYear() > 9? "" + dateTime.getMonthOfYear():"0" + dateTime.getMonthOfYear();
        String day = dateTime.getDayOfMonth() > 9 ? "" + dateTime.getDayOfMonth():"0" + dateTime.getDayOfMonth();
        String date = dateTime.getYear() + "-" +
                month + "-" +
                day;



            return new DetailedForecastGraphMapping(tempC,tempF,date, precip, weatherIconCode);
    }

    public List<HashMap> getWeeklyUltraviolet(JSONObject city){

        DateTime dateTime = new DateTime();
        List<HashMap> list = new ArrayList();
        for (int day = 0; day < 5; day++) {
            list.add(getSingleUltravioletIndex(dateTime.plusDays(day), city));
        }
        return list;
    }
    private HashMap getSingleUltravioletIndex(DateTime dateTime, JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));

            JSONObject jsonObject = null;
            try {
                jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=24&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + cityName);
            }catch (IOException e){
                e.printStackTrace();
            }
        HashMap map = (HashMap)jsonObject.toMap().get("data");

        HashMap res = new HashMap();
        res.put("index",
                ((HashMap)((ArrayList)map.get("weather")).get(0)).get("uvIndex"));
        res.put("date", convertDayOfWeekShort(dateTime.getDayOfWeek())+" " +
                dateTime.getDayOfMonth()+" "
                + convertMonthOfYearShort(dateTime.getMonthOfYear()));
        return res;
    }

    public List getFiveYearsAverage(JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));

        DateTime dateTime = new DateTime();

        ArrayList output = new ArrayList();

        for (int i = 0; i < 6; i++) {
            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime.minusYears(i), cityName,
                    new DateTime().isAfter(dateTime), false, 1);
            HashMap map = apiWeatherFinder.findWeatherByDate();

            ArrayList param = (ArrayList) MyJsonHelper.getParam(map, "weather", "hourly");

            int tempC = parseInt(((HashMap) param.get(14)).get("tempC"));
            int tempF = parseInt(((HashMap) param.get(14)).get("tempF"));
            HashMap result = new HashMap();
            result.put("year", dateTime.minusYears(i).getYear());
            result.put("tempC", tempC);
            result.put("tempF", tempF);

            output.add(result);

        }
        return output;
    }

        public static String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } finally {
                is.close();
            }
    }
}
