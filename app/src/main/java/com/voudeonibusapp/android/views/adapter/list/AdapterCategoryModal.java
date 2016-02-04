package com.voudeonibusapp.android.views.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibusapp.android.controller.CategoryController;
import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.components.CategoryItem;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmResults;

public class AdapterCategoryModal extends ArrayAdapter<Category> {

    private Trip trip;
    private RealmList<Category> categories;
    private ArrayList<CategoryItem> categoryItems;
    private boolean hasEmptyCategory;

    public AdapterCategoryModal(Context context, RealmList<Category> categories, Trip trip) {
        super(context, -1);
        this.categories = categories;
        this.trip = trip;

        this.categoryItems = new ArrayList<>();

        hasEmptyCategory = CategoryController.hasEmptyCategory();

    }

    public AdapterCategoryModal(Context context, RealmResults<Category> categories, Trip trip) {
        super(context, -1);

        this.categoryItems = new ArrayList<>();

        RealmList<Category> list = new RealmList<>();
        list.addAll(categories);
        this.categories = list;
        this.trip = trip;

        hasEmptyCategory = CategoryController.hasEmptyCategory();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            CategoryItem categoryItem = new CategoryItem(getContext(), parent, this.categories.get(position), trip);
            this.categoryItems.add(categoryItem);
            return categoryItem.getView();
        } else {
            return convertView;
        }
    }

    @Override
    public int getCount() {
        return  this.categories.size();
    }

    public ArrayList<Category> getCategories() {

        ArrayList<Category> listCategories = new ArrayList<>();

        for (int i = 0; i < this.categoryItems.size(); i++) {
            CategoryItem categoryItem = categoryItems.get(i);

            if (i < getCount()) {

                if (categoryItem.isHasCategory()) {

                    Category category = categoryItem.getCategory();
                    category.setInsert(categoryItem.getCheckbox().isChecked());
                    listCategories.add(category);
                }
            }
        }


        return listCategories;

    }


}
