package com.voudeonibus.views.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.cards.lines.CardLine;

import io.realm.RealmList;

public class AdapterCardLine extends ArrayAdapter<Trip> {

    private Context context;
    private RealmList<Trip> list;

    public AdapterCardLine(Context context, RealmList<Trip> list) {
        super(context, -1);

        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardLine cardLine = new CardLine(this.context, parent, list.get(position));

        return cardLine.getView();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }
}
