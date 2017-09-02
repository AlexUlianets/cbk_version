package com.oplao.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class CityToTimeZoneConverter {

    public static String convert(String city){
        List<String> list = Arrays.asList(TimeZone.getAvailableIDs());

        return list.stream().filter(s -> s.contains(city)).findAny().get();
    }
}
