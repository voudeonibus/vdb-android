package com.voudeonibusapp.android.views.utils;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;

public class TagArrowUtils {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable createTag(String color) {

        ShapeDrawable shapeDrawableM = new ShapeDrawable(new RectShape());
//        shapeDrawableM.getShape().resize(100, 100);
        shapeDrawableM.setBounds(0, 0, 100, 20);
        shapeDrawableM.getPaint().setColor(Color.parseColor(color));

//        <size android:width="100dp" android:height="20dp"/>
//        <corners android:radius="0dp"/>


        RotateDrawable rotateDrawable = new RotateDrawable();
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setColor(Color.parseColor("#ffffff"));

        rotateDrawable.setFromDegrees(45.0f);
        rotateDrawable.setDrawable(shapeDrawable);


        RotateDrawable rotateDrawable1 = new RotateDrawable();
        ShapeDrawable shapeDrawable1 = new ShapeDrawable(new RectShape());
        shapeDrawable1.getPaint().setColor(Color.parseColor("#ffffff"));


        rotateDrawable1.setFromDegrees(-45.0f);
        rotateDrawable1.setDrawable(shapeDrawable1);

        Drawable[] layers = {shapeDrawableM, rotateDrawable, rotateDrawable1};
        LayerDrawable layerDrawable = new LayerDrawable(layers);


        layerDrawable.setLayerInset(1, 0, -40, -20, 38);
        layerDrawable.setLayerInset(2, 0, 40, -20, -50);


        return layerDrawable;

    }

}
