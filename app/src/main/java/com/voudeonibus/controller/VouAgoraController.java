package com.voudeonibus.controller;

import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.models.api.Category;
import com.voudeonibus.models.api.CategoryDays;
import com.voudeonibus.models.api.Departure;
import com.voudeonibus.models.api.Direction;
import com.voudeonibus.models.api.Line;
import com.voudeonibus.models.api.Schedule;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.models.aux.Hours;
import com.voudeonibus.models.aux.VouAgora;

import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class VouAgoraController {

    private static ArrayList<Integer> CATEGORY_DAYS = new ArrayList<>();

    public static ArrayList<Integer> getCategoryDays() {
     return CATEGORY_DAYS;
    }

    public static void analysisCards(Category category, boolean emitPost) {
        removeCategory(category);
        addCategory(category);

        if (emitPost) {
            EventBus.getDefault().post(MessageEvent.UPDATE_VOU_AGORA);
        }
    }

    public static void analysisCards(Category category) {
        analysisCards(category, true);
    }

    public static void analysisCards() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> categories = realm.where(Category.class)
                .findAll();

        for (Category category : categories) {
            analysisCards(category, false);
        }

        EventBus.getDefault().post(MessageEvent.UPDATE_VOU_AGORA);
    }

    private static void removeCategory(Category category) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<VouAgora> resultsCategoryRemove = realm
                .where(VouAgora.class)
                .equalTo("category.idLocal", category.getIdLocal())
                .findAll();

        realm.beginTransaction();
        resultsCategoryRemove.clear();
        realm.commitTransaction();
    }

    private static void addCategory(Category category) {

        int auxDay = 10;

        if (category.getDays().size() > 0 && category.getHours().size() > 0 && category.getLines().size() > 0) {

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            for (CategoryDays categoryDays : category.getDays()) {

                VouAgora vouAgora = realm.createObject(VouAgora.class);

                if (categoryDays.getDay() != auxDay) {

                    for (Hours hours : category.getHours()) {


                        vouAgora.setCategory(category);
                        vouAgora.setCategoryDays(categoryDays);
                        vouAgora.setHours(hours);

                        RealmList<Trip> tripsMerge = new RealmList<>();

                        for (Line line : category.getLines()) {
                            RealmResults<Trip> tripsToday = line.getTrips().where().equalTo("directions.departures.category_day", categoryDays.getDay()).findAll();
                            RealmList<Trip> tripsFilter = new RealmList<>();

                            for (Trip trip : tripsToday) {

                                for (Direction direction : trip.getDirections()) {

                                    for (Departure departure : direction.getDepartures()) {

                                        for (Schedule schedule : departure.getSchedules()) {

                                            int hourSchedule = Integer.valueOf(schedule.getTime().split(":")[0]);
                                            int minuteSchedule = Integer.valueOf(schedule.getTime().split(":")[1]);


                                            if ((((hourSchedule < vouAgora.getHours().getHoursEnd()) || (hourSchedule == vouAgora.getHours().getHoursEnd() && minuteSchedule <= vouAgora.getHours().getMinuteEnd())) && vouAgora.getHours().getHoursEnd() <= 23)) {
                                                Trip newTrip = TripController.copy(trip);
                                                newTrip.setSchedule(schedule);
                                                newTrip.setDirection(direction.getDirection());
                                                newTrip.setVariations(direction.getVariations());
                                                newTrip.setCategoryDays(departure.getCategory_day());
                                                tripsFilter.add(newTrip);
                                            }
                                        }
                                    }
                                }
                            }

                            tripsMerge.addAll(tripsToday);
                        }

                        vouAgora.setTrips(tripsMerge);
                    }

                    auxDay = categoryDays.getDay();
                }
            }

            realm.commitTransaction();

        }

    }

    private static RealmResults<VouAgora> getVouAgora(int categoryDay) {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<VouAgora> query = realm.where(VouAgora.class).equalTo("categoryDays.day", categoryDay);

        RealmResults<VouAgora> resultsVouAgora = query.findAll();

        return resultsVouAgora;
    }

    private static RealmResults<VouAgora> getVouAgoraToday() {
        CATEGORY_DAYS.add(getCategoryDayToday());
        RealmResults<VouAgora> results = getVouAgora(getCategoryDayToday());


        for (VouAgora agora : results) {
            agora.setOnlyCurrentHours(true);
        }

        return results;
    }

    private static RealmResults<VouAgora> getVouAgoraWeek() {
        CATEGORY_DAYS.add(0);
        return getVouAgora(0);
    }

    private static RealmResults<VouAgora> getVouAgoraSaturday() {
        CATEGORY_DAYS.add(1);
        return getVouAgora(1);
    }

    private static RealmResults<VouAgora> getVouAgoraSundayHoliday() {
        CATEGORY_DAYS.add(2);
        return getVouAgora(2);
    }

    public static RealmList<VouAgora> getAllVouAgora() {
        CATEGORY_DAYS.clear();
        RealmList<VouAgora> results = new RealmList<>();
        // results.addAll(getVouAgoraToday());
        results.addAll(getVouAgoraWeek());
        results.addAll(getVouAgoraSaturday());
        results.addAll(getVouAgoraSundayHoliday());
        return processSetFirstTitle(results);
    }

    private static int getDayToday() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        return day_of_week;
    }

    private static int getCategoryDay(int day) {
        int category_day = 0;
        switch (day) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                category_day = 0;
                break;
            case 1:
                category_day = 2;
                break;
            case 7:
                category_day = 1;
                break;
        }
        return category_day;
    }

    public static int getCategoryDayToday() {
        return getCategoryDay(getDayToday());
    }

    private  static int getCategoryDayNext(int day) {
        day += 1;
        if (day > 7 || day == 0) day = 1;
        return getCategoryDay(day);
    }

    public static int getCategoryDayNext() {
        return getCategoryDayNext(getDayToday());
    }

    public static boolean hasVouAgora() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(VouAgora.class).findAll().size() > 0;
    }

    private static RealmList<VouAgora> processSetFirstTitle(RealmList<VouAgora> listVouAgora) {

        Semana semana = new Semana();

        for (VouAgora vouAgora : listVouAgora) {

            /**
             * Aqui começa a lógica do negócio
             * Se o dia for 0 então ele é semana
             *
             * 0 = Dia da semana
             * 1 = Sabado
             * 2 = Domingo
             */

            vouAgora.setFirstDayTitle(false);

            switch (vouAgora.getCategoryDays().getDay()) {
                case 1 :
                    if (semana.isSaturday()) {
                        vouAgora.setFirstDayTitle(true);
                        semana.setSaturday(false);
                    }
                    break;

                case 2 :
                    if (semana.isSunday()) {
                        vouAgora.setFirstDayTitle(true);
                        semana.setSunday(false);
                    }
                    break;

                default:
                    if (semana.isWeek()) {
                        vouAgora.setFirstDayTitle(true);
                        semana.setWeek(false);
                    }
                    break;

            }
        }

        return listVouAgora;
    }


}

class Semana {
    private boolean week;
    private boolean saturday;
    private boolean sunday;

    public Semana() {
        this.setWeek(true);
        this.setSaturday(true);
        this.setSunday(true);
    }

    public void setWeek(boolean week) {
        this.week = week;
    }

    public boolean isWeek() {
        return this.week;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSaturday() {
        return this.saturday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isSunday() {
        return this.sunday;
    }

}
