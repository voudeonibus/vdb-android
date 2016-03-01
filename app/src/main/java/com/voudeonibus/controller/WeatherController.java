package com.voudeonibus.controller;

import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.models.aux.Weather;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

public class WeatherController {

    public static void update(Weather weather) {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Weather> weathers = realm.where(Weather.class).findAll();

        realm.beginTransaction();
        weathers.clear();

        realm.copyToRealm(weather);

        realm.commitTransaction();

        EventBus.getDefault().post(MessageEvent.UPDATE_WEATHER);


    }

    public static Weather get() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Weather> result = realm.where(Weather.class).findAll();

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }

    }

}
