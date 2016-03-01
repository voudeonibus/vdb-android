package com.voudeonibus.models.aux;

import io.realm.RealmObject;

public class Weather extends RealmObject {

    private String description;
    private double temp;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
