package com.oplao.Controller;


import com.oplao.service.SearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
public class SearchController {

    @Autowired
    SearchService searchService;
    @RequestMapping(value = "find_occurences/{searchRequest:.+}", method = RequestMethod.POST)
    @ResponseBody
    public List<HashMap> onChange(@PathVariable("searchRequest") String searchRequest) {
        List list = null;
        if((Character.isDigit(searchRequest.trim().charAt(0)) || Character.isDigit(searchRequest.trim().charAt(1))) && searchRequest.contains(",")){
            String [] parsedRequest = searchRequest.split(",");
            String lat = "";
            String lon = "";
            if(parsedRequest.length==4){
                lat= parsedRequest[0].trim() + "." + parsedRequest[1].trim();
                lon = parsedRequest[2].trim() +"." +parsedRequest[3].trim();
            }else {
                lat = parsedRequest[0].replace(",", ".").trim();
                try {
                    lon = parsedRequest[1].replaceAll(",", ".").trim();
                }catch (ArrayIndexOutOfBoundsException e){
                }
            }

            if(lat.contains(".")|| lat.contains(",") && lon.contains(".") || lon.contains(",")) {
                list = searchService.findByCoordinates(lat, lon);
            }else {
                 return null;
            }
        }else if(searchRequest.length()==3 && Objects.equals(searchRequest, searchRequest.toUpperCase())) {
               list = searchService.findByAirports(searchRequest);
        }
        else {
               list = searchService.findByCity(searchRequest);
        }
        List<HashMap> maps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            maps.add((HashMap) ((JSONObject) list.get(i)).toMap());
        }
        return maps;
    }

    private int checkDuplicateCookie(HttpServletRequest request, HttpServletResponse response,
                                            JSONObject city){
        for (int i = 0; i < request.getCookies().length; i++) {
            if(request.getCookies()[i].getName().equals(SearchService.cookieName)){
                JSONArray array = new JSONArray(request.getCookies()[i].getValue());
                for (int j = 0; j < array.length(); j++) {
                    if(Objects.equals("" + (array.getJSONObject(j)).get("geonameId"),
                            "" + city.get("geonameId"))){
                        setCitySelected(array, j);
                        searchService.clearCookies(request, response);

                        Cookie c = new Cookie(SearchService.cookieName, array.toString());
                        c.setMaxAge(60 * 60 * 24);
                        c.setPath("/");
                        response.addCookie(c);

                        return 0;
                    }
                }
            }
        }
        return 1;
    }

    private void setCitySelected(JSONArray array, int index){

        for (int i = 0; i < array.length(); i++) {
            array.getJSONObject(i).put("status", "unselected");
        }

        array.getJSONObject(index).put("status", "selected");
    }
    @RequestMapping(value = "/select_city/{geonameId}", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus selectCity(@PathVariable("geonameId") int geonameId, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, HttpServletRequest request,
                               HttpServletResponse response) {
        List<JSONObject> list = null;
        JSONObject city = null;
        try{
            try {
                list =searchService.findByGeonameId(geonameId);
                city = list.get(0);
            }catch (Exception e){
                list = searchService.findByGeonameIdAirports(geonameId);
                city = list.get(0);
            }

            city.put("status", "selected");
            JSONArray arr = new JSONArray(currentCookieValue);
            for (int i = 0; i < arr.length(); i++) {
                arr.getJSONObject(i).put("status", "unselected");
            }

            if(checkDuplicateCookie(request, response,city) != 0) {
                if(arr.length()>4){
                    arr.remove(4);
                    arr.put(4, city);
                }else{
                    arr.put(city);
                }
                searchService.clearCookies(request, response);

                Cookie c = new Cookie(SearchService.cookieName, arr.toString());
                c.setMaxAge(60 * 60 * 24);
                c.setPath("/");
                response.addCookie(c);

                return HttpStatus.OK;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return HttpStatus.OK;
    }

}
