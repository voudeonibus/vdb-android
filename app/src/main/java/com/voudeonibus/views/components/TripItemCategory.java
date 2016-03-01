package com.voudeonibus.views.components;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.voudeonibus.R;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.adapter.list.AdapterCardTripDeparture;
import com.voudeonibus.views.utils.ListViewsUtils;

public class TripItemCategory extends RecyclerView.ViewHolder {

    private Trip trip;
    private boolean hasCategory;
    private int idCategory;


    private Context context;

    private TextView textTrip;
    private ListView tripDepartureList;
    private Button btnAddRemove;

    public TripItemCategory(Context context, ViewGroup viewGroup, boolean hasCategory, int idCategory) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_item_details_category, viewGroup, false));
        this.context = context;
        this.hasCategory = hasCategory;
        this.idCategory = idCategory;

        setLayoutElements();
        setClickListener();
    }

    private void setLayoutElements() {
        this.textTrip = (TextView) this.itemView.findViewById(R.id.textTrip);
        this.tripDepartureList = (ListView) this.itemView.findViewById(R.id.tripDepartureList);
        this.btnAddRemove = (Button) this.itemView.findViewById(R.id.btnAddRemove);
    }

    private void setClickListener() {
        this.btnAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = null;

                if (context instanceof Activity) {
                    activity = (Activity) context;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("Trip_id", trip.get_id());
                activity.setResult(CategorySettingLineView.RESULT_TRIP, resultIntent);
                activity.finish();
                activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });
    }

    public void setTrip(Trip trip) {
        this.trip = trip;

        this.textTrip.setText(this.trip.getOrigin() + " / " + this.trip.getDestination());


        AdapterCardTripDeparture adapterCardTripDeparture = new AdapterCardTripDeparture(this.itemView.getContext(), this.trip.getDirections());
        this.tripDepartureList.setAdapter(adapterCardTripDeparture);

        ListViewsUtils.setListViewHeightBasedOnItems(this.tripDepartureList);


        this.btnAddRemove.setText(this.context.getResources().getString(R.string.label_add));
        this.btnAddRemove.setTextColor(this.context.getResources().getColor(R.color.blue));

    }
}
