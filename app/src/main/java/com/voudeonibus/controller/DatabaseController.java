package com.voudeonibus.controller;

import com.voudeonibus.models.conf.Database;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseController {

    public static boolean isEmpty() {
        final Realm realm = Realm.getDefaultInstance();

        return realm.where(Database.class).findAll().isEmpty();
    }

    public static void saveDataSync() {
        final Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Database newDate = realm.createObject(Database.class);
        newDate.setLast(new Date());
        realm.commitTransaction();
    }

    public static void saveShowTutorial() {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Database> lists = realm.where(Database.class).findAll();
        realm.beginTransaction();

        if (lists.size() > 0) {
            Database d = lists.get(0);
            d.setShowTutorial(true);
        } else {
            Database d = new Database();
            d.setShowTutorial(true);
            d.setLast(new Date());

        }

        realm.commitTransaction();

    }

    public static boolean isShowTutorial() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Database> lists = realm.where(Database.class).equalTo("showTutorial", true).findAll();

        return (lists.size() > 0);
    }

}
