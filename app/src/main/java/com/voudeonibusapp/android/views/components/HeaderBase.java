package com.voudeonibusapp.android.views.components;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.NotificationController;
import com.voudeonibusapp.android.event.MessageEvent;

import de.greenrobot.event.EventBus;

public abstract class HeaderBase extends Fragment {

    protected View view;

    private View wrapper;
    private LinearLayout headerBackLinear;
    private ImageView headerMenuButton;
    private Button headerBackButton;
    private TextView headerTitleText;
    private ImageView headerNotificationBackground;
    private Button headerNotificationButton;
    private TextView headerNotificationCount;

    private View.OnClickListener clickCustomBackListener;
    private String labelBackButton;

    protected String title;
    protected int color;

    public static boolean NOTIFICATION_IS_OPEN = false;

    private Sidebar sidebar;
    private boolean executeSidebar = false;

    private int animationOnTransition = 0;

    public void setDefaults() {
        setLayoutElements();
        setContent();
        setNotification();
        setClickListener();
    }

    private void setLayoutElements() {
        headerBackLinear = (LinearLayout) this.view.findViewById(R.id.headerBackLinear);
        headerTitleText = (TextView) this.view.findViewById(R.id.headerTitleText);
        headerMenuButton = (ImageView) this.view.findViewById(R.id.headerMenuButton);
        headerBackButton = (Button) this.view.findViewById(R.id.headerBackButton);
        headerNotificationButton = (Button) this.view.findViewById(R.id.headerNotificationButton);
        headerNotificationBackground = (ImageView) this.view.findViewById(R.id.headerNotificationBackground);
        headerNotificationCount = (TextView) this.view.findViewById(R.id.headerNotificationCount);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setNotification() {
//        int count = NotificationController.countNotification();
//        if (count > 0) {
//            headerNotificationCount.setText(String.valueOf(count));
//            headerNotificationBackground.setImageResource(R.drawable.ic_notification_with_notification_without_number);
//        } else {
//            headerNotificationBackground.setImageResource(R.drawable.ic_notification_icon);
//        }
    }

    private void setContent() {
        if (this.title != null && this.title != "") {
            headerTitleText.setText(this.title);
        }
    }

    private void setClickListener() {
        this.headerMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidebar.getSwipe().toggle();
            }
        });

        this.headerNotificationButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                if (HeaderBase.NOTIFICATION_IS_OPEN) {
                    HeaderBase.NOTIFICATION_IS_OPEN = false;
                    HeaderBase.this.setNotification();
                    EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION);
                } else {
                    HeaderBase.NOTIFICATION_IS_OPEN = true;
                    headerNotificationCount.setText("");
                    headerNotificationBackground.setImageResource(R.drawable.ic_close);
                    EventBus.getDefault().post(MessageEvent.SHOW_NOTIFICATION);
                    Answers.getInstance().logCustom(new CustomEvent("Open Notification"));
                }

                Log.d("Show shw", String.valueOf(HeaderBase.NOTIFICATION_IS_OPEN));

            }
        });

        final View.OnClickListener clickListenerBackButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (HeaderBase.NOTIFICATION_IS_OPEN) {
                    EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION);
                } else {


                    if (HeaderBase.this.clickCustomBackListener != null) {
                        HeaderBase.this.clickCustomBackListener.onClick(v);
                    } else {

                        HeaderBase.this.getActivity().finish();

                    }

                    if (HeaderBase.this.animationOnTransition == 1) {
                        HeaderBase.this.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                    }
                }
            }
        };

        this.headerBackLinear.setOnClickListener(clickListenerBackButton);
        this.headerBackButton.setOnClickListener(clickListenerBackButton);
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public void setTitle(String title) {
        this.title = title;
        if (this.title != null && this.title != "") {
            headerTitleText.setText(this.title);
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setExecuteSidebar(boolean executeSidebar) {
        this.executeSidebar = executeSidebar;

        if (this.executeSidebar) {
            this.headerMenuButton.setVisibility(View.VISIBLE);
            this.headerBackLinear.setVisibility(View.GONE);
        } else {
            this.headerMenuButton.setVisibility(View.GONE);
            this.headerBackLinear.setVisibility(View.VISIBLE);
        }
    }

    public View.OnClickListener getClickCustomBackListener() {
        return clickCustomBackListener;
    }

    public void setClickCustomBackListener(View.OnClickListener clickCustomBackListener) {
        this.clickCustomBackListener = clickCustomBackListener;
    }

    public void setLabelBackButton(String labelBackButton) {
        this.labelBackButton = labelBackButton;
        this.headerBackButton.setText(this.labelBackButton);
    }

    public void setAnimationOnTransition(int animationOnTransition) {
        this.animationOnTransition = animationOnTransition;
    }

    public void removePaddingTop() {
        inherithRemovePaddingTop();
    }

    abstract void inherithRemovePaddingTop();
}
