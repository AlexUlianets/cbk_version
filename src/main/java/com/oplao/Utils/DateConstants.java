package com.oplao.Utils;

import org.joda.time.DateTimeConstants;

public class DateConstants {

    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;


    public static String convertTimeToAmPm(int time){

        if(time>=1200){
            time = time-1200;

            String timeStr = "" + time;

            if(timeStr.length()==3){
                return timeStr.substring(0,1) + " PM";
            }else if(timeStr.length()==4){
                return timeStr.substring(0,2) + " PM";
            }
            else if(timeStr.length()==1){
                return "12 AM";
            }
        }else {
            String timeStr = "" + time;

            if(timeStr.length()==3){
                return timeStr.substring(0,1) + " AM";
            }else if(timeStr.length()==4){
                return timeStr.substring(0,2) + " AM";
            }else if(timeStr.length()==1){
                return "12 PM";
            }
        }
        return null;
    }
    public static String convertDayOfWeekShort(int day) {
        switch (day) {
            case DateTimeConstants.MONDAY
                    :
                return "Mon";
            case DateTimeConstants.TUESDAY
                    :
                return "Tue";

            case DateTimeConstants.WEDNESDAY
                    :
                return "Wed";

            case DateTimeConstants.THURSDAY
                    :
                return "Thu";

            case DateTimeConstants.FRIDAY
                    :
                return "Fri";

            case DateTimeConstants.SATURDAY
                    :
                return "Sat";

            case DateTimeConstants.SUNDAY
                    :
                return "Sun";
            default:
                return "wrong value for field 'day of week' ";
        }
    }

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
}
