package com.oplao.model;

import com.oplao.service.WeatherService;
import org.joda.time.DateTime;

public class OutlookWeatherMapping {

    private String country;
    private String city;
    private String month;
    private int day;
    private String dayOfWeek;
    private int hours;
    private int minutes;
    private int temp_c;
    private int temp_f;
    private int feelsLikeC;
    private int feelsLikeF;
    private double precipMM;
    private int windspeedMiles;
    private int windspeedKmph;
    private int windGustMiles;
    private int windGustKmph;
    private int pressure;
    private String weatherIconCode;

    public static OutlookWeatherMapping create(String country, String city,
                                               DateTime dateTime, int temp_c,
                                               int temp_f, int feelsLikeC,
                                               int feelsLikeF, double precipMM,
                                               int windspeedMiles, int windSpeedKmph,
                                               int windGustMiles, int windGustKmph,
                                               int pressure, String weatherIconCode) {

        return new OutlookWeatherMapping(
                country,
                city,
                WeatherService.convertMonthOfYear(dateTime.getMonthOfYear()),
                dateTime.getDayOfMonth(),
                WeatherService.convertDayOfWeek(dateTime.getDayOfWeek()),
                dateTime.getHourOfDay(),
                dateTime.getMinuteOfHour(),
                temp_c, temp_f, feelsLikeC,
                feelsLikeF,
                precipMM, windspeedMiles, windSpeedKmph,
                windGustMiles, windGustKmph,
                pressure, weatherIconCode
        );
    }





    private OutlookWeatherMapping(String country, String city, String month, int day,
                                 String dayOfWeek, int hours, int minutes, int temp_c,
                                 int temp_f, int feelsLikeC, int feelsLikeF, double precipMM,
                                 int windspeedMiles, int windspeedKmph, int windGustMiles, int windGustKmph,
                                 int pressure, String weatherIconCode) {
        this.country = country;
        this.city = city;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.hours = hours;
        this.minutes = minutes;
        this.temp_c = temp_c;
        this.temp_f = temp_f;
        this.feelsLikeC = feelsLikeC;
        this.feelsLikeF = feelsLikeF;
        this.precipMM = precipMM;
        this.windspeedMiles = windspeedMiles;
        this.windspeedKmph = windspeedKmph;
        this.windGustMiles = windGustMiles;
        this.windGustKmph = windGustKmph;
        this.pressure = pressure;
        this.weatherIconCode = weatherIconCode;
    }


    public String getWeatherIconCode() {
        return weatherIconCode;
    }

    public void setWeatherIconCode(String weatherIconCode) {
        this.weatherIconCode = weatherIconCode;
    }

    public int getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(int temp_c) {
        this.temp_c = temp_c;
    }

    public int getTemp_f() {
        return temp_f;
    }

    public void setTemp_f(int temp_f) {
        this.temp_f = temp_f;
    }

    public int getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(int feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public int getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(int feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public double getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(double precipMM) {
        this.precipMM = precipMM;
    }

    public int getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(int windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public int getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(int windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public int getWindGustMiles() {
        return windGustMiles;
    }

    public void setWindGustMiles(int windGustMiles) {
        this.windGustMiles = windGustMiles;
    }

    public int getWindGustKmph() {
        return windGustKmph;
    }

    public void setWindGustKmph(int windGustKmph) {
        this.windGustKmph = windGustKmph;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
