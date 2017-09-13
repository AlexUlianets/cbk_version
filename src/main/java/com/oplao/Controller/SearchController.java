package com.oplao.Controller;


import com.oplao.service.SearchService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.oplao.service.SearchService.cookie;
import static com.oplao.service.SearchService.cookiedMaps;

@Controller
public class SearchController {

    @Autowired
    SearchService searchService;
    @RequestMapping(value = "/find_occurences/{searchRequest}", method = RequestMethod.POST)
    @ResponseBody
    public List<HashMap> onChange(@PathVariable("searchRequest") String searchRequest) {
        List list = null;
        try{
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=ru&max=10&nameStarts="+searchRequest);
        }catch (IOException e){
            e.printStackTrace();
        }
        List<HashMap> maps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            maps.add((HashMap) ((JSONObject) list.get(i)).toMap());
        }
        return maps;
    }


    private HttpStatus checkDuplicateCookie(HttpServletRequest request, HttpServletResponse response,
                                            HashMap city){
        for (int i = 0; i < SearchService.cookiedMaps.size(); i++) {
            if(Integer.parseInt(""+(cookiedMaps.get(i)).get("adminCode1")) == Integer.parseInt(""+ city.get("adminCode1"))){

                searchService.setCookieSelected(i);
                searchService.clearCookies(request, response);

                cookie.setValue(cookiedMaps.toString());
                response.addCookie(SearchService.cookie);
                return HttpStatus.OK;
            }
        }
        return null;
    }
    @RequestMapping(value = "/select_city/{geonameId}", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus selectCity(@PathVariable("geonameId") int geonameId, HttpServletRequest request,
                               HttpServletResponse response) {
        List list = null;
        try{
            list = SearchService.findByOccurences("https://bd.oplao.com/geoLocation/find.json?lang=ru&max=10&geonameId=" + geonameId);
        }catch (IOException e){
            e.printStackTrace();
        }
        List<HashMap> maps = new ArrayList<>();
        for (Object aList : list) {
            maps.add((HashMap) ((JSONObject) aList).toMap());
        }

        HashMap city = maps.get(0);
        if(checkDuplicateCookie(request, response, city) != null){
            return HttpStatus.OK;
        }


        for (int i = 0; i < SearchService.cookiedMaps.size(); i++) {
            (cookiedMaps.get(i)).remove("status");
            (cookiedMaps.get(i)).put("status", "unselected");
        }
        if(!city.containsKey("status")){
            city.put("status", "selected");
        }else{
            city.replace("status","selected");
        }

        JSONObject object = new JSONObject(city);
        if(SearchService.cookiedMaps.size()>5) {
            SearchService.cookiedMaps.remove(1);
            SearchService.cookiedMaps.add(5, object);
        }else{
            SearchService.cookiedMaps.add(object);
        }

        searchService.clearCookies(request, response);

        cookie.setValue(cookiedMaps.toString());
        response.addCookie(SearchService.cookie);
        return HttpStatus.OK;
    }

}
