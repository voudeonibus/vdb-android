package com.voudeonibus.views.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibus.R;
import com.voudeonibus.models.api.CategoryDays;
import com.voudeonibus.views.components.CheckboxDayItem;

import java.util.ArrayList;

import io.realm.RealmList;

public class AdapterCategorySettingDay extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> list;
    private ArrayList<CheckboxDayItem> listView;
    private boolean isFirst;

    private RealmList<CategoryDays> categoryDayses;

    public AdapterCategorySettingDay(Context context) {
        super(context, -1);

        this.context = context;
        this.list = new ArrayList<>();
        this.listView = new ArrayList<>();

        this.list.add(context.getResources().getString(R.string.day_0));
        this.list.add(context.getResources().getString(R.string.day_1));
        this.list.add(context.getResources().getString(R.string.day_2));

    }

    public AdapterCategorySettingDay(Context context, RealmList<CategoryDays> categoryDayses) {
        this(context);
        this.categoryDayses = categoryDayses;
    }

    public AdapterCategorySettingDay(Context context, boolean isFirst) {
        this(context);
        this.isFirst = isFirst;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position == 0) {
            listView = new ArrayList<>();
        }

        CheckboxDayItem checkboxDayItemView = new CheckboxDayItem(context, parent, position, this.list.get(position));
        listView.add(checkboxDayItemView);

        if (this.categoryDayses != null) {
            for (CategoryDays categoryDays : this.categoryDayses) {
                if (categoryDays.getDay() == position) {
                    checkboxDayItemView.setCheck(true);
                }
            }
        } else if (this.isFirst) {
            if (this.list.get(position).equals(context.getResources().getString(R.string.day_0))) {
                checkboxDayItemView.setCheck(true);
            }
        }

        return checkboxDayItemView.getView();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    public ArrayList<CategoryDays> getDays() {

        ArrayList<CategoryDays> listDays = new ArrayList<>();

        for (CheckboxDayItem checkboxDayItem : this.listView) {
            if (checkboxDayItem.isChecked()) {
                listDays.add(new CategoryDays(checkboxDayItem.getValue()));
            }
        }

        return listDays;
    }
}
