package com.oplao.Controller;


import com.oplao.Utils.LanguageUtil;
import com.oplao.service.LanguageService;
import com.oplao.service.SearchService;
import com.oplao.service.WeatherService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import static com.oplao.service.SearchService.validCountryCodes;

@Controller
public class SlashController {


    @Autowired
    SearchService searchService;
    @Autowired
    LanguageService languageService;
    @Autowired
    WeatherService weatherService;
    @RequestMapping("/")
    public ModelAndView slash(HttpServletRequest request, HttpServletResponse response,
                       @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,
                       @CookieValue(value = "langCookieCode", defaultValue = "") String languageCookieCode,
                              @CookieValue(value = "temp_val", defaultValue = "C") String typeTemp){

        JSONObject currentCity = searchService.findSelectedCity(request, response, currentCookieValue); //is done to generate location before the page is loaded

        if(languageCookieCode.equals("")) {
            String cc = "";
            try {
                cc = currentCity.getString("country_code");
            } catch (JSONException e) {
                cc = currentCity.getString("countryCode");
            }
            if (Arrays.asList(validCountryCodes).contains(cc.toLowerCase())) {
                languageCookieCode = cc.toLowerCase();
            }else{
                languageCookieCode = "en";
            }
        }

        Locale locale = new Locale(languageCookieCode, LanguageUtil.getCountryCode(languageCookieCode));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_" + languageCookieCode, locale);

        HashMap content = languageService.generateFrontPageContent(resourceBundle, currentCity.getString("name"), currentCity.getString("countryName"), languageCookieCode);

        List threeDaysTabs = weatherService.getTableDataForDays(currentCity, 1,3,false,null, languageCookieCode);
        List recentTabs = searchService.createRecentCitiesTabs(currentCookieValue, languageCookieCode);
        List countryWeather = searchService.getCountryWeather(currentCity, languageCookieCode);
        List holidayWeather = searchService.getHolidaysWeather(currentCity, languageCookieCode);
        List holidayDestinations = searchService.getTopHolidaysDestinations(23, languageCookieCode);

        ModelAndView modelAndView = new ModelAndView("front-page2");
        modelAndView.addObject("content", content);
        modelAndView.addObject("temperature", weatherService.getRemoteData(currentCity, languageCookieCode));
        modelAndView.addObject("pageName", "frontPage");
        modelAndView.addObject("typeTemp", typeTemp);
        modelAndView.addObject("threeDaysTabs", threeDaysTabs);
        modelAndView.addObject("recentTabs", recentTabs);
        modelAndView.addObject("countryWeather", countryWeather);
        modelAndView.addObject("holidayWeather", holidayWeather);
        modelAndView.addObject("holidayDestinations", holidayDestinations);
        modelAndView.addObject("currentCountryCode", languageCookieCode);
        modelAndView.addObject("selectedCity", currentCity.getString("name").toUpperCase() + "_" + currentCity.getString("countryCode").toUpperCase());
        return modelAndView;
    }

    @RequestMapping(value = "/generate_language_content", produces = "application/json")
    @ResponseBody
    public HashMap generateLanguageContent(@RequestParam(value = "langCode", required = false) String langCode, @RequestParam(value = "path") String path, HttpServletRequest request, HttpServletResponse response, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue){

        try {
            currentCookieValue = URLDecoder.decode(currentCookieValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return languageService.generateLanguageContent(langCode, path, searchService.findSelectedCity(request, response, currentCookieValue));
    }

    @RequestMapping("/generate_lang_code")
    @ResponseBody
    public String generateLangCode(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue){
        return  searchService.findSelectedCity(request, response, currentCookieValue).getString("countryCode").toLowerCase();
    }
}
