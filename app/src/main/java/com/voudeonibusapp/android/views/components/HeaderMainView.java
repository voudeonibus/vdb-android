package com.voudeonibusapp.android.views.components;

import android.app.Activity;

import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.views.utils.TranstionBackground;

import de.greenrobot.event.EventBus;

public class HeaderMainView {

    private Activity activity;

    private Button buttonVouAgora;
    private Button buttonTodasLinhas;

    private View tabs;
    private View line_tabs;

    private View header_main;

    private int position_tab = 1;

    public HeaderMainView(Activity activity) {
        this.activity = activity;
        setLayoutElements();

        EventBus.getDefault().register(this);

    }

    public HeaderMainView(Activity activity, View.OnClickListener vouAgoraClick, View.OnClickListener todasLinhasClick) {
        this(activity);
        setClickVouAgora(vouAgoraClick);
        setClickTodasLinhas(todasLinhasClick);
    }


    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent == MessageEvent.APPLY_BG_LEVEL_0) {
            header_main.getBackground().setLevel(0);
        }

        if (messageEvent == MessageEvent.APPLY_BG_LEVEL_1) {
            header_main.getBackground().setLevel(1);
        }


        if (messageEvent == MessageEvent.APPLY_BG_LEVEL_2) {
            header_main.getBackground().setLevel(2);
        }

        if (messageEvent == MessageEvent.APPLY_BG_LEVEL_3) {
            header_main.getBackground().setLevel(3);
        }

    }

    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }

    public void setClickVouAgora(View.OnClickListener clicklistener) {
        // @TODO: Refactor this
        // this.buttonVouAgora.setOnClickListener(clicklistener);
    }

    public void setClickTodasLinhas(View.OnClickListener clickListener) {
        // @TODO: Refactor this
        // this.buttonTodasLinhas.setOnClickListener(clickListener);
    }

    private void setLayoutElements() {
        header_main = activity.findViewById(R.id.header_main);
        buttonVouAgora = (Button) activity.findViewById(R.id.tabVouAgoraButton);
        buttonTodasLinhas = (Button) activity.findViewById(R.id.buttonTodasLinhas);
        tabs = activity.findViewById(R.id.tabs);
        line_tabs = activity.findViewById(R.id.line_tabs);
    }


    public void SlideToRight() {

        EventBus.getDefault().post(MessageEvent.APPLY_BACKGROUND_DEFAULT);

        TransitionDrawable transition = (TransitionDrawable) header_main.getBackground();
        transition.startTransition(TranstionBackground.TIME);

        if (position_tab == 1) {

            position_tab = 2;



            Animation slide = null;
            slide = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
                    0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

            slide.setDuration(100);
            slide.setFillAfter(true);
            slide.setFillEnabled(true);
            line_tabs.startAnimation(slide);

            slide.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    line_tabs.clearAnimation();

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(line_tabs.getWidth(), line_tabs.getHeight());
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    line_tabs.setLayoutParams(lp);

                    buttonVouAgora.setTypeface(Typeface.DEFAULT);
                    buttonTodasLinhas.setTypeface(Typeface.DEFAULT);
                }

            });
        }

    }

    public void SlideToLeft() {

        EventBus.getDefault().post(MessageEvent.REVERSE_BACKGROUND);

        TransitionDrawable transition = (TransitionDrawable) header_main.getBackground();
        transition.reverseTransition(TranstionBackground.TIME);

        if (position_tab == 2) {

            position_tab = 1;

            Animation slide = null;
            slide = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, -0.5f, Animation.RELATIVE_TO_PARENT,
                    0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

            slide.setDuration(100);
            slide.setFillAfter(true);
            slide.setFillEnabled(true);
            line_tabs.startAnimation(slide);

            slide.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    line_tabs.clearAnimation();

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(line_tabs.getWidth(), line_tabs.getHeight());
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    line_tabs.setLayoutParams(lp);

                    buttonTodasLinhas.setTypeface(Typeface.DEFAULT);
                    buttonVouAgora.setTypeface(Typeface.DEFAULT);

                }

            });

        }
    }



}
