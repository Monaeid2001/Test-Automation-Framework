package com.automationexercise.utils;

public class TimeManager {
    //timestamp
    //simple data format
    //screenshots and logs and reports
    public static String getTimestamp(){

        return new java.text.SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new java.util.Date());
    }
    //getsimpletimestamp
    //unique timestamp for each data like id
    public static String getSimpleTimestamp(){

        return Long.toString(System.currentTimeMillis());
    }

}
