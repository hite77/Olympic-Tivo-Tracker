package com.hiteware.olympics;

/**
 * Created on 5/22/16.
 */
public class Averager {
    public float calculate(String time, String capacity) {
        int firstColon = time.indexOf(":");
        int secondColon = time.indexOf(":",firstColon+1);
        float hrs = Float.valueOf(time.substring(0,firstColon));
        float minutes = Float.valueOf(time.substring(firstColon+1,secondColon));
        float seconds = Float.valueOf(time.substring(secondColon+1));
        float timeFloat = (float) (hrs + minutes/60.0 + seconds/(60.0*60.0));

        float gb;
        if (capacity.contains("MB")) {
            gb = (float) (Float.valueOf(capacity.replace("MB",""))/1024.0);
        }
        else {
            gb =  Float.valueOf(capacity.replace("GB",""));
        }
        return gb/timeFloat;
    }
}
