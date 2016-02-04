package com.voudeonibusapp.android.controller;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.voudeonibusapp.android.enums.EColorsCategory;
import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.models.api.CategoryDays;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.models.aux.ColorsCategory;
import com.voudeonibusapp.android.models.aux.Hours;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CategoryController {

    public static final int LIMIT_CATEGORY = 7;

    public static void addCategory(Category category) {
        if (hasEmptyCategory()) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Category categoryNew = realm.copyToRealm(category);

            categoryNew.getColor().setUsed(true);
            realm.commitTransaction();

            VouAgoraController.analysisCards(categoryNew);
        }
    }

    public static void updateCategory(String name, ColorsCategory colorsCategory, Category category, RealmList<Line> lines, RealmList<Hours> hourses, RealmList<CategoryDays> dayses) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        category.setName(name);

        category.setLines(lines);

        RealmList<Hours> newHourses = new RealmList<>();

        category.getHours().clear();

        for (Hours hours : hourses) {
            Hours newHours = realm.copyToRealm(hours);
            newHourses.add(newHours);
        }

        category.setHours(newHourses);

        category.getDays().clear();

        RealmList<CategoryDays> newDayses = new RealmList<>();

        for (CategoryDays categoryDays : dayses) {
            CategoryDays newCategoryDays = realm.copyToRealm(categoryDays);
            newDayses.add(newCategoryDays);
        }

        category.getColor().setUsed(false);
        category.setColor(colorsCategory);

        category.setDays(newDayses);
        category.getColor().setUsed(true);

        realm.commitTransaction();

        VouAgoraController.analysisCards(category);

    }

    public static void updateCategory(ArrayList<Category> categories, Line line) {

        Realm realm = Realm.getDefaultInstance();

        for (Category category : categories) {


            Log.d("Ops", category.getName());
            Log.d("Ops", String.valueOf(category.isInsert()));

            boolean inserted = false;

            for (int i = 0; i < category.getLines().size(); i++) {
                Line t = category.getLines().get(i);

                if (t.get_id().equals(line.get_id())) {
                    if (!category.isInsert()) {
                        Log.d("Ops", "ops");

                        realm.beginTransaction();
                        category.getLines().remove(i);
                        realm.commitTransaction();
                        inserted = true;
                        break;
                    } else {
                        inserted = true;
                    }
                }
            }

            if (!inserted && category.isInsert()) {

                Log.d("Ops", "inseriu");

                realm.beginTransaction();
                category.getLines().add(line);
                realm.commitTransaction();
            }
        }
    }

    public static void removeTrip(Category category, Line line) {

        try {

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            for (int i = 0; i < category.getLines().size(); i++) {
                Line t = category.getLines().get(i);

                if (t.get_id().equals(line.get_id())) {
                    category.getLines().remove(i);
                }
            }

            realm.commitTransaction();

            VouAgoraController.analysisCards(category);

        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public static void removeCategory(Category category) {
        if (category.isValid()) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            category.removeFromRealm();
            realm.commitTransaction();

            VouAgoraController.analysisCards(category);
        }
    }

    public static void addTrip(Category category, Line line) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        category.getLines().add(line);
        realm.copyToRealmOrUpdate(category);
        realm.commitTransaction();
    }

    public static Category findCategoryById(int idLocal) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Category.class)
                .equalTo("idLocal", idLocal)
                .findFirst();
    }

    public static RealmResults<Category> all() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Category.class).findAll();
    }

    public static RealmResults<Category> findCategoryByTrip(Trip trip) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Category.class)
                .equalTo("trips._id", trip.get_id())
                .findAll();
    }

    public static RealmResults<Category> findOthersCategoryToTrip(Trip trip) {
        final  Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> results = realm.where(Category.class).findAll();
        return results;
    }

    public static boolean hasEmptyCategory() {
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(Category.class).count() < LIMIT_CATEGORY) {
            return true;
        } else {
            return false;
        }

    }

    public static void insertColorsDefault() {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        ColorsCategory colorGreen = realm.createObject(ColorsCategory.class);
        colorGreen.setColor("#7ED321");
        colorGreen.setUsed(true);
        colorGreen.setLevel(0);
        colorGreen.setNick(EColorsCategory.GREEN);

        ColorsCategory colorYellow = realm.createObject(ColorsCategory.class);
        colorYellow.setColor("#FFC107");
        colorYellow.setUsed(true);
        colorYellow.setLevel(1);
        colorYellow.setNick(EColorsCategory.YELLOW);

        ColorsCategory colorRed = realm.createObject(ColorsCategory.class);
        colorRed.setColor("#F44336");
        colorRed.setLevel(2);
        colorRed.setNick(EColorsCategory.RED);

        ColorsCategory colorGreenDark = realm.createObject(ColorsCategory.class);
        colorGreenDark.setColor("#009688");
        colorGreenDark.setLevel(3);
        colorGreenDark.setNick(EColorsCategory.GREEN_DARK);

        ColorsCategory colorOrange = realm.createObject(ColorsCategory.class);
        colorOrange.setColor("#FF5722");
        colorOrange.setLevel(4);
        colorOrange.setNick(EColorsCategory.ORANGE);

        ColorsCategory colorGray = realm.createObject(ColorsCategory.class);
        colorGray.setColor("#607D8B");
        colorGray.setLevel(5);
        colorGray.setNick(EColorsCategory.GRAY);

        ColorsCategory colorPupper = realm.createObject(ColorsCategory.class);
        colorPupper.setColor("#9C27B0");
        colorPupper.setLevel(6);
        colorPupper.setNick(EColorsCategory.PUPPER);

        realm.commitTransaction();


    }

    public static void insertCategoryDefault() {
        Realm realm = Realm.getDefaultInstance();

        Category categoryWork = new Category();
        categoryWork.setIdLocal(0);
        categoryWork.setIcon(0);
        categoryWork.setName("Trabalho");
        categoryWork.setColor(ColorsCategoryController.findByColor("#FFC107"));
        categoryWork.setIsDefault(true);

        realm.beginTransaction();
        realm.copyToRealm(categoryWork);
        realm.commitTransaction();

        Category categoryEducation = new Category();
        categoryEducation.setIdLocal(1);
        categoryEducation.setIcon(1);
        categoryEducation.setName("Estudo");
        categoryEducation.setColor(ColorsCategoryController.findByColor("#7ED321"));
        categoryEducation.setIsDefault(true);

        realm.beginTransaction();
        realm.copyToRealm(categoryEducation);
        realm.commitTransaction();
    }

    public static RealmResults<ColorsCategory> findColorsNotUsed() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ColorsCategory.class)
                .equalTo("used", false)
                .findAll();
    }

}
