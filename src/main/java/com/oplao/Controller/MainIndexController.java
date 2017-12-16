package com.oplao.Controller;


import com.oplao.Utils.LanguageUtil;
import com.oplao.service.LanguageService;
import com.oplao.service.SearchService;
import com.oplao.service.SitemapService;
import com.oplao.service.WeatherService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


@Controller
public class MainIndexController {

    @Controller
    @RequestMapping("/{lang}")
    static class Routes {

        @Autowired
        SearchService searchService;
        @Autowired
        SitemapService sitemapService;
        @Autowired
        LanguageService languageService;
        @Autowired
        WeatherService weatherService;
//        @RequestMapping({
//                "/",
//                "/weather",
//                "/forecast",
//                "/forecast/today",
//                "/weather/today",
//                "/weather/outlook",
//                "/forecast/outlook",
//                "/forecast/tomorrow",
//                "/weather/tomorrow",
//                "/weather/history1",
//                "/forecast/history1",
//                "/weather/history3",
//                "/forecast/history3",
//                "/forecast/3",
//                "/weather/3",
//                "/forecast/5",
//                "/weather/5",
//                "/forecast/7",
//                "/weather/7",
//                "/forecast/10",
//                "/weather/10",
//                "/forecast/14",
//                "/weather/14",
//                "/forecast/hour-by-hour1",
//                "/weather/hour-by-hour1",
//                "/forecast/hour-by-hour3",
//                "/weather/hour-by-hour3",
//                "/weather/map",
//                "/about",
//                "/weather/widgets"
//        })
//        public String index(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, @CookieValue(value = SearchService.langCookieCode, defaultValue = "") String languageCookieCode) {
//            try {
//                sitemapService.addToSitemap(request.getRequestURI());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String reqUrl = request.getRequestURI();
//
//            if(reqUrl.contains("forecast")) {
//                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
//                response.setHeader("Location", reqUrl.replace("forecast", "weather"));
//            }
//            if(reqUrl.charAt(reqUrl.length()-1)!='/' && !reqUrl.contains("widgets")){
//                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
//                response.setHeader("Location", reqUrl+"/");
//            }
//            searchService.selectLanguage(reqUrl, request, response, languageCookieCode, searchService.findSelectedCity(request, response, currentCookieValue), currentCookieValue);
//            return "/WEB-INF/views/index.jsp";
//        }
//        @RequestMapping({
//                "/",
//                "/weather/{locationRequest:.+}",
//                "/forecast/{locationRequest:.+}",
//                "/forecast/today/{locationRequest:.+}",
//                "/weather/today/{locationRequest:.+}",
//                "/weather/outlook/{locationRequest:.+}",
//                "/forecast/outlook/{locationRequest:.+}",
//                "/weather/map/{locationRequest:.+}",
//                "/forecast/map/{locationRequest:.+}",
//                "/forecast/tomorrow/{locationRequest:.+}",
//                "/weather/tomorrow/{locationRequest:.+}",
//                "/weather/history1/{locationRequest:.+}",
//                "/weather/history3/{locationRequest:.+}",
//                "/forecast/3/{locationRequest:.+}",
//                "/weather/3/{locationRequest:.+}",
//                "/forecast/5/{locationRequest:.+}",
//                "/weather/5/{locationRequest:.+}",
//                "/forecast/7/{locationRequest:.+}",
//                "/weather/7/{locationRequest:.+}",
//                "/forecast/10/{locationRequest:.+}",
//                "/weather/10/{locationRequest:.+}",
//                "/forecast/14/{locationRequest:.+}",
//                "/weather/14/{locationRequest:.+}",
//                "/forecast/hour-by-hour1/{locationRequest:.+}",
//                "/weather/hour-by-hour1/{locationRequest:.+}",
//                "/forecast/hour-by-hour3/{locationRequest:.+}",
//                "/weather/hour-by-hour3/{locationRequest:.+}",
//        })
//        public String index(@PathVariable(value = "locationRequest") String locationRequest,
//                            @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,
//                            HttpServletRequest request, HttpServletResponse response,
//                            @CookieValue(value = "langCookieCode", defaultValue = "") String languageCookieCode) {
//            String reqUrl = request.getRequestURI();
//            try {
//                locationRequest = URLDecoder.decode(locationRequest, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            JSONObject generatedCity = searchService.generateUrlRequestWeather(locationRequest, currentCookieValue, request, response, reqUrl.split("/")[1]);
//            try {
//                sitemapService.addToSitemap(request.getRequestURI());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if(reqUrl.contains("forecast")){
//                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
//                response.setHeader("Location", reqUrl.replace("forecast", "weather"));
//            }
//            return "/WEB-INF/views/index.jsp";
//        }




        @RequestMapping("/weather/outlook/{locationRequest:.+}")
        public ModelAndView weatherOutlook(@PathVariable(value = "locationRequest") String locationRequest,
                            @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,
                            HttpServletRequest request, HttpServletResponse response,
                            @CookieValue(value = "langCookieCode", defaultValue = "") String languageCookieCode,
                            @CookieValue(value = "temp_val", defaultValue = "C") String typeTemp) {
            String reqUrl = request.getRequestURI();
            try {
                locationRequest = URLDecoder.decode(locationRequest, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSONObject generatedCity = searchService.generateUrlRequestWeather(locationRequest, currentCookieValue, request, response, reqUrl.split("/")[1]);

            if(reqUrl.contains("forecast")){
                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                response.setHeader("Location", reqUrl.replace("forecast", "weather"));
            }

            Locale locale = new Locale(languageCookieCode, LanguageUtil.getCountryCode(languageCookieCode));
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_" + languageCookieCode, locale);

            HashMap content = languageService.generateFrontPageContent(resourceBundle, generatedCity.getString("name"), generatedCity.getString("countryName"), languageCookieCode);
            List recentTabs = searchService.createRecentCitiesTabs(currentCookieValue, languageCookieCode);

            ModelAndView modelAndView = new ModelAndView("outlook2");


            modelAndView.addObject("currentCountryCode", languageCookieCode);
            modelAndView.addObject("selectedCity", generatedCity.getString("name").toUpperCase() + "_" + generatedCity.getString("countryCode").toUpperCase());
            modelAndView.addObject("content", content);
            modelAndView.addObject("typeTemp", typeTemp);
            modelAndView.addObject("temperature", weatherService.getRemoteData(generatedCity, languageCookieCode));
            modelAndView.addObject("pageName", "outlook");
            modelAndView.addObject("recentTabs", recentTabs);
            return modelAndView;
        }
    }
}
