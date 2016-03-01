package com.voudeonibus.views.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibus.R;
import com.voudeonibus.event.MessageEvent;

import de.greenrobot.event.EventBus;

public class HeaderNormal extends HeaderBase {


    private boolean showTitleImg = false;
    private ImageView headerTitleImg;
    private TextView headerTitleText;

    private View wrapper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent == MessageEvent.HIDE_NOTIFICATION) {
            this.setNotification();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.header_normal, container, false);
        this.headerTitleImg = (ImageView) this.view.findViewById(R.id.headerTitleImg);
        this.headerTitleText = (TextView) this.view.findViewById(R.id.headerTitleText);
        this.wrapper = (View) this.view.findViewById(R.id.wrapper);
        setDefaults();

        showImg();

        return this.view;
    }

    private void showImg() {
        if (this.showTitleImg) {
            this.headerTitleImg.setVisibility(View.VISIBLE);
            this.headerTitleText.setVisibility(View.GONE);
        } else {
            this.headerTitleImg.setVisibility(View.GONE);
            this.headerTitleText.setVisibility(View.VISIBLE);
        }
    }

    public void setShowTitleImg(boolean showTitleImg) {
        this.showTitleImg = showTitleImg;

        showImg();
    }

    @Override
    void inherithRemovePaddingTop() {
        this.wrapper.setPadding(0, 0, 0, 0);
    }
}
