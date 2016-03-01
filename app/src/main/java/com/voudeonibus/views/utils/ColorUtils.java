package com.voudeonibus.views.utils;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;


import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.voudeonibus.R;
import com.voudeonibus.controller.CategoryController;
import com.voudeonibus.models.aux.ColorsCategory;

import io.realm.RealmResults;

public class ColorUtils {

    private static int[] colorChoice(Context context){

        int[] mColorChoices=null;
        RealmResults<ColorsCategory> colors = CategoryController.findColorsNotUsed();

        if (colors.size() > 0) {
            mColorChoices = new int[colors.size()];
            for (int i = 0; i < colors.size(); i++) {
                mColorChoices[i] = Color.parseColor(colors.get(i).getColor());

            }
        }

        return mColorChoices;
    }

    public static void showDialog(Context context, FragmentManager fragmentManager, ColorPickerSwatch.OnColorSelectedListener onSelected) {
        ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title,
                colorChoice(context), 0, 3,
                ColorPickerDialog.SIZE_SMALL);
        colorcalendar.show(fragmentManager,"cal");

        colorcalendar.setOnColorSelectedListener(onSelected);
    }
}
