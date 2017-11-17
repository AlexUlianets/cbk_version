package com.oplao.Utils;

public class LanguageUtil {

    public static String getCountryCode(String language){
        switch (language){
            case "en":
                return "EN";
            case "ru":
                return "RU";
            case "ua":
                return "UA";
            case "by":
                return "BY";
            case "fr":
                return "FR";
            case "it":
                return "IT";
            case "de":
                return "DE";
        }
        return "en";
    }

    public static String validateOldCountryCodes(String langCode) {

        if (langCode.equals("ua")) {
            return "uk";
        } else if (langCode.equals("by")) {
            return "be";
        } else {
            return langCode;
        }
    }
}
