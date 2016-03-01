package com.voudeonibus.controller;

import android.util.Log;

public class NotificationController {

    public static int countNotification() {
        return 0;
        // Realm realm = Realm.getDefaultInstance();
        // return realm.where(Notification.class).findAll().size();
    }

    public static boolean removeAll() {
        Log.d("Notification", "removeAll");
        return true;
    }
}
