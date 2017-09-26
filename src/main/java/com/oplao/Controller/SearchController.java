package com.oplao.Controller;

import com.oplao.service.SearchService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public List<HashMap> findOccurences(@PathVariable("searchRequest") String searchRequest) {

        return searchService.findSearchOccurences(searchRequest);
      }

    @RequestMapping(value = "/select_city/{geonameId}", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus selectCity(@PathVariable("geonameId") int geonameId, @CookieValue(value = SearchService.cookieName, defaultValue = "") String currentCookieValue, HttpServletRequest request,
                               HttpServletResponse response) {
        return searchService.selectCity(geonameId, currentCookieValue, request, response);

    }

}
