package com.oplao.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Index {
    @RequestMapping("/a")
    public String get(){
        return "redirect:about.html";
    }
}
