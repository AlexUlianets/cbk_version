package com.oplao.Controller;

import com.oplao.model.*;
import com.oplao.service.SearchService;
import com.oplao.service.TextWeatherService;
import com.oplao.service.WeatherService;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class OutlookController {


    @Autowired
    WeatherService weatherService;
    @Autowired
    SearchService searchService;

    @RequestMapping("/set_current_location_cookie")
    public void setCurrentLocationCookie(HttpServletRequest request, HttpServletResponse response){
       searchService.setCurrentLocationCookie(request, response);
    }
    @RequestMapping("/get_coordinates")
    @ResponseBody
    public HashMap getCoordinates(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }
        return weatherService.getCoordinates(selectedCity);
    }
    @RequestMapping("/get_api_weather")
    @ResponseBody
    public OutlookWeatherMapping getApiWeather(HttpServletRequest request, HttpServletResponse response) throws Exception{

        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }
        return weatherService.getRemoteData(selectedCity);
    }

    @RequestMapping("/get_astronomy")
    @ResponseBody
    public HashMap getAstronomy(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }

        return weatherService.getAstronomy(selectedCity);
    }

    @RequestMapping("/get_weekly_weather_summary")
    @ResponseBody
    public WeeklyWeatherSummaryMapping getWeeklySummary(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }
        DateTime dateTime = new DateTime();
        DateTime closestMondayDateTime = dateTime.minusDays(dateTime.getDayOfWeek()-1);

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
    public List<DetailedForecastGraphMapping> getDetailedForecastMapping(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }
        return weatherService.getDetailedForecastMapping(selectedCity);
    }
    @RequestMapping("/get_weekly_weather")
    @ResponseBody
    public HashMap<Integer, HashMap<String,WeeklyWeatherReportMapping>>  getWeeklyWeather(HttpServletRequest request, HttpServletResponse response){

        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }
        return weatherService.getWeeklyWeatherReport(selectedCity);
    }

    @RequestMapping("/get_year_summary")
    @ResponseBody
    public List<HashMap> getYearSummary(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }

        return weatherService.getYearSummary(selectedCity);

    }
    @RequestMapping("/get_five_years_average")
    @ResponseBody
    public List getFiveYearsAverage(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }
        return weatherService.getFiveYearsAverage(selectedCity);
    }

    @RequestMapping("/get_weekly_ultraviolet_index")
    @ResponseBody
    public List getWeeklyUltravioletIndex(HttpServletRequest request, HttpServletResponse response){
        JSONObject selectedCity = null;
        try {
            selectedCity = searchService.getSelectedCity();
        }catch (Exception e){
            searchService.setCurrentLocationCookie(request, response);
            selectedCity = searchService.getSelectedCity();
        }

        return weatherService.getWeeklyUltraviolet(selectedCity);
    }

    @RequestMapping("get_recent_cities_tabs")
    @ResponseBody
    public List<HashMap> getRecentCitiesTabs(){

        return searchService.createRecentCitiesTabs();
    }
    }
