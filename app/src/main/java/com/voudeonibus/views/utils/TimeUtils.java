package com.voudeonibus.views.utils;

import android.app.TimePickerDialog;
import android.content.Context;

import com.voudeonibus.enums.ETime;

import org.joda.time.LocalDateTime;

import java.util.Calendar;

public class TimeUtils {

    public static void showTimePicker(String title, Context activity, TimePickerDialog.OnTimeSetListener setListener) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        showTimePicker(title, activity, setListener, hour, minute);
    }
    public static void showTimePicker(String title, Context activity, TimePickerDialog.OnTimeSetListener setListener, int hour, int minute) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(activity, setListener, hour, minute, true);
        mTimePicker.setTitle(title);
        mTimePicker.show();

    }

    public static int getHoursCurrent() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteCurrent() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static ETime getTime() {
        int hours = LocalDateTime.now().getHourOfDay();
        int min = LocalDateTime.now().getMinuteOfHour();

        if(hours >= 18){
            return ETime.NIGHT;
        }

        return ETime.DAY;
    }

}
