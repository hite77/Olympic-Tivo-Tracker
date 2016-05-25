package com.hiteware.olympics;

import java.util.ArrayList;

/**
 * Created on 5/22/16.
 */
public class Averager {
    ArrayList<Float>  averages = new ArrayList<>();
    ArrayList<String> channels = new ArrayList<>();

    public float calculate(String time, String capacity, String Channel) {
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
        float average = gb/timeFloat;
        int pos = channels.indexOf(Channel);
        if (pos != -1) {
            averages.set(pos, (float) ((average+averages.get(pos)) /2.0));
        }
        else {
            averages.add(average);
            channels.add(Channel);
        }
        return average;
    }

    public float retrieve(String Channel) {
        int pos = channels.indexOf(Channel);
        if (pos != -1) {
            return averages.get(pos);
        }

        Float sum = (float) 0.0;
        Float count = (float) 0.0;
        for (Float average : averages) {
            sum += average;
            count += (float) 1.0;
        }
        return sum / count;
    }
}
