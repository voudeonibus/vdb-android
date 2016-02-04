package com.voudeonibusapp.android.views.components;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.NotificationController;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.views.adapter.list.AdapterNotification;
import com.voudeonibusapp.android.views.utils.ListViewsUtils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class NotificationFragment extends Fragment {

    private Context context;

    private View view;
    private View background;

    private ListView headerNotificationList;
    private Button btnClearNotification;
    private TextView notificationCleanMessage;

    private ArrayList<String> notifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.dialog_notification, container, false);
        this.context = this.view.getContext();

        this.view.setVisibility(View.GONE);

        this.notifications = new ArrayList<String>();
//        this.notifications.add("Guaramirim");
//        this.notifications.add("Rau");
//        this.notifications.add("Madre Paulina");

        setLayoutElements();
        setNotification();
        setClickListener();

        return this.view;
    }

    public void setNotification() {

        int count = this.notifications.size();

        if (count > 0) {
            AdapterNotification notification = new AdapterNotification(this.context, this.notifications);
            headerNotificationList.setAdapter(notification);

            ListViewsUtils.setListViewHeightBasedOnItems(headerNotificationList);
        } else {
            EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION_BTN_CLEAR);
        }

    }

    public void setLayoutElements() {
        this.btnClearNotification = (Button) this.view.findViewById(R.id.btnClearNotification);
        this.headerNotificationList = (ListView) this.view.findViewById(R.id.headerNotificationList);
        this.notificationCleanMessage = (TextView) this.view.findViewById(R.id.notificationCleanMessage);
        this.background = this.view.findViewById(R.id.background);
    }

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
        if (messageEvent == MessageEvent.HIDE_NOTIFICATION) {
            this.view.setVisibility(View.GONE);
            HeaderBase.NOTIFICATION_IS_OPEN = false;

        } else if (messageEvent == MessageEvent.SHOW_NOTIFICATION) {
            this.view.setVisibility(View.VISIBLE);
            this.view.bringToFront();
            HeaderBase.NOTIFICATION_IS_OPEN = true;
        } else if (messageEvent == MessageEvent.SHOW_NOTIFICATION_BTN_CLEAR) {
            this.btnClearNotification.setVisibility(View.VISIBLE);
            this.notificationCleanMessage.setVisibility(View.GONE);

        } else if (messageEvent == MessageEvent.HIDE_NOTIFICATION_BTN_CLEAR) {
            this.btnClearNotification.setVisibility(View.GONE);
            this.notificationCleanMessage.setVisibility(View.VISIBLE);
        }
    }

    private void setClickListener() {
        this.btnClearNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationFragment.this.removeAll();
            }
        });

        this.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION);
            }
        });
    }

    private void removeAll() {
        if (NotificationController.removeAll()) {
            this.notifications.clear();
            this.setNotification();
            EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION_BTN_CLEAR);
        } else {
            Log.d("Notification", "remove all bad");
        }
    }
}
