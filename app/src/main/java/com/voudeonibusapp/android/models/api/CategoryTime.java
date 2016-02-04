package com.voudeonibusapp.android.models.api;

import java.util.Date;

import io.realm.RealmObject;

public class CategoryTime extends RealmObject {

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
