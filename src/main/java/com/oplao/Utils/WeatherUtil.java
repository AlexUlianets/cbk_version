package com.oplao.Utils;

public class WeatherUtil {

    public static int convertFtoC(double tempF){

        float res = (float) (tempF-32/1.8);

        String str;
        str = String.format("%.0f", res);

        return Integer.parseInt(str);
    }

    public static int formatToInt(Object value){
        return Integer.parseInt(String.format("%.0f", value));
    }
}
