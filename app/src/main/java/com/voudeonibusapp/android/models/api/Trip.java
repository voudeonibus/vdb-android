package com.voudeonibusapp.android.models.api;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Trip extends RealmObject implements Serializable {

    private String _id;
    private String origin;
    private String destination;
    private RealmList<Direction> directions;

    @Ignore
    private Schedule schedule;

    @Ignore
    private int categoryDays;

    @Ignore
    private int direction;

    @Ignore
    private RealmList<Variation> variations;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public RealmList<Direction> getDirections() {
        return directions;
    }

    public void setDirections(RealmList<Direction> directions) {
        this.directions = directions;
    }

    public RealmList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(RealmList<Variation> variations) {
        this.variations = variations;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getCategoryDays() {
        return categoryDays;
    }

    public void setCategoryDays(int categoryDays) {
        this.categoryDays = categoryDays;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
