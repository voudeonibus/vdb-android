package com.voudeonibusapp.android.controller;

import android.content.Context;
import android.widget.Toast;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.api.LineAPI;
import com.voudeonibusapp.android.aux.Callback;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

public class LineController {

    public static void syncDate(final Context context) {

        final Realm realm = Realm.getDefaultInstance();

        LineAPI.all(new Callback() {

            @Override
            public void exec(int statusCode, JSONObject response) {

            }

            @Override
            public void exec(int statusCode, JSONArray response) {

                try {

                    int lenght = response.length();

                    for (int i = 0; i < lenght; i++) {
                        JSONObject object = response.getJSONObject(i);
                        realm.beginTransaction();
                        realm.createObjectFromJson(com.voudeonibusapp.android.models.api.Line.class, object.toString());
                        realm.commitTransaction();

                    }

                    Toast toast = Toast.makeText(context, context.getString(R.string.toast_sync_date), Toast.LENGTH_SHORT);
                    toast.show();
                    EventBus.getDefault().post(MessageEvent.UPDATE_LINES);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public static void insertAllLines(Context context) {

        JSONArray lines = JSONUtils.readJSON(context, "lines.json");
        final Realm realm = Realm.getDefaultInstance();

        try {
            int length = lines.length();

            for (int i = 0; i < length; i++) {
                JSONObject object = lines.getJSONObject(i);
                realm.beginTransaction();
                realm.createObjectFromJson(com.voudeonibusapp.android.models.api.Line.class, object.toString());
                realm.commitTransaction();
            }

            Toast toast = Toast.makeText(context, context.getString(R.string.toast_sync_date), Toast.LENGTH_SHORT);
            toast.show();
            EventBus.getDefault().post(MessageEvent.UPDATE_LINES);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static RealmResults<Line> all() {

        final Realm realm = Realm.getDefaultInstance();

        return realm.where(Line.class).findAll();

    }

    public static Line findById(String _id) {
        final Realm realm = Realm.getDefaultInstance();
        Line line = realm.where(Line.class).equalTo("_id", _id).findFirst();
        return line;
    }

    public static Line findByTrip(Trip trip) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Line.class)
                .equalTo("trips._id", trip.get_id())
                .findFirst();
    }

}
