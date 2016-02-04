package com.voudeonibusapp.android.views.components;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.adapter.list.AdapterCardTripDeparture;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLine;
import com.voudeonibusapp.android.views.base.ViewGeneric;
import com.voudeonibusapp.android.views.utils.ListViewsUtils;

import io.realm.RealmResults;

public class TripItem extends ViewGeneric {

    private Trip trip;
    private RealmResults<Category> categories;
    private AdapterSearchLine.TypeDetails typeDetails;
    private Context context;

    private ListView tripDepartureList;

    private TextView tripDestinationText;

    private int category_day;

    public TripItem(Context context, ViewGroup viewGroup) {
        super(context, viewGroup, R.layout.trip_item);
        setLayoutElements();
    }


    public TripItem(Context context, ViewGroup viewGroup, AdapterSearchLine.TypeDetails typeDetails, int category_day) {
        this(context, viewGroup);
        this.context = context;
        this.typeDetails = typeDetails;
        this.category_day = category_day;

        setLayoutElements();
    }

    public void setTrip(Trip trip) {
        this.trip = trip;

        setBinding();
        setDeparture();
    }

    public void setLayoutElements() {
        this.tripDestinationText = (TextView) this.view.findViewById(R.id.tripDestinationText);
        this.tripDepartureList = (ListView) this.view.findViewById(R.id.tripDepartureList);
    }

    private void setBinding() {
        this.tripDestinationText.setText(trip.getOrigin() + " / " + trip.getDestination());
    }

    private void setDeparture() {
        AdapterCardTripDeparture adapterCardTripDeparture = new AdapterCardTripDeparture(this.view.getContext(), this.trip.getDirections(), category_day);
        this.tripDepartureList.setAdapter(adapterCardTripDeparture);

        ListViewsUtils.setListViewHeightBasedOnItems(this.tripDepartureList);
    }
}
