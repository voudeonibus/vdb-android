package com.voudeonibus.views.components;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.voudeonibus.R;
import com.voudeonibus.models.api.CategoryDays;
import com.voudeonibus.views.adapter.list.AdapterCategorySettingDay;
import com.voudeonibus.views.utils.ListViewsUtils;

import io.realm.RealmList;

public class CategorySettingDayView extends Fragment {

    private View view;
    private AdapterCategorySettingDay categorySettingDayAdapter;
    private ListView categorySettingDayList;
    private boolean isFirst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.category_setting_day, container, false);

        // Add header of the card setting
        String settingTitle = this.getString(R.string.ct_setting_title_day);
        Drawable settingIcon = this.getResources().getDrawable(R.drawable.ic_calendar);
        new CategorySettingLayoutTitleView(this.view, settingTitle, settingIcon);
        this.categorySettingDayAdapter = new AdapterCategorySettingDay(this.view.getContext());
        setLayoutElements();
        setListItems();

        return this.view;
    }

    private void setLayoutElements() {
        this.categorySettingDayList = (ListView) this.view.findViewById(R.id.categorySettingDayList);
    }

    private void setListItems() {
        this.categorySettingDayList.setAdapter(this.categorySettingDayAdapter);

        ListViewsUtils.setListViewHeightBasedOnItems(this.categorySettingDayList);
    }

    public void setDays(RealmList<CategoryDays> days) {
        this.categorySettingDayAdapter = new AdapterCategorySettingDay(this.view.getContext(), days);
        setListItems();
    }

    public RealmList<CategoryDays> getDays() {

        RealmList<CategoryDays> days = new RealmList<>();

        for (CategoryDays categoryDay : this.categorySettingDayAdapter.getDays()) {
            days.add(categoryDay);
        }

        return days;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
        this.categorySettingDayAdapter = new AdapterCategorySettingDay(this.view.getContext(), this.isFirst);
        setListItems();
    }
}
