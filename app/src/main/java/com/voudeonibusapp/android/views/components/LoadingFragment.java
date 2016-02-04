package com.voudeonibusapp.android.views.components;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.event.MessageEvent;

import de.greenrobot.event.EventBus;

public class LoadingFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent == MessageEvent.OPEN_LOADING) {
            this.view.setVisibility(View.VISIBLE);
        }

        if (messageEvent == MessageEvent.CLOSE_LOADING) {
            this.view.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.loading, container, false);

         this.view.setVisibility(View.GONE);

        return this.view;
    }
}

