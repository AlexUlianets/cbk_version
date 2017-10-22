package com.oplao.Controller;


import com.oplao.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class Index {

    @Controller
    @RequestMapping("/en")
    static class Routes {

        @Autowired
        SearchService searchService;
        @RequestMapping({
                "/",
                "/weather",
                "/forecast/today",
                "/weather/outlook",
                "/forecast/tomorrow",
                "/weather/history1",
                "/weather/history3",
                "/forecast/3",
                "/forecast/5",
                "/forecast/7",
                "/forecast/10",
                "/forecast/14",
                "/forecast/hour-by-hour1",
                "/forecast/hour-by-hour3",
                "/about"
        })
        public String index() {
            return "forward:/index.html";
        }

        @RequestMapping({
                "/",
                "/weather/{locationRequest}",
                "/forecast/today/{locationRequest}",
                "/weather/outlook/{locationRequest}",
                "/forecast/tomorrow/{locationRequest}",
                "/weather/history1/{locationRequest}",
                "/weather/history3/{locationRequest}",
                "/forecast/3/{locationRequest}",
                "/forecast/5/{locationRequest}",
                "/forecast/7/{locationRequest}",
                "/forecast/10/{locationRequest}",
                "/forecast/14/{locationRequest}",
                "/forecast/hour-by-hour1/{locationRequest}",
                "/forecast/hour-by-hour3/{locationRequest}",
        })
        public String index(@PathVariable(value = "locationRequest") String locationRequest, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, HttpServletRequest request, HttpServletResponse response) {
            searchService.generateUrlRequestWeather(locationRequest, currentCookieValue, request, response);
            return "forward:/index.html";
        }
    }
}
