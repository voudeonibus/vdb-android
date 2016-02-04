package com.voudeonibusapp.android.models.api;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Departure extends RealmObject {

    private int category_day;
    private RealmList<Schedule> schedules;

    public Departure() {
        this.category_day = 100;
    }

    public int getCategory_day() {
        return category_day;
    }

    public void setCategory_day(int category_day) {
        this.category_day = category_day;
    }

    public RealmList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(RealmList<Schedule> schedules) {
        this.schedules = schedules;
    }
}
