package com.oplao.Controller;

import com.oplao.model.*;
import com.oplao.service.SearchService;
import com.oplao.service.TextWeatherService;
import com.oplao.service.WeatherService;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class OutlookController {


    @Autowired
    WeatherService weatherService;
    @Autowired
    SearchService searchService;

    @RequestMapping("/get_coordinates")
    @ResponseBody
    public HashMap getCoordinates(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,HttpServletRequest request, HttpServletResponse response){

        return weatherService.getCoordinates(searchService.findSelectedCity(request, response, currentCookieValue));
    }
    @RequestMapping("/get_api_weather")
    @ResponseBody
    public OutlookWeatherMapping getApiWeather(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, HttpServletRequest request, HttpServletResponse response){

        return weatherService.getRemoteData(searchService.findSelectedCity(request, response, currentCookieValue));
    }

    @RequestMapping("/refresh_cookies")
    @ResponseBody
    public String refreshCookies(@CookieValue(value = SearchService.cookieName, defaultValue = "")String currentCookieValue){
        return currentCookieValue;
    }
    @RequestMapping("/get_astronomy")
    @ResponseBody
    public HashMap getAstronomy(@CookieValue(value = SearchService.cookieName, defaultValue = "")String currentCookieValue, HttpServletRequest request, HttpServletResponse response){

        return weatherService.getAstronomy(searchService.findSelectedCity(request, response, currentCookieValue));
    }

    @RequestMapping("/get_weekly_weather_summary")
    @ResponseBody
    public WeeklyWeatherSummaryMapping getWeeklySummary(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, HttpServletRequest request, HttpServletResponse response){

        DateTime dateTime = new DateTime();
        DateTime closestMondayDateTime = dateTime.minusDays(dateTime.getDayOfWeek()-1);
        JSONObject selectedCity = searchService.findSelectedCity(request, response, currentCookieValue);
        TextWeatherService service = new TextWeatherService();

        List<TextWeatherMapping> list = new ArrayList<>();
        for (int day = closestMondayDateTime.getDayOfWeek(); day <=7; day++) {
            list.add(service.getTextWeatherMapping(closestMondayDateTime.plusDays(day-1), selectedCity));
        }

        TextWeatherMapping maxDayTemp = list.stream()
                .max(Comparator.comparing(TextWeatherMapping::getMaxDayTempC)).orElse(new TextWeatherMapping());
        TextWeatherMapping minDayTemp = list.stream()
                .min(Comparator.comparing(TextWeatherMapping::getMinDayTempC)).orElse(new TextWeatherMapping());


        final int[] averageMaxC = {0};
        list.stream().forEach(textWeatherMapping -> averageMaxC[0] +=textWeatherMapping.getMaxDayTempC());

        final int[] averageMaxF = {0};
        list.stream().forEach(textWeatherMapping -> averageMaxF[0] +=textWeatherMapping.getMaxDayTempF());

        final int[] averageMinC = {0};
        list.stream().forEach(textWeatherMapping -> averageMinC[0] +=textWeatherMapping.getMinDayTempC());

        final int[] averageMinF = {0};
        list.stream().forEach(textWeatherMapping -> averageMinF[0] +=textWeatherMapping.getMinDayTempF());

        final double[] totalRainfall = {0};
        list.stream().forEach(textWeatherMapping -> totalRainfall[0] +=textWeatherMapping.getmaxRainfallMM());


        TextWeatherMapping windiestMS = list.stream().sorted(Comparator.comparing(TextWeatherMapping::getMaxWindMS).reversed()).findFirst().orElse(new TextWeatherMapping());

        double maxWindiestMS = windiestMS.getMaxWindMS();

        TextWeatherMapping windiestKmPh = list.stream().sorted(Comparator.comparing(TextWeatherMapping::getMaxWindKmPh).reversed()).findFirst().orElse(new TextWeatherMapping());

        double maxWindiestKmPh = windiestKmPh.getMaxWindKmPh();


        return new WeeklyWeatherSummaryMapping(maxDayTemp.getMaxDayTempC(), maxDayTemp.getMaxDayTempF(),
                minDayTemp.getMinDayTempC(), minDayTemp.getMinDayTempF(), averageMinC[0]/list.size(),
                averageMinF[0]/list.size(), averageMaxC[0]/list.size(), averageMaxF[0]/list.size(),
                totalRainfall[0],maxWindiestMS , maxWindiestKmPh);
    }

    @RequestMapping("/get_detailed_forecast")
    @ResponseBody
    public List<DetailedForecastGraphMapping> getDetailedForecastMapping(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,HttpServletRequest request, HttpServletResponse response){
        return weatherService.getDetailedForecastMapping(searchService.findSelectedCity(request, response, currentCookieValue));
    }
    @RequestMapping("/get_weekly_weather")
    @ResponseBody

    public HashMap<Integer, HashMap<String,HashMap>>  getWeeklyWeather(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,HttpServletRequest request, HttpServletResponse response){

        return weatherService.getWeeklyWeatherReport(searchService.findSelectedCity(request, response, currentCookieValue));
    }

    @RequestMapping("/get_year_summary")
    @ResponseBody
    public List<HashMap> getYearSummary(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,HttpServletRequest request, HttpServletResponse response){
        return weatherService.getYearSummary(searchService.findSelectedCity(request, response, currentCookieValue));

    }
    @RequestMapping("/get_five_years_average")
    @ResponseBody
    public List getFiveYearsAverage(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,HttpServletRequest request, HttpServletResponse response){
        return weatherService.getFiveYearsAverage(searchService.findSelectedCity(request, response, currentCookieValue));
    }

    @RequestMapping("/get_weekly_ultraviolet_index")
    @ResponseBody
    public List getWeeklyUltravioletIndex(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,HttpServletRequest request, HttpServletResponse response){

        return weatherService.getWeeklyUltraviolet(searchService.findSelectedCity(request, response, currentCookieValue));
    }

    @RequestMapping("get_recent_cities_tabs")
    @ResponseBody
    public List<HashMap> getRecentCitiesTabs(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue){

        return searchService.createRecentCitiesTabs(currentCookieValue);
    }

    @RequestMapping("/deleteCity/{geonameId}")
    @ResponseBody
    public HttpStatus deleteCity(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,
                                 @PathVariable("geonameId") String geonameId,
                                 HttpServletRequest request, HttpServletResponse response){

            Cookie c = searchService.deleteCity(currentCookieValue, geonameId, request, response);

            if(c !=null){
                response.addCookie(c);
            }else {
                return HttpStatus.BAD_REQUEST;
            }

       return HttpStatus.OK;
    }
    }
