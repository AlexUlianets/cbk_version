package com.oplao.Controller;


import com.oplao.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SlashController {


    @Autowired
    SearchService searchService;

    @RequestMapping("/")
    public String main(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue){

        searchService.findSelectedCity(request, response, currentCookieValue); //is done to generate location before the page is loaded
        return "forward:index.html";
    }

}
