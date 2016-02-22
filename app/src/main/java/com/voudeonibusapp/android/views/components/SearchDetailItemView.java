package com.voudeonibusapp.android.views.components;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.TripController;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.activity.BaseActivity;
import com.voudeonibusapp.android.views.activity.SearchDetailsActivity;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLine;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLineDetails;

import java.text.DecimalFormat;

import io.realm.RealmResults;

@SuppressLint("ValidFragment")
public class SearchDetailItemView extends Fragment {

    private View view;
    private ViewGroup rootView;
    private ListView listTrips;
    private int category_day;
    private Line line;
    private RealmResults<Trip> trips;
    private AdapterSearchLine.TypeDetails typeDetails;
    private AdapterSearchLineDetails adapterSearchLineDetails;
    private BaseActivity baseActivity;
    private View wrapper_content_base;

    private TextView lineNumberText;
    private TextView lineNameText;
    private TextView linePriceText;

    View fHeader;
    View aSearchDetails;

    @SuppressLint("ValidFragment")
    public SearchDetailItemView(int category_day, Line line, AdapterSearchLine.TypeDetails typeDetails, BaseActivity baseActivity, View wrapper_content_base) {
        this.category_day = category_day;
        this.trips = TripController.getOnlyDay(this.category_day, line.getTrips());
        this.line = line;
        this.typeDetails = typeDetails;
        this.baseActivity = baseActivity;
        this.wrapper_content_base = wrapper_content_base;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.f_search_detail_item, container, false);
//        view = inflater.inflate(R.layout.details_lines_header, container, false);
//        fHeader = view.findViewById(R.id.fSearchDetailItem);
//        aSearchDetails = view.findViewById(R.id.aSearchDetails);
        this.adapterSearchLineDetails = new AdapterSearchLineDetails(rootView.getContext(), trips, typeDetails, category_day);
        setLayoutElements();
        setListItems();
        Log.d("Ops 2", String.valueOf(trips.size()) + " " + String.valueOf(category_day));
        this.setLine(this.line);
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLine(Line line) {
        this.line = line;

        this.lineNumberText.setText(this.line.getRoute_short_name());
        this.lineNameText.setText(this.line.getRoute_long_name());
        this.linePriceText.setText("R$ " + new DecimalFormat("0.00").format(this.line.getPrice()));

    }


    private void setLayoutElements() {
        this.listTrips = (ListView) rootView.findViewById(R.id.listTrips);
        this.lineNumberText = (TextView) getActivity().findViewById(R.id.lineNumberText);
        this.lineNameText = (TextView) getActivity().findViewById(R.id.lineNameText);
        this.linePriceText = (TextView) getActivity().findViewById(R.id.linePriceText);
    }

    protected void calculateSwipeRefreshFullHeight() {

        wrapper_content_base.getLayoutParams().height = 50000;
        listTrips.getLayoutParams().height = 50000;
        wrapper_content_base.requestLayout();
        listTrips.requestLayout();

    }

    private void setListItems() {
        this.listTrips.setAdapter(this.adapterSearchLineDetails);
        calculateSwipeRefreshFullHeight();
    }

    public void searchTrip(String name) {
        this.adapterSearchLineDetails = new AdapterSearchLineDetails(getActivity(), trips, typeDetails, category_day);
        this.setListItems();
    }
}
