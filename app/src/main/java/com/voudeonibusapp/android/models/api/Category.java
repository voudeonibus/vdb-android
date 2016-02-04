package com.voudeonibusapp.android.models.api;

import com.voudeonibusapp.android.models.aux.ColorsCategory;
import com.voudeonibusapp.android.models.aux.Hours;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {

    @PrimaryKey
    private int idLocal;
    private String name;
    private int icon;
    private boolean isDefault;
    private ColorsCategory color;
    private RealmList<Line> lines;
    private RealmList<Hours> hours;
    private RealmList<CategoryDays> days;

    @Ignore
    private boolean insert = false;

    public Category() {
        Realm realm = Realm.getDefaultInstance();

        this.icon = 2;
        this.isDefault = false;
        this.lines = new RealmList<>();
        this.hours = new RealmList<>();
        this.days = new RealmList<>();
        this.idLocal = (int) realm.where(Category.class).maximumInt("idLocal") + 1;
    }

    public Category(RealmList<Line> trips, RealmList<Hours> times, RealmList<CategoryDays> days) {
        this();
        this.lines = trips;
        this.hours = times;
        this.days = days;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Line> getLines() {
        return lines;
    }

    public void setLines(RealmList<Line> lines) {
        this.lines = lines;
    }

    public RealmList<Hours> getHours() {
        return hours;
    }

    public void setHours(RealmList<Hours> hours) {
        this.hours = hours;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public ColorsCategory getColor() {
        return color;
    }

    public void setColor(ColorsCategory color) {
        this.color = color;
    }

    public RealmList<CategoryDays> getDays() {
        return days;
    }

    public void setDays(RealmList<CategoryDays> days) {
        this.days = days;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }
}
