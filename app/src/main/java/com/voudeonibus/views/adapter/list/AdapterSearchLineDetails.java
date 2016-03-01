package com.voudeonibus.views.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibus.controller.SearchController;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.components.TripItem;

import io.realm.RealmList;
import io.realm.RealmResults;

public class AdapterSearchLineDetails extends ArrayAdapter<TripItem> {

    private Context context;
    private RealmList<Trip> list;
    private RealmResults<Trip> listResult;
    private AdapterSearchLine.TypeDetails typeDetails;
    private int category_day;


    private int lastPosition;


    public AdapterSearchLineDetails(Context context, RealmList<Trip> list, AdapterSearchLine.TypeDetails typeDetails, int category_day) {
        super(context, -1);
        this.context = context;
        this.list = list;
        this.typeDetails = typeDetails;
        this.category_day = category_day;
    }

    public AdapterSearchLineDetails(Context context, RealmResults<Trip> listResult, AdapterSearchLine.TypeDetails typeDetails, int category_day) {
        super(context, -1);
        this.context = context;
        this.listResult = listResult;
        this.typeDetails = typeDetails;
        this.category_day = category_day;
    }

    public AdapterSearchLineDetails(Context context, RealmList<Trip> list, AdapterSearchLine.TypeDetails typeDetails, String name, int category_day) {
        this(context, SearchController.filterTrip(list, name), typeDetails, category_day);
    }

    public AdapterSearchLineDetails(Context context, RealmResults<Trip> listResult, AdapterSearchLine.TypeDetails typeDetails, String name, int category_day) {
        this(context, SearchController.filterTrip(listResult, name), typeDetails, category_day);
    }

//
//    @Override
//    public TripItem onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new TripItem(context, parent, typeDetails, category_day);
//    }
//
//    @Override
//    public void onBindViewHolder(TripItem holder, int position) {
//
//        if (list == null) {
//            holder.setTrip(this.listResult.get(position));
//        } else {
//            holder.setTrip(this.list.get(position));
//        }
//
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TripItem tripItem = new TripItem(context, parent, typeDetails, category_day);
        if (list == null) {
            tripItem.setTrip(this.listResult.get(position));
        } else {
            tripItem.setTrip(this.list.get(position));
        }

        return tripItem.getView();

    }

    @Override
    public int getCount() {
        if (list == null) {
            return listResult.size();
        }

        return list.size();
    }
}
