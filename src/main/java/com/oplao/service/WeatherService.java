package com.oplao.service;

import com.oplao.Utils.DateConstants;
import com.oplao.model.GeoLocation;
import com.oplao.model.OutlookWeatherMapping;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherService {


    public static String convertDayOfWeek(int day) {


        switch (day) {
            case DateTimeConstants.MONDAY
                    : return "Monday";
            case DateTimeConstants.TUESDAY
                    : return "Tuesday";

            case DateTimeConstants.WEDNESDAY
                    : return "Wednesday";

            case DateTimeConstants.THURSDAY
                    : return "Thursday";

            case DateTimeConstants.FRIDAY
                    : return "Friday";

            case DateTimeConstants.SATURDAY
                    : return "Saturday";

            case DateTimeConstants.SUNDAY
                    : return "Sunday";
            default:return "wrong value for field 'day of week' ";

        }
    }
    public static String convertMonthOfYear(int month) {


        switch (month) {
            case DateConstants.JANUARY
                    :
                return "January";

            case DateConstants.FEBRUARY
                    :
                return "February";
            case DateConstants.MARCH
                    :
                return "March";
            case DateConstants.APRIL
                    :
                return "April";
            case DateConstants.MAY
                    :
                return "May";
            case DateConstants.JUNE
                    :
                return "June";
            case DateConstants.JULY
                    :
                return "July";
            case DateConstants.AUGUST
                    :
                return "August";
            case DateConstants.SEPTEMBER
                    :
                return "September";
            case DateConstants.OCTOBER
                    :
                return "October";
            case DateConstants.NOVEMBER
                    :
                return "November";
            case DateConstants.DECEMBER
                    :
                return "December";
            default:
                return "Wrong value for field 'month'";
        }
    }


        public OutlookWeatherMapping getRemoteData(){
        GeoLocation geoLocation = GeoIPv4.getLocation("94.126.240.2");
            URL url = null;
            DateTime dateTime = new DateTime();

            try {
                url = new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=gwad8rsbfr57wcbvwghcps26&format=json&show_comments=no&mca=no&cc=yes&tp=6&date="+dateTime.getYear()+"-" + dateTime.getMonthOfYear() + "-" +dateTime.getDayOfMonth() + "&q=" + geoLocation.getCity());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection con = null;
            String body = "";
            try {
                con = url.openConnection();
                InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                body = IOUtils.toString(in, encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }

        JSONObject jsonObject = new JSONObject(body);
        HashMap map = (HashMap)jsonObject.toMap().get("data");
        ArrayList<HashMap> currentCondition = (ArrayList<HashMap>)map.get("current_condition");
        ArrayList<HashMap> weather = (ArrayList<HashMap>)map.get("weather");
        HashMap weatherData = weather.get(0);
        ArrayList<HashMap> hourly = (ArrayList<HashMap>)weatherData.get("hourly");
        HashMap hourlyHm = hourly.get(0);
        HashMap currentConditions = currentCondition.get(0);

            return
                    OutlookWeatherMapping.create(geoLocation.getCountryName(), geoLocation.getCity(), dateTime,
                            Integer.parseInt(String.valueOf(currentConditions.get("temp_C"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("temp_F"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("FeelsLikeC"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("FeelsLikeF"))),
                            Double.parseDouble(String.valueOf(currentConditions.get("precipMM"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("windspeedMiles"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("windspeedKmph"))),
                            Integer.parseInt(String.valueOf(hourlyHm.get("WindGustMiles"))),
                            Integer.parseInt(String.valueOf(hourlyHm.get("WindGustKmph"))),
                            Integer.parseInt(String.valueOf(currentConditions.get("pressure"))));

    }
}
