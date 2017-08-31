package com.oplao.model;

public class WeeklyWeatherSummaryMapping {

    int maxDayTempC;
    int maxDayTempF;
    int minDayTempC;
    int minDayTempF;
    int averageMinTempC;
    int averageMinTempF;
    int averageMaxTempC;
    int averageMaxTempF;
    double totalRainfall;
    double windiestMS;
    double windiestKmPh;


    public WeeklyWeatherSummaryMapping() {
    }

    public WeeklyWeatherSummaryMapping(int maxDayTempC, int maxDayTempF, int minDayTempC, int minDayTempF, int averageMinTempC, int averageMinTempF, int averageMaxTempC, int averageMaxTempF, double totalRainfall, double windiestMS, double windiestKmPh) {
        this.maxDayTempC = maxDayTempC;
        this.maxDayTempF = maxDayTempF;
        this.minDayTempC = minDayTempC;
        this.minDayTempF = minDayTempF;
        this.averageMinTempC = averageMinTempC;
        this.averageMinTempF = averageMinTempF;
        this.averageMaxTempC = averageMaxTempC;
        this.averageMaxTempF = averageMaxTempF;
        this.totalRainfall = totalRainfall;
        this.windiestMS = windiestMS;
        this.windiestKmPh = windiestKmPh;
    }

    public int getMaxDayTempC() {
        return maxDayTempC;
    }

    public void setMaxDayTempC(int maxDayTempC) {
        this.maxDayTempC = maxDayTempC;
    }

    public int getMaxDayTempF() {
        return maxDayTempF;
    }

    public void setMaxDayTempF(int maxDayTempF) {
        this.maxDayTempF = maxDayTempF;
    }

    public int getMinDayTempC() {
        return minDayTempC;
    }

    public void setMinDayTempC(int minDayTempC) {
        this.minDayTempC = minDayTempC;
    }

    public int getMinDayTempF() {
        return minDayTempF;
    }

    public void setMinDayTempF(int minDayTempF) {
        this.minDayTempF = minDayTempF;
    }

    public int getAverageMinTempC() {
        return averageMinTempC;
    }

    public void setAverageMinTempC(int averageMinTempC) {
        this.averageMinTempC = averageMinTempC;
    }

    public int getAverageMinTempF() {
        return averageMinTempF;
    }

    public void setAverageMinTempF(int averageMinTempF) {
        this.averageMinTempF = averageMinTempF;
    }

    public int getAverageMaxTempC() {
        return averageMaxTempC;
    }

    public void setAverageMaxTempC(int averageMaxTempC) {
        this.averageMaxTempC = averageMaxTempC;
    }

    public int getAverageMaxTempF() {
        return averageMaxTempF;
    }

    public void setAverageMaxTempF(int averageMaxTempF) {
        this.averageMaxTempF = averageMaxTempF;
    }

    public double getTotalRainfall() {
        return totalRainfall;
    }

    public void setTotalRainfall(double totalRainfall) {
        this.totalRainfall = totalRainfall;
    }

    public double getWindiestMS() {
        return windiestMS;
    }

    public void setWindiestMS(double windiestMS) {
        this.windiestMS = windiestMS;
    }

    public double getWindiestKmPh() {
        return windiestKmPh;
    }

    public void setWindiestKmPh(double windiestKmPh) {
        this.windiestKmPh = windiestKmPh;
    }
}
