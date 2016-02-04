package com.voudeonibusapp.android.models.aux;

import android.graphics.Color;

import com.voudeonibusapp.android.enums.EColorsCategory;

import io.realm.RealmObject;

public class ColorsCategory extends RealmObject {

    private String color;
    private int colorInt;
    private String nick;
    private boolean used;
    private int level;

    public ColorsCategory() {
        this.used = false;
    }

    public ColorsCategory(EColorsCategory nick, String color) {
        super();
        this.nick = nick.toString();
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        this.colorInt = Color.parseColor(this.color);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setNick(EColorsCategory nick) {
        this.nick = nick.toString();
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getColorInt() {
        return colorInt;
    }

    public void setColorInt(int colorInt) {
        this.colorInt = colorInt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
