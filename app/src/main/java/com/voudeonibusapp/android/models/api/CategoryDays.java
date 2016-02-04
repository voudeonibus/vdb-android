package com.voudeonibusapp.android.models.api;

import com.voudeonibusapp.android.utils.Sha1HexUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoryDays extends RealmObject {

    @PrimaryKey
    private String _id;
    private int day;

    public CategoryDays() {
        try {
            this._id = Sha1HexUtils.makeSHA1Hash();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public CategoryDays(int day) {
        this();
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
