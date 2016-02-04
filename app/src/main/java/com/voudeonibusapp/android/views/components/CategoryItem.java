package com.voudeonibusapp.android.views.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.base.ViewGeneric;

public class CategoryItem extends ViewGeneric {

    private Context context;
    private Category category;
    private Trip trip;

    private CheckBox checkbox;
    private TextView textCategory;

    private boolean hasCategory = false;

    public CategoryItem(Context context, ViewGroup viewGroup, Category category, Trip trip) {
        super(context, viewGroup, R.layout.category_item);
        this.context = context;
        this.category = category;
        this.trip = trip;

        this.hasCategory = true;

        setContent();
        setClickListener();
    }

    public CategoryItem(Context context, ViewGroup viewGroup) {
        super(context, viewGroup, R.layout.category_item);
        this.context = context;

        this.hasCategory = false;

        setContent();
        setClickListener();
    }

    @Override
    public void setLayoutElements() {
        this.checkbox = (CheckBox) this.view.findViewById(R.id.checkbox);
        this.textCategory = (TextView) this.view.findViewById(R.id.text_category);
    }

    private void setContent() {

//        if (this.category != null) {
//
//
//            for (Trip t : category.getLines()) {
//                if (t.get_id().equals(trip.get_id())) {
//                    this.checkbox.setChecked(true);
//                }
//            }
//
//            this.textCategory.setText(this.category.getName());
//
//            Drawable dr = getContext().getDrawable(R.drawable.ic_check_on_w);
//            dr.setLevel(this.category.getColor().getLevel());
//
//            this.checkbox.setButtonDrawable(dr);
//
//
//
//        } else {
//            this.textCategory.setText("Outra categoria");
//        }
    }

    private void setClickListener() {


        this.textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CategoryItem.this.checkbox.toggle();
            }
        });

    }

    public boolean isHasCategory() {
        return hasCategory;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public Category getCategory() {
        return category;
    }
}
