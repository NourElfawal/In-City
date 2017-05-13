package com.example.android.alexapp;

import java.io.Serializable;

/**
 * Created by amany on 4/5/2017.
 */

public class User implements Serializable {
    private Plan plan;
    private String userId;


    //the constractor
    public User(){}
    public User(Plan planName,  String userId){
        this.plan = planName;
        this.userId = userId;


    }

    public Plan getplan() {
        return plan;
    }

    public void setplan(Plan plan) {
        this.plan = plan;
    }

    public String  getid() {
        return userId;
    }

    public void setid(String userId) {
        this.userId = userId;
    }


}
