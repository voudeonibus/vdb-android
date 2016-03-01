package com.voudeonibus.models.api;

import io.realm.RealmObject;

public class DetailNotification extends RealmObject {

    private int type;

    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
