package com.voudeonibus.models.aux;

import com.voudeonibus.models.api.Category;
import com.voudeonibus.models.api.CategoryDays;
import com.voudeonibus.models.api.Trip;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class VouAgora extends RealmObject {

    private Category category;
    private CategoryDays categoryDays;
    private Hours hours;
    private RealmList<Trip> trips;

    @Ignore
    private int category_day_aux;

    @Ignore
    private boolean onlyCurrentHours;

    @Ignore
    private boolean firstDayTitle;

    public VouAgora() {

    }

    public VouAgora(Category category) {
        this.category = category;
    }

    public VouAgora(Category category, RealmList<Trip> trips) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryDays getCategoryDays() {
        return categoryDays;
    }

    public void setCategoryDays(CategoryDays categoryDays) {
        this.categoryDays = categoryDays;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public int getCategory_day_aux() {
        return category_day_aux;
    }

    public void setCategory_day_aux(int category_day_aux) {
        this.category_day_aux = category_day_aux;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public boolean isOnlyCurrentHours() {
        return onlyCurrentHours;
    }

    public void setOnlyCurrentHours(boolean onlyCurrentHours) {
        this.onlyCurrentHours = onlyCurrentHours;
    }

    public boolean isFirstDayTitle() { return firstDayTitle; }

    public void setFirstDayTitle(boolean firstDayTitle) {
        this.firstDayTitle = firstDayTitle;
    }
}
