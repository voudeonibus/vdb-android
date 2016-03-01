package com.voudeonibus.views.utils;

import com.voudeonibus.R;

public class CategoryUtils {

    public static int getIconCategory(int i) {
        switch (i) {
            case 0:
                return R.drawable.ic_category_work;
            case 1:
                return R.drawable.ic_category_study;
            default:
                return R.drawable.ic_category_star;
        }
    }

}
