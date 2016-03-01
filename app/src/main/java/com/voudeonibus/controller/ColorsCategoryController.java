package com.voudeonibus.controller;

import com.voudeonibus.models.aux.ColorsCategory;

import io.realm.Realm;

public class ColorsCategoryController {

    public static ColorsCategory findByColorInt(int color) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ColorsCategory.class)
                .equalTo("colorInt", color)
                .findFirst();
    }

    public static ColorsCategory findByColor(String color) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ColorsCategory.class)
                .equalTo("color", color)
                .findFirst();
    }

    public static ColorsCategory findByColor(int color) {
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        return findByColor(hexColor);
    }

    public static ColorsCategory findFirstColorsNotUsed() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ColorsCategory.class)
                .equalTo("used", false)
                .findFirst();
    }

}
