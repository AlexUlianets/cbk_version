package com.oplao.service;

import com.oplao.Utils.DateConstants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.oplao.service.WeatherService.EXT_STATES;
import static com.oplao.service.WeatherService.dayTimes;
import static com.oplao.service.WeatherService.dayTimesHours;

@Service
public class WidgetService {


    public HashMap getInfoWidgets(String city, String lang, String currentCookieLang) {

        if (lang == null || lang.equals("")) {
            lang = currentCookieLang;
        }

        JSONObject currentCity = null;
        try {
            currentCity = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=en&max=10&geonameId=" + city).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateTime dateTime = new DateTime(DateTimeZone.forID((String) ((JSONObject) currentCity.get("timezone")).get("timeZoneId")));

        APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, "",
                false, true, 1, String.valueOf(currentCity.get("lat")), String.valueOf(currentCity.get("lng")));
        HashMap map = apiWeatherFinder.findWeatherByDate();

        HashMap<String, Object> res = new HashMap<>();
        HashMap currentConditions = ((HashMap) ((ArrayList) map.get("current_condition")).get(0));
        res.put("hours", dateTime.getHourOfDay());
        res.put("time", dateTime.getHourOfDay() + ":" + dateTime.getMinuteOfHour());
        res.put("date", dateTime.getDayOfMonth() + " " + DateConstants.convertMonthOfYearShort(dateTime.getMonthOfYear()).toUpperCase());
        res.put("day", DateConstants.convertDayOfWeekShort(dateTime.getDayOfWeek()).toLowerCase());
        res.put("city", currentCity.getString("name"));
        res.put("countryCode", currentCity.getString("countryCode"));
        res.put("feelsLikeC", currentConditions.get("FeelsLikeC"));
        res.put("feelsLikeF", currentConditions.get("FeelsLikeF"));
        res.put("weatherIconCode", EXT_STATES.get(Integer.parseInt("" + currentConditions.get("weatherCode"))));
        res.put("clarity", EXT_STATES.get(Integer.parseInt("" + currentConditions.get("weatherCode"))));
        res.put("pressurehPa", currentConditions.get("pressure"));
        res.put("pressureInch", new BigDecimal(Integer.parseInt("" + currentConditions.get("pressure")) * 0.000296133971008484).setScale(2, BigDecimal.ROUND_UP).doubleValue());
        res.put("temp_c", currentConditions.get("temp_C"));
        res.put("temp_f", currentConditions.get("temp_F"));
        res.put("windMph", currentConditions.get("windspeedMiles"));
        res.put("windMs", (int) Math.round(Integer.parseInt("" + currentConditions.get("windspeedKmph")) * 0.27777777777778));
        res.put("windDegree", currentConditions.get("winddirDegree"));
        res.put("threeDays", getThreeDaysWeatherForWidgets(dateTime, currentCity));
        res.put("wholeDay", getWholeDayWeatherForWidgets(dateTime, currentCity));
        return res;
    }

    private List<HashMap> getThreeDaysWeatherForWidgets(DateTime dateTime, JSONObject currentCity) {

        List<HashMap> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, "",
                    false, true, 1, String.valueOf(currentCity.get("lat")), String.valueOf(currentCity.get("lng")));
            dateTime = dateTime.plusDays(1);
            HashMap map = apiWeatherFinder.findWeatherByDate();
            HashMap weather2Pm = ((HashMap) ((ArrayList) ((HashMap) ((ArrayList) map.get("weather")).get(0)).get("hourly")).get(14));
            HashMap<String, Object> res = new HashMap<>();
            res.put("date", dateTime.getDayOfMonth() + " " + DateConstants.convertMonthOfYearShort(dateTime.getMonthOfYear()).toUpperCase());
            res.put("day", DateConstants.convertDayOfWeekShort(dateTime.getDayOfWeek()).toLowerCase());
            res.put("icon", EXT_STATES.get(Integer.parseInt("" + weather2Pm.get("weatherCode"))));
            res.put("clarity", EXT_STATES.get(Integer.parseInt("" + weather2Pm.get("weatherCode"))));
            res.put("temp_c", weather2Pm.get("tempC"));
            res.put("temp_f", weather2Pm.get("tempF"));
            result.add(res);
        }
        return result;

    }

    private List<HashMap> getWholeDayWeatherForWidgets(DateTime dateTime, JSONObject currentCity) {
        List<HashMap> result = new ArrayList<>();
        APIWeatherFinder apiWeatherFinder = new APIWeatherFinder(dateTime, "",
                false, true, 1, String.valueOf(currentCity.get("lat")), String.valueOf(currentCity.get("lng")));
        dateTime = dateTime.plusDays(1);
        HashMap map = apiWeatherFinder.findWeatherByDate();

        List hourly = ((ArrayList) ((HashMap) ((ArrayList) map.get("weather")).get(0)).get("hourly"));
        for (int dayTime = 0; dayTime < dayTimes.length; dayTime++) {
            HashMap<String, Object> dayMap = new HashMap<>();
            HashMap elem = (HashMap) hourly.get(dayTimesHours[dayTime]);

            dayMap.put("name", dayTimes[dayTime]);

            dayMap.put("date", dateTime.getDayOfMonth() + " " + DateConstants.convertMonthOfYearShort(dateTime.getMonthOfYear()).toUpperCase());
            dayMap.put("day", DateConstants.convertDayOfWeekShort(dateTime.getDayOfWeek()).toLowerCase());
            dayMap.put("icon", EXT_STATES.get(Integer.parseInt("" + elem.get("weatherCode"))));
            dayMap.put("clarity", EXT_STATES.get(Integer.parseInt("" + elem.get("weatherCode"))));
            dayMap.put("temp_c", elem.get("tempC"));
            dayMap.put("temp_f", elem.get("tempF"));
            result.add(dayMap);
        }
        return result;

    }
}
