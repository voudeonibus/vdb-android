package com.voudeonibus.controller;

import com.voudeonibus.models.api.Departure;
import com.voudeonibus.models.api.Direction;
import com.voudeonibus.models.api.Line;
import com.voudeonibus.models.api.Schedule;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.models.aux.VouAgora;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TripController {

    public static Trip findById(String _id) {
        final Realm realm = Realm.getDefaultInstance();

        Trip trip = realm.where(Trip.class).equalTo("_id", _id).findFirst();
        return trip;
    }

    public static RealmResults<Trip> getOnlyDay(int day, RealmList<Trip> trips) {
        return trips.where().equalTo("directions.departures.category_day", day).findAll();
    }

    public static RealmList<Trip> breakTripInSchedule(RealmResults<Trip> trips, VouAgora vouAgora) {
        RealmList<Trip> tripsList = new RealmList<>();
        tripsList.addAll(trips);
        return breakTripInSchedule(trips, vouAgora);
    }

    public static RealmList<Trip> breakTripInSchedule(RealmList<Trip> trips, VouAgora vouAgora) {
        RealmList<Trip> tripsList = new RealmList<>();

        for (Trip trip : trips) {

            for (Direction direction : trip.getDirections()) {

                for (Departure departure : direction.getDepartures()) {

                    for (Schedule schedule : departure.getSchedules()) {

                        Calendar cal = Calendar.getInstance();
                        int hours = cal.get(Calendar.HOUR_OF_DAY);
                        int minutes = cal.get(Calendar.MINUTE);

                        int hourSchedule = Integer.valueOf(schedule.getTime().split(":")[0]);
                        int minuteSchedule = Integer.valueOf(schedule.getTime().split(":")[1]);

//                        if (vouAgora.isOnlyCurrentHours()) {
                            if (hourSchedule > hours || (hourSchedule == hours && minuteSchedule >= minutes)) {
                                Trip newTrip = copy(trip);
                                newTrip.setSchedule(schedule);
                                newTrip.setDirection(direction.getDirection());
                                newTrip.setVariations(direction.getVariations());
                                newTrip.setCategoryDays(departure.getCategory_day());
                                tripsList.add(newTrip);

                            }
//                        } else {
//                            Trip newTrip = copy(trip);
//                            newTrip.setSchedule(schedule);
//                            newTrip.setDirection(direction.getDirection());
//                            newTrip.setVariations(direction.getVariations());
//                            newTrip.setCategoryDays(departure.getCategory_day());
//                            tripsList.add(newTrip);
//
//                        }
                    }
                }
            }
        }

        //Sorting
        Collections.sort(tripsList, new Comparator<Trip>() {
            @Override
            public int compare(Trip trip, Trip trip2) {

                String[] hours1 = trip.getSchedule().getTime().split(":");
                String[] hours2 = trip2.getSchedule().getTime().split(":");

                int hour1 = Integer.valueOf(hours1[0]);
                int minute1 = Integer.valueOf(hours1[1]);
                int hour2 = Integer.valueOf(hours2[0]);
                int minute2 = Integer.valueOf(hours2[1]);

                if (hour1 > hour2) {
                    return 1;
                } else if (hour1 == hour2 ) {

                    if (minute1 > minute2) {
                        return 1;
                    } else {
                        return -1;
                    }

                } else if (hour1 < hour2) {
                    return -1;
                } else {
                    return -1;
                }

            }
        });

        return tripsList;
    }

    public static RealmList<Trip> getCardGroupLine(RealmList<Line> lines, VouAgora vouAgora) {
        RealmList<Trip> tripsMerge = new RealmList<>();

        for (Line line : lines) {
            RealmResults<Trip> tripsToday = line.getTrips().where().equalTo("directions.departures.category_day", VouAgoraController.getCategoryDayToday()).findAll();
            tripsMerge.addAll(tripsToday);
        }

//        for (Integer category_day : VouAgoraController.getCategoryDays()) {
//            RealmResults<Trip> tripsNextDay = trips.where().equalTo("directions.departures.category_day", category_day).findAll();
//            tripsMerge.addAll(tripsNextDay);
//        }

        return TripController.breakTripInSchedule(tripsMerge, vouAgora);

    }

    public static RealmList<Trip> getCardGroupLine(VouAgora vouAgora) {
        return getCardGroupLine(vouAgora.getCategory().getLines(), vouAgora);
    }

    public static Trip copy(Trip tripC) {
        Trip trip = new Trip();
        trip.set_id(tripC.get_id());
        trip.setDirection(tripC.getDirection());
        trip.setOrigin(tripC.getOrigin());
        trip.setDestination(tripC.getDestination());
        trip.setVariations(tripC.getVariations());
        return trip;
    }
}
