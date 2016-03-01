package com.voudeonibus.views.components;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voudeonibus.R;
import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.views.adapter.list.AdapterCard;
import com.voudeonibus.views.base.ObservableScrollView;
import com.melnykov.fab.FloatingActionButton;

import de.greenrobot.event.EventBus;

public class VouAgoraView extends Fragment {

    private ViewGroup rootView;
    private WeatherView fragment_weather;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ObservableScrollView.Callbacks callbacks;
    private ObservableScrollView observableScrollView;
    private View wrapperWeather;
    private View placeholder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        rootView = (ViewGroup) inflater.inflate(R.layout.vou_agora, container, false);

        setLayoutElements();

        final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        setListItems();

        return rootView;
    }

    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent == MessageEvent.UPDATE_VOU_AGORA) {
            setListItems();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void setListItems() {
        mAdapter = new AdapterCard();
        recyclerView.setAdapter(mAdapter);
    }

    private void setLayoutElements() {


        recyclerView = (RecyclerView) rootView.findViewById(R.id.cards);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(MessageEvent.SWITCH_ALL_LINES);
            }
        });

//        fab.attachToListView(recyclerView);

//        recyclerView.setHasFixedSize(true);
//
//        placeholder = rootView.findViewById(R.id.placeholder);
//        wrapperWeather = rootView.findViewById(R.id.wrapperWeather);
//        observableScrollView = (ObservableScrollView) rootView.findViewById(R.id.vou_agora);
//
//        this.callbacks = new ObservableScrollView.Callbacks(wrapperWeather, placeholder);
//        callbacks.setObservableView(observableScrollView);


    }

    public void setOnScrollListener(RecyclerView.OnScrollListener scrollListener) {
//        recyclerView.setOnScrollListener(scrollListener);
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {

            private static final int HIDE_THRESHOLD = 20;
            private int scrolledDistance = 0;
            private boolean controlsVisible = true;

            private void onHide() {
                fragment_weather.getView().setVisibility(View.GONE);
            }

            private void onShow() {
                fragment_weather.getView().setVisibility(View.VISIBLE);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    onHide();
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    onShow();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }

                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                    scrolledDistance += dy;

                }
            }
        };
    }


}
