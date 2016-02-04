package com.voudeonibusapp.android.views.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewGeneric {

    protected Context context;
    protected ViewGroup viewGroup;
    protected View view;

    public ViewGeneric(Context context, ViewGroup viewGroup, int layout) {
        this.context = context;
        this.viewGroup = viewGroup;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(layout, viewGroup, false);

        setLayoutElements();
    }

    public Context getContext() {
        return context;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public View getView() {
        return view;
    }

    public abstract void setLayoutElements();

}
