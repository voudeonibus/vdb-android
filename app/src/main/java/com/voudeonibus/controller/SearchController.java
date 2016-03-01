package com.voudeonibus.controller;

import com.voudeonibus.models.api.Line;
import com.voudeonibus.models.api.Trip;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SearchController {

    public static RealmResults<Line> findByRouteName(String name) {
        final Realm realm = Realm.getDefaultInstance();

        return realm.where(Line.class).contains("route_long_name", name, false).findAll();
    }

    public static RealmResults<Trip> findTripsByLine(Line line, String name) {
        final  Realm realm = Realm.getDefaultInstance();

        if (name == null || name.trim() == "") {
            return realm.where(Line.class)
                    .equalTo("_id", line.get_id())
                    .findAll().get(0).getTrips().where().findAll();
        }

        return realm.where(Line.class)
                .equalTo("_id", line.get_id())
                .findAll()
                .get(0)
                    .getTrips()
                    .where()
                    .contains("origin", name, false)
                    .or()
                    .contains("destination", name, false)
                    .findAll();

    }

    public static RealmResults<Trip> filterTrip(RealmList<Trip> trips, String name) {
        return trips.where()
                .contains("origin", name, false)
                .or()
                .contains("destination", name, false)
                .findAll();
    }

    public static RealmResults<Trip> filterTrip(RealmResults<Trip> trips, String name) {
        return trips.where()
                .contains("origin", name, false)
                .or()
                .contains("destination", name, false)
                .findAll();
    }

}
