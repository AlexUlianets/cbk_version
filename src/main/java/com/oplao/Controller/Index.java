package com.oplao.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Index {
    @Controller
    static class Routes {

        @RequestMapping({
                "/today",
                "/outlook",
                "/tomorrow",
                "/past-weather",
                "/three-days",
                "/five-days",
                "/seven-days",
                "/ten-days",
                "/fourteen-days",
                "/hour-by-hour"
        })
        public String index() {
            return "forward:/index.html";
        }

        @RequestMapping({"/", "/weather"})
        public String mainPage() {
            return "forward:/templates/front-page.html";
        }
    }
}
