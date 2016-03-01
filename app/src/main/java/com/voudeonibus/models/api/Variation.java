package com.voudeonibus.models.api;

import io.realm.RealmObject;

public class Variation extends RealmObject{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
