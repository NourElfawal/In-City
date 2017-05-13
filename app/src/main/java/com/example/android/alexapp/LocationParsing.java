package com.example.android.alexapp;

/**
 * Created by amany on 3/29/2017.
 */

public class LocationParsing {

    static double latit;
    static double longit;


    public static double getLatit(String myLocation) {
        String[] parts = myLocation.split(",");
        latit = Double.parseDouble(parts[0].replace("lat/lng: (", ""));
        return latit;
    }

    public static double getLongit(String myLocation) {
        String[] parts = myLocation.split(",");
        longit = Double.parseDouble(parts[1].replace(")", ""));
        return longit;
    }
}
