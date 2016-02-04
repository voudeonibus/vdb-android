package com.voudeonibusapp.android.models.aux;

import com.voudeonibusapp.android.utils.Sha1HexUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Hours extends RealmObject {

    @PrimaryKey
    private String _id;
    private int hoursStart;
    private int minuteStart;
    private int hoursEnd;
    private int minuteEnd;

    private Date dateStart;
    private Date dateEnd;

    public Hours() {
        try {
            this._id = Sha1HexUtils.makeSHA1Hash();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public Hours(int hoursStart, int minuteStart, int hoursEnd, int minuteEnd) {
        this();
        this.hoursStart = hoursStart;
        this.minuteStart = minuteStart;
        this.hoursEnd = hoursEnd;
        this.minuteEnd = minuteEnd;
        setDateStart();
        setDateEnd();
    }

    public void setDateStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 0);
        cal.set(Calendar.HOUR_OF_DAY, this.hoursStart);
        cal.set(Calendar.MINUTE, this.minuteStart);
        cal.set(Calendar.SECOND, 0);
        this.dateStart = cal.getTime();
    }

    public void setDateEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 0);
        cal.set(Calendar.HOUR_OF_DAY, this.hoursEnd);
        cal.set(Calendar.MINUTE, this.minuteEnd);
        cal.set(Calendar.SECOND, 0);
        this.dateEnd = cal.getTime();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(int minuteEnd) {
        this.minuteEnd = minuteEnd;
        setDateEnd();
    }

    public int getHoursEnd() {
        return hoursEnd;
    }

    public void setHoursEnd(int hoursEnd) {
        this.hoursEnd = hoursEnd;
        setDateEnd();
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public void setMinuteStart(int minuteStart) {
        this.minuteStart = minuteStart;
        setDateStart();
    }

    public int getHoursStart() {
        return hoursStart;
    }

    public void setHoursStart(int hoursStart) {
        this.hoursStart = hoursStart;
        setDateStart();
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
}
