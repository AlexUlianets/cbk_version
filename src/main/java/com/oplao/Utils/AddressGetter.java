package com.oplao.Utils;

import javax.servlet.http.HttpServletRequest;

public class AddressGetter {

    public static String getCurrentIpAddress(HttpServletRequest request){


        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
            return "94.126.240.2";
        }
        else{
            return ip;
        }

    }
    }
