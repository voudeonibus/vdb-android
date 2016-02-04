package com.voudeonibusapp.android.views.adapter.list;

import android.app.Fragment;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibusapp.android.controller.CategoryController;
import com.voudeonibusapp.android.enums.ETypeActivity;
import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.views.components.SidebarCategoryItem;

import io.realm.RealmResults;

public class AdapterSidebarCategory extends ArrayAdapter<String> {

    private Fragment fragment;
    private Context context;
    private RealmResults<Category> list;
    private boolean hasEmptyCategory;
    private ETypeActivity eTypeActivity;

    public AdapterSidebarCategory(Context context, Fragment fragment, RealmResults<Category> list, ETypeActivity eTypeActivity) {
        super(context, -1);
        this.fragment = fragment;
        this.context = context;
        this.list = list;
        this.hasEmptyCategory = CategoryController.hasEmptyCategory();

        this.eTypeActivity = eTypeActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (hasEmptyCategory && position == (list.size())) {
            return new SidebarCategoryItem(context, fragment, parent, true, eTypeActivity).getView();
        } else {
            return new SidebarCategoryItem(context, fragment, parent, list.get(position), eTypeActivity).getView();
        }
    }

    @Override
    public int getCount() {

        /*
         * Verify has more category to create, if yes, add more one to count for
         * add the button 'add category'
         */

        if (hasEmptyCategory) {
            return list.size() + 1;
        } else {
            return list.size();
        }

    }
}
