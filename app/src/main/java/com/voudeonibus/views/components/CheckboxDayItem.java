package com.voudeonibus.views.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.voudeonibus.R;
import com.voudeonibus.views.base.ViewGeneric;

public class CheckboxDayItem extends ViewGeneric {

    private int value;
    private String title;

    private TextView dayNameText;
    private CheckBox dayValueCheckbox;


    public CheckboxDayItem(Context context, ViewGroup viewGroup, int value, String title) {
        super(context, viewGroup, R.layout.category_setting_day_item);
        this.value = value;
        this.title = title;
        this.dayNameText.setText(title);
        setClickListener();
    }

    @Override
    public void setLayoutElements() {
        this.dayNameText = (TextView) this.getView().findViewById(R.id.dayNameText);
        this.dayValueCheckbox = (CheckBox) this.getView().findViewById(R.id.dayValueCheckbox);
    }

    private void setClickListener() {

        this.dayNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckboxDayItem.this.dayValueCheckbox.toggle();

            }
        });

    }

    public void setCheck(boolean check) {
        this.dayValueCheckbox.setChecked(check);
    }

    public boolean isChecked() {
        return this.dayValueCheckbox.isChecked();
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}
