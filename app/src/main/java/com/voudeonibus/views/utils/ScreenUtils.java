package com.voudeonibus.views.utils;

import android.content.Context;

public class ScreenUtils {

    public static String getDensity(Context context) {
        float density= context.getResources().getDisplayMetrics().density;
        String labelDensity = "Without Density";

        if (density == 0.75) labelDensity = "LDPI";
        if (density == 1.0) labelDensity = "MDPI";
        if (density == 1.5) labelDensity = "HDPI";
        if (density == 2.0) labelDensity = "XHDPI";
        if (density == 3.0) labelDensity = "XXHDPI";
        if (density == 4.0) labelDensity = "XXXHDPI";

        return labelDensity;
    }

}
