package com.voudeonibusapp.android.views.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

public class CustoDetailsLinearLayoutManager extends LinearLayoutManager {

    public CustoDetailsLinearLayoutManager(Context context) {
        super(context);
    }

    public int getChildsHeight() {

        Log.d("Ops w", String.valueOf(this.getChildCount()));

        for (int i = 0; i < this.getChildCount(); i++) {
            Log.d("Ops h", String.valueOf(this.getChildAt(i).getMeasuredHeight()));
        }

        return 0;
    }
}
