package com.oplao.Controller;


import com.oplao.model.*;
import com.oplao.service.TextWeatherService;
import com.oplao.service.WeatherService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class OutlookController {


    @RequestMapping("/get_coordinates")
    @ResponseBody
    public HashMap getCoordinates(){
        return new WeatherService().getCoordinates();
    }
    @RequestMapping("/get_api_weather")
    @ResponseBody
    public OutlookWeatherMapping getApiWeather() throws Exception{


        WeatherService service = new WeatherService();


        return service.getRemoteData();
    }

    @RequestMapping("/get_text_weather")
    @ResponseBody
    public OutlookWeatherMapping getTextWeather(){

        DateTime dateTime = new DateTime();
        DateTime closestMondayDateTime = dateTime.minusDays(dateTime.getDayOfWeek()-1);

        return null;
    }

    @RequestMapping("/get_astronomy")
    @ResponseBody
    public HashMap getAstronomy(){

        return new WeatherService().getAstronomy();
    }

    @RequestMapping("/get_weekly_weather_summary")
    @ResponseBody
    public WeeklyWeatherSummaryMapping getWeeklySummary(){

        DateTime dateTime = new DateTime();
        DateTime closestMondayDateTime = dateTime.minusDays(dateTime.getDayOfWeek()-1);

        TextWeatherService service = new TextWeatherService();

        List<TextWeatherMapping> list = new ArrayList<>();
        for (int day = closestMondayDateTime.getDayOfWeek(); day <=7; day++) {
            list.add(service.getTextWeatherMapping(closestMondayDateTime.plusDays(day-1)));
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
    public List<DetailedForecastGraphMapping> getDetailedForecastMapping(){
        return new WeatherService().getDetailedForecastMapping();
    }
    @RequestMapping("/get_weekly_weather")
    @ResponseBody
    public HashMap<Integer, HashMap<String,WeeklyWeatherReportMapping>>  getWeeklyWeather(){
        WeatherService service = new WeatherService();
        return service.getWeeklyWeatherReport();
    }

    @RequestMapping("/get_five_years_average")
    @ResponseBody
    public List getFiveYearsAverage(){
        return new WeatherService().getFiveYearsAverage();
    }

    @RequestMapping("/get_weekly_ultraviolet_index")
    @ResponseBody
    public List getWeeklyUltravioletIndex(){
        WeatherService service = new WeatherService();
        return service.getWeeklyUltraviolet();
    }
}
