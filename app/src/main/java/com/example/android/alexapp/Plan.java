package com.example.android.alexapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amany on 4/5/2017.
 */

public class Plan implements Serializable {
    private String name;
    private int duration;
    private ArrayList<Place> placeId;
    private int planId;


    //the constractor
    public Plan(){}
    public Plan(String planName,int planDuration, ArrayList<Place> planPlaces,int planId){
        this.name = planName;
        this.duration = planDuration;
        this.placeId = planPlaces;
        this.planId = planId;

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getduration() {
        return duration;
    }

    public void setduration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Place> getplaces() {
        return placeId;
    }

    public void setplaces(ArrayList<Place> placeid) {
        this.placeId = placeid;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
