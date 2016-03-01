package com.voudeonibus.models.api;

import com.crashlytics.android.Crashlytics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;

public class Schedule extends RealmObject {

    private String _id;
    private String time;
    private int hour;
    private int minute;
    private Date timeDate;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;

        String[] a = this.time.split(":");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 0);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(a[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(a[1]));
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        Date d = cal.getTime();

        setTimeDate(d);

    }

    public Date getTimeDate() {

        Date date = null;
        DateFormat format = new SimpleDateFormat("mm:mm", Locale.getDefault());
        try {
            date = format.parse(this.time);
        } catch (ParseException e) {
            Crashlytics.logException(e);
        }

        return date;
    }

    public void setTimeDate(Date timeDate) {
        this.timeDate = timeDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getHour() {

        String[] a = this.time.split(":");

        this.setMinute(Integer.parseInt(a[1]));

        return Integer.parseInt(a[0]);
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        String[] a = this.time.split(":");

        return Integer.parseInt(a[1]);
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
