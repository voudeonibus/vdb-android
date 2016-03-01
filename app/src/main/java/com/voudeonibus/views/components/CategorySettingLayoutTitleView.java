package com.voudeonibus.views.components;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibus.R;

/**
 * Created by maikco on 13/08/15.
 */
public class CategorySettingLayoutTitleView {

    private View view;
    private ImageView categorySettingIconImage;
    private TextView categorySettingTitleText;

    public CategorySettingLayoutTitleView(View view, String title, Drawable icon) {
        this.view = view;

        setLayoutElements();
        setElementsValues(title, icon);
    }

    private void setLayoutElements() {
        this.categorySettingIconImage = (ImageView) this.view.findViewById(R.id.categorySettingIconImage);
        this.categorySettingTitleText = (TextView) this.view.findViewById(R.id.categorySettingTitleView);
    }

    private void setElementsValues(String title, Drawable icon) {
        this.categorySettingTitleText.setText(title);
        this.categorySettingIconImage.setImageDrawable(icon);
    }
}
