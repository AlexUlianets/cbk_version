package com.oplao.Controller;


import com.oplao.model.GeoLocation;
import com.oplao.model.OutlookWeatherMapping;
import com.oplao.service.WeatherService;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class OutlookHeaderWidgetController {

    @RequestMapping("/get_api_weather")
    @ResponseBody
    public OutlookWeatherMapping getApiWeather() throws Exception{


        WeatherService service = new WeatherService();


        return service.getRemoteData();
    }

//    @RequestMapping("/get_widget_header")
//    @ResponseBody
//    public OutlookWeatherMapping getWidgetHeader(HttpServletRequest request){
//
//    }

}
