package com.voudeonibusapp.android.views.components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.daimajia.swipe.SwipeLayout;
import com.facebook.login.LoginManager;
import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.views.activity.BaseActivity;

import de.greenrobot.event.EventBus;

public class Sidebar {

    private BaseActivity activity;
    private Context context;
    private View sidebar;
    private View contentSwipe;
    private View auxTop;

    private SwipeLayout swipeLayout;
    private Button sbPrivacyAction;
    private Button sbAboutAction;
    private Button sbResearchAction;
    private View sbLogoffAction;
    private View wrapperSidebar;

    private boolean executeSidebar = false;

    private ButtonCategorySidebar fragment_button_category_sidebar;

    public Sidebar(Context context) {
        this.context = context;
        this.activity = (BaseActivity) context;

        setLayoutElements();

        settingSwipe();
        settingClickListener();



    }

    public Sidebar(Context context, boolean executeSidebar) {
        this.executeSidebar = executeSidebar;
        this.context = context;
        this.activity = (BaseActivity) context;

        setLayoutElements();

        settingSwipe();
        settingClickListener();

    }

    public Sidebar(Context context, View sidebar, View mainContent) {
        this.context = context;
        this.sidebar = sidebar;
        settingSwipe();
    }

    private void setLayoutElements() {
        this.auxTop = this.activity.findViewById(R.id.auxTop);
        this.sidebar = this.activity.findViewById(R.id.sidebar);
        this.contentSwipe = this.activity.findViewById(R.id.contentSwipe);
        this.wrapperSidebar = this.activity.findViewById(R.id.wrapperSidebar);
        this.swipeLayout =  (SwipeLayout) this.activity.findViewById(R.id.swipe);
        this.sbLogoffAction = this.activity.findViewById(R.id.sbLogoffAction);
        this.sbPrivacyAction = (Button) this.activity.findViewById(R.id.sbPrivacyAction);
        this.sbAboutAction = (Button) this.activity.findViewById(R.id.sbAboutAction);
        this.sbResearchAction = (Button) this.activity.findViewById(R.id.sbResearchAction);
//        this.fragment_button_category_sidebar = (ButtonCategorySidebar) this.activity.getFragmentManager().findFragmentById(R.id.fragment_button_category_sidebar);
//        this.fragment_button_category_sidebar.setTypeActivity(this.activity.eTypeActivity);


    }

    private void settingClickListener() {

        //this.sbProfileAction.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {}
        //});

        this.sbLogoffAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                EventBus.getDefault().post(MessageEvent.UPDATE_FACEBOOK_PROFILE);
            }
        });

        this.sbPrivacyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Answers.getInstance().logCustom(new CustomEvent("Click on privacy"));

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Sidebar.this.context.getString(R.string.link_privacy)));
                Sidebar.this.context.startActivity(browserIntent);
            }
        });

        this.sbAboutAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Answers.getInstance().logCustom(new CustomEvent("Click on about"));

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Sidebar.this.context.getString(R.string.link_about)));
                Sidebar.this.context.startActivity(browserIntent);
            }
        });

        this.sbResearchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Answers.getInstance().logCustom(new CustomEvent("Click on research"));

                final String appPackageName = Sidebar.this.context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    Sidebar.this.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    Sidebar.this.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

    private void settingSwipe() {


        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ((ScrollView) this.sidebar).setFillViewport(false);
            this.auxTop.setVisibility(View.GONE);
        }

        swipeLayout.getDragEdgeMap().clear();

        if (executeSidebar) {
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, wrapperSidebar);
        }

    }

    public SwipeLayout getSwipe() {
        return this.swipeLayout;
    }

    public void showHideLogoff(boolean show) {
        if (show) {
            this.sbLogoffAction.setVisibility(View.VISIBLE);
        } else {
            this.sbLogoffAction.setVisibility(View.GONE);
        }
    }

}
