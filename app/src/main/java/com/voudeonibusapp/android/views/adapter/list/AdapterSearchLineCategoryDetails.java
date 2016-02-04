package com.voudeonibusapp.android.views.adapter.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.components.TripItemCategory;

import io.realm.RealmList;

public class AdapterSearchLineCategoryDetails extends RecyclerView.Adapter<TripItemCategory> {

    private boolean hasCategory;
    private int idCategory;

    private Context context;
    private RealmList<Trip> trips;

    public AdapterSearchLineCategoryDetails(Context context, RealmList<Trip> trips, boolean hasCategory, int idCategory) {
        this.context = context;
        this.trips = trips;
        this.hasCategory = hasCategory;
        this.idCategory = idCategory;
    }

    @Override
    public TripItemCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TripItemCategory(context, parent, hasCategory, idCategory);
    }

    @Override
    public void onBindViewHolder(TripItemCategory holder, int position) {
        holder.setTrip(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
}
