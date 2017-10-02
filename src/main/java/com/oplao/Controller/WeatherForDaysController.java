package com.oplao.Controller;


import com.oplao.service.SearchService;
import com.oplao.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class WeatherForDaysController {


    @Autowired
    WeatherService weatherService;
    @Autowired
    SearchService searchService;


    @RequestMapping("/get_table_data_for_days")
    @ResponseBody
    public List getDynamicTableData(@CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,
                                    HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "date", required = false) String date, @RequestParam("numOfHours") int numOfHours,
                                    @RequestParam("numOfDays") int numOfDays, @RequestParam("pastWeather") boolean pastWeather){

        return weatherService.getTableDataForDays(searchService.findSelectedCity(request, response, currentCookieValue), numOfHours, numOfDays, pastWeather, date);
    }

    @RequestMapping("/get_not_universal_table_data")
    @ResponseBody

    public HashMap<Integer, HashMap<String,HashMap>>  getWeeklyWeather(@RequestParam(value="numOfDays") int numOfDays, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, HttpServletRequest request, HttpServletResponse response){

        return weatherService.getWeeklyWeatherReport(searchService.findSelectedCity(request, response, currentCookieValue), numOfDays);
    }

}
