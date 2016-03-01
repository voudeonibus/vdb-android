package com.voudeonibus.models.api;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Direction extends RealmObject {

    private int direction;
    private RealmList<Departure> departures;
    private RealmList<Variation> variations;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public RealmList<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(RealmList<Departure> departures) {
        this.departures = departures;
    }

    public RealmList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(RealmList<Variation> variations) {
        this.variations = variations;
    }

}
