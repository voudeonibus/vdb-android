package com.voudeonibusapp.android.views.cards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CardDefault extends RecyclerView.ViewHolder {

    public CardDefault(View itemView) {
        super(itemView);
        setLayoutElements();
    }

    public CardDefault(ViewGroup parent, int layout) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));


    }

    public abstract void setLayoutElements();


}
