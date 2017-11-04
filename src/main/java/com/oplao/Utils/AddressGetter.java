package com.oplao.Utils;

import com.oplao.Application;

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

        if(ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")||ip.equalsIgnoreCase("127.0.0.1")){
            Application.log.info("connected by ip " + ip);
            return "176.8.91.205";
        }
        else{
            Application.log.info("connected by ip " + ip);
            return ip;
        }

    }
    }
