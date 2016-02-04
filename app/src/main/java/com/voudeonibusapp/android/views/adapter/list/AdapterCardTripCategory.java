package com.voudeonibusapp.android.views.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.cards.lines.CardTripCategory;

import io.realm.RealmList;
import io.realm.RealmResults;

public class AdapterCardTripCategory extends ArrayAdapter<Category> {

    private Context context;
    private Trip trip;
    private RealmList<Category> list;

    public AdapterCardTripCategory(Context context, RealmResults<Category> list, Trip trip) {
        super(context, -1);

        this.context = context;
        this.trip = trip;
        this.list = new RealmList<>();
        this.list.addAll(list);
    }

    @Override
    public void remove(Category object) {
        this.list.remove(object);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardTripCategory cardTripCategory = new CardTripCategory(this.context, parent, list.get(position), trip, this);
        return cardTripCategory.getView();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }
}
