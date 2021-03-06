package com.voudeonibus.app;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.voudeonibus.api.WeatherAPI;
import com.voudeonibus.controller.CategoryController;
import com.voudeonibus.controller.DatabaseController;
import com.voudeonibus.controller.LineController;
import com.voudeonibus.controller.VouAgoraController;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class VDBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);

        WeatherAPI.update();

        if (DatabaseController.isEmpty()) {
            LineController.insertAllLines(getApplicationContext());
            DatabaseController.saveDataSync();
            CategoryController.insertColorsDefault();
            CategoryController.insertCategoryDefault();
        }

        VouAgoraController.analysisCards();
        /**
         * Facebook init
         */
        FacebookSdk.sdkInitialize(getApplicationContext());
        JodaTimeAndroid.init(this);
    }

    public void handleUncaughtException (Thread thread, Throwable e) {
        e.printStackTrace();
        this.handleUncaughtException(thread, e);
    }


}
