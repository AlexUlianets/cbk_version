package com.oplao.service;

import com.oplao.Utils.CityToTimeZoneConverter;
import com.oplao.Utils.DateConstants;
import com.oplao.Utils.MoonPhase;
import com.oplao.Utils.MyJsonHelper;
import com.oplao.model.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
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

    public List<HashMap> getYearSummary(JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));

        JSONObject jsonObject = null;

        try{
            jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&q=" + String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))) + "&cc=no&fx=yes&num_of_days=1&tp=24&showlocaltime=no");
        }catch (IOException e){
            e.printStackTrace();
        }

        HashMap map = (HashMap) jsonObject.toMap().get("data");
        List months = ((ArrayList)((HashMap)((ArrayList)map.get("ClimateAverages")).get(0)).get("month"));
        List<HashMap> result = new ArrayList<>();
        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        for (int i = 0; i < months.size(); i++) {
            HashMap res = new HashMap();
            HashMap elem = ((HashMap)months.get(i));
            res.put("active", dateTime.getMonthOfYear() - 1 == i);
            res.put("month", "" + String.valueOf(elem.get("name")).substring(0,3));
            res.put("fullMonthName", String.valueOf(elem.get("name")));
            res.put("maxtempC", elem.get("avgMaxTemp"));
            res.put("maxtempF", elem.get("avgMaxTemp_F"));
            res.put("mintempC", elem.get("avgMinTemp"));
            res.put("mintempF", elem.get("avgMinTemp_F"));
            res.put("precipMM", elem.get("avgMonthlyRainfall"));
            res.put("precipInch", elem.get("avgMonthlyRainfall_inch"));
            result.add(res);
        }

        return result;

    }
    List<Integer> dayTimeValues = Arrays.asList(1200, 1300, 1400, 1500, 1600, 1700, 1800);
    List<Integer> nightTimeValues = Arrays.asList(0,100,200,300,400,500,600);



    public HashMap<Integer, HashMap<String,HashMap>> getWeeklyWeatherReport(JSONObject city, int numOfDays){

        String cityName = validateCityName((String)city.get("asciiName"));


        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));

       HashMap<Integer, HashMap<String,HashMap>> result = new HashMap<>();

        for (int day = dateTime.getDayOfMonth(), count = 0; day < dateTime.getDayOfMonth()+numOfDays ; day++, count++) {
            result.put(count, getOneDayReportMapping(dateTime.plusDays(
                    count),
                    String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng")))));
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
        double precipDayMM  = new BigDecimal(getSumDoubleParam(hourly,"precipMM", dayTimeValues)).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        double precipNightMM = new BigDecimal(getSumDoubleParam(hourly,"precipMM", nightTimeValues)).setScale(2, BigDecimal.ROUND_UP).doubleValue();
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
        String weatherCode = "" + EXT_STATES.get(parseInt(hourly.get(12).get("weatherCode")));
        String dayWeatherCode = "" + EXT_STATES.get(parseInt(hourly.get(14).get("weatherCode")));
        String nightWeatherCode = "" + EXT_STATES.get(parseInt(hourly.get(2).get("weatherCode")));
        int windDegreeDay = getAVGIntParam(hourly, "winddirDegree", dayTimeValues) + 40 + 180;
        int windDegreeNight = getAVGIntParam(hourly, "winddirDegree", nightTimeValues) + 40 + 180;
        double avgPressureInchDay = new BigDecimal(avgPressureDay * 0.000296133971008484).setScale(2, BigDecimal.ROUND_UP).doubleValue();  //convert pressure from PA to inches
        double avgPressureInchNight = new BigDecimal(avgPressureNight * 0.000296133971008484).setScale(2, BigDecimal.ROUND_UP).doubleValue();  //convert pressure from PA to inches
        int avgWindMsDay = (int)Math.round(avgWindKmhDay*0.27777777777778);
        int avgWindMsNight = (int)Math.round(avgWindKmhNight * 0.27777777777778);
        int maxGustMsDay = (int)Math.round(maxGustKmhDay * 0.27777777777778);
        int maxGustMsNight = (int)Math.round(maxGustKmhNight * 0.27777777777778);
        double precipDayIn = new BigDecimal(precipDayMM * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        double precipNightIn = new BigDecimal(precipNightMM * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        String winddirDay = "" + hourly.get(14).get("winddir16Point");
        String winddirNight = "" + hourly.get(2).get("winddir16Point");


        HashMap<String, HashMap> result = new HashMap<>();
        HashMap<String, Object> dayMap = new HashMap<>();
        HashMap<String, Object> wholeDayMap = new HashMap<>();
        wholeDayMap.put("dayOfMonth", dateTime.getDayOfMonth());
        wholeDayMap.put("monthOfYear", DateConstants.convertMonthOfYear(dateTime.getMonthOfYear()));
        wholeDayMap.put("dayOfWeek", DateConstants.convertDayOfWeek(dateTime.getDayOfWeek()));
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
        dayMap.put("precipMm", precipDayMM);
        dayMap.put("precipIn", precipDayIn);
        dayMap.put("windMph", avgWindMDay);
        dayMap.put("windMs", avgWindMsDay);
        dayMap.put("gustMph", maxGustMDay);
        dayMap.put("gustMs", maxGustMsDay);
        dayMap.put("pressurehPa", avgPressureDay);
        dayMap.put("pressureInch", avgPressureInchDay);
        dayMap.put("windDegree", windDegreeDay);
        dayMap.put("weatherCode", dayWeatherCode);
        dayMap.put("winddir", winddirDay);

        HashMap<String, Object> nightMap = new HashMap<>();
        nightMap.put("time", "Night");
        nightMap.put("avgTempC", avgNightC);
        nightMap.put("avgTempF", avgNightF);
        nightMap.put("feelsLikeC", maxFeelLikeNightC);
        nightMap.put("feelsLikeF", maxFeelLikeNightF);
        nightMap.put("precipChance", precipeChanceNight);
        nightMap.put("precipMm", precipNightMM);
        nightMap.put("precipIn", precipNightIn);
        nightMap.put("windMph", avgWindMNight);
        nightMap.put("windMs", avgWindMsNight);
        nightMap.put("gustMs", maxGustMsNight);
        nightMap.put("gustMph", maxGustMNight);
        nightMap.put("pressurehPa", avgPressureNight);
        nightMap.put("pressureInch", avgPressureInchNight);
        nightMap.put("windDegree", windDegreeNight);
        nightMap.put("weatherCode", nightWeatherCode);
        nightMap.put("winddir", winddirNight);

        result.put("wholeDay", wholeDayMap);
        result.put("Day", dayMap);
        result.put("Night", nightMap);

        return result;
    }



    private double getSumDoubleParam(List<HashMap> hourly, String paramName, List<Integer> dayTimeValues){

        hourly = hourly.stream().filter(hashMap ->
                dayTimeValues.contains(parseInt(hashMap.get("time"))))
                .collect(Collectors.toList());

        DoubleSummaryStatistics params = hourly.stream().mapToDouble((value) ->
                parseDouble(value.get(paramName))).summaryStatistics();
        return params.getSum();

    }
    private Integer getAVGIntParam(List<HashMap> hourly, String paramName, List<Integer> dayTimeValues){

        hourly = hourly.stream().filter(hashMap ->
                dayTimeValues.contains(parseInt(hashMap.get("time"))))
                .collect(Collectors.toList());


        IntSummaryStatistics params = hourly.stream().mapToInt((value) ->
                parseInt(value.get(paramName))).summaryStatistics();
        return (int)Math.round(params.getAverage());
    }

    private Integer parseInt(Object o){
        return Integer.parseInt(String.valueOf(o));
    }
    private Double parseDouble(Object o){
        return Double.parseDouble(String.valueOf(o));
    }


     String moon_phase_name[] = { "New Moon", // 0
     "Crescent", // 1
     "First quarter", // 2
     "Gibbous", // 3
     "Full Moon", // 4
     "Gibbous", // 5
     "Third quarter", // 6
     "Crescent"}; //7

    private String convertMoonPhaseIndexToName(int moonPhaseIndex){
        switch (moonPhaseIndex){
            case 0 :
                return moon_phase_name[0];
            case 1 :
                return moon_phase_name[1];
            case 2 :
                return moon_phase_name[2];
            case 3 :
                return moon_phase_name[3];
            case 4 :
                return moon_phase_name[4];
            case 5 :
                return moon_phase_name[5];
            case 6 :
                return moon_phase_name[6];
            case 7 :
                return moon_phase_name[7];
            default: return null;
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

        result.put("city", cityName.replaceAll("%20", " "));
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


        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        JSONObject jsonObject = null;

        try{
           jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=6&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))));
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        HashMap result = new HashMap<>();


        HashMap dateMap = new HashMap();
        int moonPhaseIndex = getMoonPhase(city);

        dateMap.put("dayOfMonth", dateTime.getDayOfMonth());
        dateMap.put("monthOfYear", convertMonthOfYearShort(dateTime.getMonthOfYear()));
        dateMap.put("year", dateTime.getYear());
        result.put("date", dateMap);
        result.put("sunrise", ((HashMap)((ArrayList)((HashMap)((ArrayList)map.get("weather")).get(0)).get("astronomy")).get(0)).get("sunrise"));
        result.put("sunset", ((HashMap)((ArrayList)((HashMap)((ArrayList)map.get("weather")).get(0)).get("astronomy")).get(0)).get("sunset"));
        result.put("moon_phase_index", moonPhaseIndex);
        result.put("moon_phase_name", convertMoonPhaseIndexToName(moonPhaseIndex+1));
        result.put("moon_phase_state", getMoonState(city));
        return result;
    }

    private int getMoonPhase(JSONObject city){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        return new MoonPhase(calendar).getPhaseIndex()-1;
    }

    private String getMoonState(JSONObject city){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        MoonPhase moonPhase = new MoonPhase(calendar);
        double waningIndex = moonPhase.getPhase();
        return waningIndex<0?"Waning":"Waxing";
    }

        public HashMap getRemoteData(JSONObject city){

            String cityName = validateCityName((String)city.get("asciiName"));
            DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
            JSONObject jsonObject = null;
        try {
          jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date=" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() + "&q=" + String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))));
        }catch (IOException e){
            e.printStackTrace();
        }
            HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
            HashMap weatherData = weather.get(0);
            HashMap currentConditions = ((HashMap)((ArrayList)map.get("current_condition")).get(0));


        HashMap<String, Object> result = new HashMap<>();

        result.put("city", cityName.replaceAll("%20", " "));
        result.put("country", city.get("countryName"));
        result.put("month", DateConstants.convertMonthOfYear(dateTime.getMonthOfYear()));
        result.put("day", dateTime.getDayOfMonth());
        result.put("dayOfWeek", DateConstants.convertDayOfWeek(dateTime.getDayOfWeek()));
        result.put("hours", dateTime.getHourOfDay());
        result.put("minutes", dateTime.getMinuteOfHour());
        result.put("temp_c", currentConditions.get("temp_C"));
        result.put("temp_f", currentConditions.get("temp_F"));
        result.put("feelsLikeC", currentConditions.get("FeelsLikeC"));
        result.put("feelsLikeF", currentConditions.get("FeelsLikeF"));
        result.put("humidity", currentConditions.get("humidity"));
        result.put("pressurehPa", currentConditions.get("pressure"));
        result.put("pressureInch", new BigDecimal(parseInt(currentConditions.get("pressure")) * 0.000296133971008484).setScale(2, BigDecimal.ROUND_UP).doubleValue());
        result.put("windMph",  currentConditions.get("windspeedMiles"));
        result.put("windMs", (int)Math.round(parseInt(currentConditions.get("windspeedKmph"))*0.27777777777778));
        result.put("direction", currentConditions.get("winddir16Point"));
        result.put("windDegree", parseInt(currentConditions.get("winddirDegree"))+180);
        result.put("sunrise", ((HashMap)((ArrayList)weatherData.get("astronomy")).get(0)).get("sunrise"));
        result.put("sunset", ((HashMap)((ArrayList)weatherData.get("astronomy")).get(0)).get("sunset"));
        result.put("weatherIconCode", ""+(EXT_STATES.get(parseInt(currentConditions.get("weatherCode")))));

        return result;
    }


    public List<HashMap> getDetailedForecastForToday(JSONObject city){
        JSONObject jsonObject = null;
        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        try {
            jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))));
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> hourly = ((ArrayList)((HashMap)((ArrayList)(map.get("weather"))).get(0)).get("hourly"));

        String month = dateTime.getMonthOfYear() > 9? "" + dateTime.getMonthOfYear():"0" + dateTime.getMonthOfYear();
        String day = dateTime.getDayOfMonth() > 9 ? "" + dateTime.getDayOfMonth():"0" + dateTime.getDayOfMonth();
        String date = dateTime.getYear() + "-" +
                month + "-" +
                day;
        List<HashMap> result = new ArrayList<>();
        for (HashMap aHourly : hourly) {
            HashMap m = new HashMap();
            m.put("tempC", aHourly.get("tempC"));
            m.put("tempF", aHourly.get("tempF"));
            m.put("date", date);
            m.put("precipMM", aHourly.get("precipMM"));
            m.put("precipInch", new BigDecimal(parseDouble(aHourly.get("precipMM")) * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue());
            m.put("weatherIcon",  "" + EXT_STATES.get(parseInt(aHourly.get("weatherCode"))));
            result.add(m);
        }

        return result;
        }

    public List<DetailedForecastGraphMapping> getDetailedForecastMapping(JSONObject city){
            List<DetailedForecastGraphMapping> result = new ArrayList<>();

        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        for (int day = 0; day < 14; day++) {
            result.add(getSingleDetailedForecastMapping(dateTime.plusDays(day), city));
        }
        return result;
    }
    private DetailedForecastGraphMapping getSingleDetailedForecastMapping(DateTime dateTime, JSONObject city){
        
        JSONObject jsonObject = null;
        try {
            jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))));
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> hourly2PmData = ((ArrayList)((HashMap)((ArrayList)(map.get("weather"))).get(0)).get("hourly"));


        int tempC = parseInt(hourly2PmData.get(14).get("tempC"));
        int tempF = parseInt(hourly2PmData.get(14).get("tempF"));
        double precipMM = getOneDayTotalRainfall(hourly2PmData).getSum();
        double precipInch = new BigDecimal(precipMM * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        String weatherIconCode = "" + EXT_STATES.get(parseInt(hourly2PmData.get(14).get("weatherCode")));

        String month = dateTime.getMonthOfYear() > 9? "" + dateTime.getMonthOfYear():"0" + dateTime.getMonthOfYear();
        String day = dateTime.getDayOfMonth() > 9 ? "" + dateTime.getDayOfMonth():"0" + dateTime.getDayOfMonth();
        String date = dateTime.getYear() + "-" +
                month + "-" +
                day;

            return new DetailedForecastGraphMapping(tempC,tempF,date, precipMM, precipInch,  weatherIconCode);
    }

    public List<HashMap> getWeeklyUltraviolet(JSONObject city){

        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        List<HashMap> list = new ArrayList<>();
        for (int day = 0; day < 5; day++) {
            list.add(getSingleUltravioletIndex(dateTime.plusDays(day), city));
        }
        return list;
    }
    private HashMap getSingleUltravioletIndex(DateTime dateTime, JSONObject city){

            JSONObject jsonObject = null;
            try {
                jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=24&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q="+ String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))));
            }catch (IOException e){
                e.printStackTrace();
            }
        HashMap map = (HashMap)jsonObject.toMap().get("data");

        HashMap res = new HashMap();
        res.put("index",
                ((HashMap)((ArrayList)map.get("weather")).get(0)).get("uvIndex"));
        res.put("date", DateConstants.convertDayOfWeekShort(dateTime.getDayOfWeek())+" " +
                dateTime.getDayOfMonth()+" "
                + convertMonthOfYearShort(dateTime.getMonthOfYear()));
        return res;
    }

    public List getFiveYearsAverage(JSONObject city){

        String cityName = validateCityName((String)city.get("asciiName"));

        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));

        ArrayList output = new ArrayList();

        for (int i = 0; i < 6; i++) {
            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime.minusYears(i), cityName,
                     i > 0, false, 1, String.valueOf(city.get("lat")), String.valueOf(city.get("lng")));
            HashMap map = apiWeatherFinder.findWeatherByDate();

            ArrayList param = (ArrayList) MyJsonHelper.getParam(map, "weather", "hourly");

            int tempC = parseInt(((HashMap) param.get(14)).get("tempC"));
            int tempF = parseInt(((HashMap) param.get(14)).get("tempF"));
            String weatherCode = ""+EXT_STATES.get(Integer.parseInt((String)((HashMap) param.get(14)).get("weatherCode")));
            HashMap result = new HashMap();
            result.put("year", dateTime.minusYears(i).getYear());
            result.put("tempC", tempC);
            result.put("tempF", tempF);
            result.put("weatherCode", weatherCode);

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
    }        public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONArray json = new JSONArray(jsonText);
                return json;
            } finally {
                is.close();
            }
    }

    public HashMap getWeeklyWeatherSummary(JSONObject city){

        DateTime dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        DateTime dt1 = null;
        List<HashMap> week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if(i > 0){
                dt1 = dt1.plusDays(i - (i-1));
            }else{
                dt1 = dateTime;
            }
            JSONObject jsonObject = null;
            try {
                jsonObject = readJsonFromUrl("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=1&date="+ dt1.getYear() + "-" + dt1.getMonthOfYear() + "-" + dt1.getDayOfMonth()+ "&q=" + String.valueOf(city.get("lat")+ "," + String.valueOf(city.get("lng"))));
                week.add(((HashMap)(((ArrayList)((JSONObject)jsonObject.get("data")).toMap().get("weather")).get(0))));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        IntSummaryStatistics maxtempCavges = week.stream().mapToInt((value) ->
                parseInt(value.get("maxtempC"))).summaryStatistics();

        IntSummaryStatistics maxtempFavges = week.stream().mapToInt((value) ->
                parseInt(value.get("maxtempF"))).summaryStatistics();

        IntSummaryStatistics mintempFavges = week.stream().mapToInt((value) ->
                parseInt(value.get("mintempF"))).summaryStatistics();

        IntSummaryStatistics mintempCavges = week.stream().mapToInt((value) ->
                parseInt(value.get("mintempC"))).summaryStatistics();

        int maxTempC = maxtempCavges.getMax();
        int avgMaxTempC = (int)maxtempCavges.getAverage();
        int maxTempF = maxtempFavges.getMax();
        int avgMaxTempF = (int)maxtempFavges.getAverage();
        DateTime maxTempDateTime = new DateTime(week.stream().filter(hashMap -> parseInt(hashMap.get("maxtempC"))==maxTempC).findAny().get().get("date"));
        String maxTempDay = convertMonthOfYearShort(maxTempDateTime.getMonthOfYear()).toUpperCase() + "."+maxTempDateTime.getDayOfMonth();
        int minTempC = mintempCavges.getMin();
        int avgMinTempC = (int)mintempCavges.getAverage();
        int minTempF = mintempFavges.getMin();
        int avgMinTempF = (int)mintempFavges.getAverage();
        DateTime minTempDateTime = new DateTime(week.stream().filter(hashMap -> parseInt(hashMap.get("mintempC"))==minTempC).findAny().get().get("date"));
        String minTempDay = convertMonthOfYearShort(minTempDateTime.getMonthOfYear()).toUpperCase() + "."+minTempDateTime.getDayOfMonth();
        double totalRainfallMM = getTotalRainfall(week);
        double totalRainfallInch = new BigDecimal(totalRainfallMM * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        int windiestMiles = getWindiestMiles(week);
        int windiestMS = (int)Math.round(getWindiestKmph(week)*0.27777777777778);
        String foundWindiest = (String)getWindiestDay(week, windiestMiles).get("date");
        DateTime windiestDateTime = new DateTime(foundWindiest);
        String windiestDay = convertMonthOfYearShort(windiestDateTime.getMonthOfYear()).toUpperCase() + "."+windiestDateTime.getDayOfMonth();

       HashMap<String, Object> results = new HashMap<>();

       results.put("maxTempC", maxTempC);
       results.put("maxTempF", maxTempF);
       results.put("avgMaxTempC", avgMaxTempC);
       results.put("avgMaxTempF", avgMaxTempF);
       results.put("minTempC", minTempC);
       results.put("minTempF", minTempF);
       results.put("avgMinTempC", avgMinTempC);
       results.put("avgMinTempF", avgMinTempF);
       results.put("totalRainfallMM", totalRainfallMM);
       results.put("totalRainfallInch", totalRainfallInch);
       results.put("windiestMiles", windiestMiles);
       results.put("windiestMS", windiestMS);
       results.put("maxTempDay", maxTempDay);
       results.put("minTempDay", minTempDay);
       results.put("windiestDay", windiestDay);
       return results;
    }

    private double getTotalRainfall(List<HashMap> week){
        double total = 0;
        for (int i = 0; i < week.size(); i++) {
            ArrayList<HashMap> hourly = (ArrayList<HashMap>) week.get(i).get("hourly");
            DoubleSummaryStatistics maxPrecipMMs = getOneDayTotalRainfall(hourly);
            total+=maxPrecipMMs.getSum();

        }
        return total;
    }

    DoubleSummaryStatistics getOneDayTotalRainfall(ArrayList<HashMap> hourly){
        return  hourly.stream().mapToDouble((value) ->
                parseDouble(value.get("precipMM"))).summaryStatistics();
    }

    private HashMap getWindiestDay(List<HashMap> week, int windiestValue){
        for (int i = 0; i < week.size(); i++) {
            List<HashMap> hourlylist = new ArrayList<>();
            for (int j = 0; j < 24; j++) {
             hourlylist.add((HashMap)((ArrayList)week.get(i).get("hourly")).get(j));
            }
            try {
                HashMap elem = hourlylist.stream().filter(hashMap -> hashMap.get("windspeedMiles").toString().equals(String.valueOf(windiestValue))).findAny().get();
                if(week.get(i).get("hourly").equals(hourlylist)){
                    return week.get(i);
                }
            }catch (NoSuchElementException e){
                continue;
            }
        }
        return null;
    }
    private int getWindiestKmph(List<HashMap> week){
        int theWindiestKmph = 0;
        for (int i = 0; i < week.size(); i++) {
            ArrayList<HashMap> hourly = (ArrayList<HashMap>) week.get(i).get("hourly");
            IntSummaryStatistics maxWindiestKmphs = hourly.stream().mapToInt((value) ->
                    parseInt(value.get("windspeedKmph"))).summaryStatistics();
            int windiestInDay = maxWindiestKmphs.getMax();
            if(windiestInDay > theWindiestKmph){
                theWindiestKmph = windiestInDay;
            }
        }
        return theWindiestKmph;
    }

    private int getWindiestMiles(List<HashMap> week){
        int theWindiestMiles = 0;
        for (int i = 0; i < week.size(); i++) {
            ArrayList<HashMap> hourly = (ArrayList<HashMap>) week.get(i).get("hourly");
            IntSummaryStatistics maxWindiestMMs = hourly.stream().mapToInt((value) ->
                    parseInt(value.get("windspeedMiles"))).summaryStatistics();

            int windiestInDay = maxWindiestMMs.getMax();

            if(windiestInDay> theWindiestMiles){
                theWindiestMiles = windiestInDay;
            }

        }
        return theWindiestMiles;
    }

    public List getDynamicTableData(JSONObject city, int numOfHours, int numOfDays, boolean pastWeather, String date){

        String cityName = validateCityName((String)city.get("asciiName"));
        DateTime dateTime = null;
        if(date == null){
           dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        }else{
            dateTime = new DateTime(date);
        }

        List list = new ArrayList();
        for (int i = 0; i < numOfDays; i++) {

            if(i > 0){
                dateTime = dateTime.plusDays(i - (i-1));
            }
            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, cityName,
                    pastWeather, true, numOfHours, String.valueOf(city.get("lat")), String.valueOf(city.get("lng")));

            HashMap data = apiWeatherFinder.findWeatherByDate();
            HashMap weather = ((HashMap)((ArrayList)data.get("weather")).get(0));
            ArrayList hourly = (ArrayList)weather.get("hourly");
            List results = new ArrayList();

            HashMap<String, Object> wholeDayMap = new HashMap<>();

            wholeDayMap.put("dayOfMonth", dateTime.getDayOfMonth());
            wholeDayMap.put("monthOfYear", DateConstants.convertMonthOfYear(dateTime.getMonthOfYear()));
            wholeDayMap.put("year", dateTime.getYear());
            wholeDayMap.put("dayOfWeek", DateConstants.convertDayOfWeek(dateTime.getDayOfWeek()));
            wholeDayMap.put("todaySign", i==0?"Today":"Tomorrow");
            wholeDayMap.put("maxtempC", weather.get("maxtempC"));
            wholeDayMap.put("mintempC", weather.get("mintempC"));
            wholeDayMap.put("maxtempF", weather.get("maxtempF"));
            wholeDayMap.put("mintempF", weather.get("mintempF"));
            wholeDayMap.put("sunrise", ((HashMap)((ArrayList)weather.get("astronomy")).get(0)).get("sunrise"));
            wholeDayMap.put("sunset", ((HashMap)((ArrayList)weather.get("astronomy")).get(0)).get("sunset"));
            wholeDayMap.put("weatherCode", "" + EXT_STATES.get(parseInt(hourly.size()==8?(((HashMap)hourly.get(5)).get("weatherCode")):(((HashMap)hourly.get(15)).get("weatherCode")))));
            wholeDayMap.put("isDay", dateTime.getHourOfDay()>6 && dateTime.getHourOfDay()<18);

            List<HashMap> oneWholeDayData = new ArrayList<>();

            for (int j = 0; j < hourly.size(); j++) {
                HashMap<String, Object> dayMap = new HashMap<>();
                HashMap elem = (HashMap)hourly.get(j);

                dayMap.put("time", DateConstants.convertTimeToAmPm(parseInt(elem.get("time"))));
                dayMap.put("weatherCode", "" + EXT_STATES.get(parseInt(elem.get("weatherCode"))));
                dayMap.put("tempC", elem.get("tempC"));
                dayMap.put("tempF", elem.get("tempF"));
                dayMap.put("feelsLikeC", elem.get("FeelsLikeC"));
                dayMap.put("feelsLikeF", elem.get("FeelsLikeF"));
                dayMap.put("precipChance", pastWeather?"0":elem.get("chanceofrain"));
                dayMap.put("precipMM", elem.get("precipMM"));
                dayMap.put("precipInch", new BigDecimal(parseDouble(elem.get("precipMM")) * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue());
                dayMap.put("windMph", elem.get("windspeedMiles"));
                dayMap.put("windMs", (int)Math.round(parseInt(elem.get("windspeedKmph"))*0.27777777777778));
                dayMap.put("winddir", elem.get("winddir16Point"));
                dayMap.put("windDegree", parseInt(elem.get("winddirDegree")) + 40 + 180);
                dayMap.put("gustMph", elem.get("WindGustMiles"));
                dayMap.put("gustMs", (int)Math.round(parseInt(elem.get("WindGustKmph"))*0.27777777777778));
                dayMap.put("cloudCover", elem.get("cloudcover"));
                dayMap.put("humidity", elem.get("humidity"));
                dayMap.put("pressurehPa", elem.get("pressure"));
                dayMap.put("pressureInch", new BigDecimal(parseInt(elem.get("pressure")) * 0.000296133971008484).setScale(2, BigDecimal.ROUND_UP).doubleValue());
                dayMap.put("isDay", parseInt(elem.get("time")) >= 600 && parseInt(elem.get("time")) < 1800);
                oneWholeDayData.add(dayMap);
            }
            results.add(oneWholeDayData);
            results.add(wholeDayMap);

            list.add(results);
        }


        return list;
    }

    private String dayTimes[] = {"Night", "Morning", "Midday", "Evening"};
    private int dayTimesHours[]= {2, 8, 14, 20};

    public List getTableDataForDays(JSONObject city, int numOfHours, int numOfDays, boolean pastWeather, String date){

        String cityName = validateCityName((String)city.get("asciiName"));
        DateTime dateTime = null;
        if(date == null){
            dateTime = new DateTime(DateTimeZone.forID((String)((JSONObject)city.get("timezone")).get("timeZoneId")));
        }else{
            dateTime = new DateTime(date);
        }

        List list = new ArrayList();
        for (int i = 0; i < numOfDays; i++) {
            if(i > 0){
                dateTime = dateTime.plusDays(i - (i-1));
            }

            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, cityName,
                    pastWeather, true, numOfHours, String.valueOf(city.get("lat")), String.valueOf(city.get("lng")));

            HashMap data = apiWeatherFinder.findWeatherByDate();
            HashMap weather = ((HashMap)((ArrayList)data.get("weather")).get(0));
           // HashMap currentCondition = (HashMap) ((ArrayList)(data).get("current_condition")).get(0);
            ArrayList hourly = (ArrayList)weather.get("hourly");

            List results = new ArrayList();

            HashMap<String, Object> wholeDayMap = new HashMap<>();

            wholeDayMap.put("dayOfMonth", dateTime.getDayOfMonth());
            wholeDayMap.put("monthOfYear", DateConstants.convertMonthOfYear(dateTime.getMonthOfYear()).substring(0,3));
            wholeDayMap.put("dayOfWeek", DateConstants.convertDayOfWeekShort(dateTime.getDayOfWeek()));
            wholeDayMap.put("maxtempC", weather.get("maxtempC"));
            wholeDayMap.put("mintempC", weather.get("mintempC"));
            wholeDayMap.put("maxtempF", weather.get("maxtempF"));
            wholeDayMap.put("mintempF", weather.get("mintempF"));
            wholeDayMap.put("weatherCode", "" + EXT_STATES.get(parseInt(((HashMap)hourly.get(8)).get("weatherCode"))));
            wholeDayMap.put("isDay", dateTime.getHourOfDay()>6 && dateTime.getHourOfDay()<18);

            List<HashMap> oneWholeDayData = new ArrayList<>();

            for (int dayTime = 0; dayTime < dayTimes.length; dayTime++) {
                HashMap<String, Object> dayMap = new HashMap<>();
                HashMap elem = (HashMap)hourly.get(dayTimesHours[dayTime]);

                dayMap.put("time", dayTimes[dayTime]);
                dayMap.put("weatherCode", "" + EXT_STATES.get(parseInt(elem.get("weatherCode"))));
                dayMap.put("tempC", elem.get("tempC"));
                dayMap.put("tempF", elem.get("tempF"));
                dayMap.put("feelsLikeC", elem.get("FeelsLikeC"));
                dayMap.put("feelsLikeF", elem.get("FeelsLikeF"));
                dayMap.put("precipChance", elem.get("chanceofrain"));
                dayMap.put("precipMM", elem.get("precipMM"));
                dayMap.put("precipInch", new BigDecimal(parseDouble(elem.get("precipMM")) * 0.0393700787).setScale(2, BigDecimal.ROUND_UP).doubleValue());
                dayMap.put("windMph", elem.get("windspeedMiles"));
                dayMap.put("windMs", (int)Math.round(parseInt(elem.get("windspeedKmph"))*0.27777777777778));
                dayMap.put("winddir", elem.get("winddir16Point"));
                dayMap.put("windDegree", parseInt(elem.get("winddirDegree")) + 40 + 180);
                dayMap.put("gustMph", elem.get("WindGustMiles"));
                dayMap.put("gustMs", (int)Math.round(parseInt(elem.get("WindGustKmph"))*0.27777777777778));
                dayMap.put("pressurehPa", elem.get("pressure"));
                dayMap.put("pressureInch", new BigDecimal(parseInt(elem.get("pressure")) * 0.000296133971008484).setScale(2, BigDecimal.ROUND_UP).doubleValue());
                dayMap.put("isDay", dayTimesHours[dayTime] == 14 || dayTimesHours[dayTime] == 8);
                oneWholeDayData.add(dayMap);
            }
            results.add(oneWholeDayData);
            results.add(wholeDayMap);

            list.add(results);
        }


        return list;

    }
}
