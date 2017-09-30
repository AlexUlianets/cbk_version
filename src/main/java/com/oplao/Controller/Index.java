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
                "/tomorrow"
        })
        public String index() {
            return "forward:/index.html";
        }
    }
}
