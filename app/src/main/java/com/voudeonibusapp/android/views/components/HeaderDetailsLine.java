package com.voudeonibusapp.android.views.components;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.event.MessageEventObject;
import com.voudeonibusapp.android.models.api.Line;

import java.io.IOException;
import java.text.DecimalFormat;

import de.greenrobot.event.EventBus;

public class HeaderDetailsLine extends HeaderBase {

    private Line line;

    private View wrapper;
    private TextView lineNumberText;
    private TextView lineNameText;
    private TextView linePriceText;
    private View viewHeader;
    private ImageView backgrondImg;

    // wrapper
//    private View headerMenuButton;
    private View headerBackLinear;
    private View headerNotification;
    private View headerTitleText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.header_details_line, container, false);
        setDefaults();
        setLayoutElements();
        return this.view;
    }

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

    public void onEvent(final MessageEventObject event){

        if (event.getMessageEvent() == MessageEvent.GET_VIEW && event.getObject() instanceof View) {
            ((View) event.getObject()).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setInterpolator(new DecelerateInterpolator());
                    fadeIn.setDuration(1000);

                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setInterpolator(new AccelerateInterpolator());
                    fadeOut.setStartOffset(1000);
                    fadeOut.setDuration(1000);

                    if (((View) event.getObject()).getLayoutParams().height < 450) {
                        AnimationSet animation = new AnimationSet(true);
                        animation.addAnimation(fadeIn);
//                        headerMenuButton.setAnimation(animation);
                        headerBackLinear.setAnimation(animation);
                        headerNotification.setAnimation(animation);
                        headerTitleText.setAnimation(animation);
                    } else {
                        AnimationSet animation = new AnimationSet(true);
                        animation.addAnimation(fadeOut);
//                        headerMenuButton.setAnimation(animation);
                        headerBackLinear.setAnimation(animation);
                        headerNotification.setAnimation(animation);
                        headerTitleText.setAnimation(animation);
                    }
                }
            });
        }




    }

    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent == MessageEvent.HIDE_NOTIFICATION) {
            super.setNotification();
        }

    }

    private void setLayoutElements() {
        wrapper = this.view.findViewById(R.id.wrapper);
        this.lineNumberText = (TextView) this.view.findViewById(R.id.lineNumberText);
        this.lineNameText = (TextView) this.view.findViewById(R.id.lineNameText);
        this.linePriceText = (TextView) this.view.findViewById(R.id.linePriceText);
        this.viewHeader = this.view.findViewById(R.id.viewHeader);
        this.backgrondImg = (ImageView) this.view.findViewById(R.id.backgrondImg);
//        this.headerMenuButton = this.view.findViewById(R.id.headerMenuButton);
        this.headerBackLinear = this.view.findViewById(R.id.headerBackLinear);
        this.headerNotification = this.view.findViewById(R.id.headerNotification);
        this.headerTitleText = this.view.findViewById(R.id.headerTitleText);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLine(Line line) {
        this.line = line;

        this.lineNumberText.setText(this.line.getRoute_short_name());
        this.lineNameText.setText(this.line.getRoute_long_name());
        this.linePriceText.setText("R$ " + new DecimalFormat("0.00").format(this.line.getPrice()));

        try {

            Drawable d = Drawable.createFromStream(this.getActivity().getAssets().open("images/" + this.line.getRoute_short_name() +  ".jpg"), null);
            this.backgrondImg.setImageDrawable(d);

        } catch (IOException e) {
            this.wrapper.setBackgroundColor(this.getActivity().getResources().getColor(R.color.blue));
        }

    }

    @Override
    void inherithRemovePaddingTop() {
        this.viewHeader.setPadding(0, 0, 0, 0);
    }
}
