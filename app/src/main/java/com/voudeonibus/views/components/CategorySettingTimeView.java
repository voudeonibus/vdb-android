package com.voudeonibus.views.components;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.voudeonibus.R;
import com.voudeonibus.aux.CallbackListAdapater;
import com.voudeonibus.models.aux.Hours;
import com.voudeonibus.views.adapter.list.AdapterCategorySettingTime;
import com.voudeonibus.views.utils.ListViewsUtils;
import com.voudeonibus.views.utils.TimeUtils;

import java.util.ArrayList;

import io.realm.RealmList;

public class CategorySettingTimeView extends Fragment {

    private View view;
    private AdapterCategorySettingTime categorySettingTimeAdapter;
    private ListView categorySettingTimeList;
    private Button addButton;
    private ArrayList<Hours> list;

    private View categorySettingDefaultText;
    private LinearLayout categoryDumbArrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.category_setting_time, container, false);

        // Add header of the card setting
        String settingTitle = this.getString(R.string.ct_setting_title_time);
        Drawable settingIcon = this.getResources().getDrawable(R.drawable.ic_watch);
        new CategorySettingLayoutTitleView(this.view, settingTitle, settingIcon);

        this.list = new ArrayList<>();

        setLayoutElements();
        setListItems();
        setClickListener();

        return this.view;
    }

    private void setLayoutElements() {
        this.categorySettingTimeList = (ListView) this.view.findViewById(R.id.categorySettingTimeList);
        this.addButton = (Button) this.view.findViewById(R.id.addButton);
        this.categorySettingDefaultText = this.view.findViewById(R.id.categorySettingDefaultText);
        this.categoryDumbArrow = (LinearLayout) this.view.findViewById(R.id.categoryDumbArrow);
    }

    private void setListItems() {
        this.categorySettingTimeAdapter = new AdapterCategorySettingTime(this.view.getContext(), list, new CallbackListAdapater() {
            @Override
            public void exec(int i) {
                CategorySettingTimeView.this.list.remove(i);
                CategorySettingTimeView.this.setListItems();
            }
        });

        if (this.list.size() > 0) {
            this.categorySettingDefaultText.setVisibility(View.GONE);
            this.categoryDumbArrow.setVisibility(View.GONE);
        } else {
            this.categorySettingDefaultText.setVisibility(View.VISIBLE);
            this.categoryDumbArrow.setVisibility(View.VISIBLE);
        }

        this.categorySettingTimeList.setAdapter(this.categorySettingTimeAdapter);

        ListViewsUtils.setListViewHeightBasedOnItems(this.categorySettingTimeList);
    }

    private void setClickListener() {


        View.OnClickListener clickAdd = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hoursStart = TimeUtils.getHoursCurrent();
                int minuteStart = TimeUtils.getMinuteCurrent();
                int hoursEnd = (hoursStart == 24 ? 1 : hoursStart + 1);
                int minuteEnd = minuteStart;
                list.add(new Hours(hoursStart, minuteStart, hoursEnd, minuteEnd));
                setListItems();
            }

        };

        this.addButton.setOnClickListener(clickAdd);
        this.categorySettingDefaultText.setOnClickListener(clickAdd);
    }

    public void setHours(RealmList<Hours> hours) {
        for (Hours hour : hours) {
            this.list.add(hour);
        }
        setListItems();
    }

    public RealmList<Hours> getHours() {
        RealmList<Hours> hours = new RealmList<>();

        for (Hours hour : list) {
            hours.add(hour);
        }

        return hours;
    }


}
