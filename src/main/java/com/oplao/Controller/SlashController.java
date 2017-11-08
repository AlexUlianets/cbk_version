package com.oplao.Controller;


import com.oplao.service.LanguageService;
import com.oplao.service.SearchService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class SlashController {


    @Autowired
    SearchService searchService;
    @Autowired
    LanguageService languageService;

    @RequestMapping("/")
    public String main(HttpServletRequest request, HttpServletResponse response,
                       @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue,
                       @CookieValue(value = "langCookieCode", defaultValue = "") String languageCookieCode){

        JSONObject city = searchService.findSelectedCity(request, response, currentCookieValue); //is done to generate location before the page is loaded
        String reqUrl = request.getRequestURI();
        if(reqUrl.contains("forecast")){
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", reqUrl.replace("forecast", "weather"));
        }
        searchService.selectLanguage(reqUrl, request, response, languageCookieCode, city);
        return "forward:index.html";
    }


    @RequestMapping("/generate_language_content")
    @ResponseBody
    public HashMap generateLanguageContent(@RequestParam(value = "langCode") String langCode, @RequestParam(value = "path") String path, HttpServletRequest request, HttpServletResponse response, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue){

        return languageService.generateLanguageContent(langCode, path, searchService.findSelectedCity(request, response, currentCookieValue));
    }
}
