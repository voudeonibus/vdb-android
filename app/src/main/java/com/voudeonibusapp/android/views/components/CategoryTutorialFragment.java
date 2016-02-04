package com.voudeonibusapp.android.views.components;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.event.MessageEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by maikco on 23/10/15.
 */
public class CategoryTutorialFragment extends Fragment {

    private View view;
    private Button btnInit;

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

        if (messageEvent == MessageEvent.SHOW_CATEGORY_TUTORIAL) {
            this.view.setVisibility(View.VISIBLE);
        }

        if (messageEvent == MessageEvent.HIDE_CATEGORY_TUTORIAL) {
            this.view.setVisibility(View.GONE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.dialog_category_tutorial, container, false);

        this.view.setVisibility(View.GONE);

        setLayoutElements();
        setClickListener();

        return this.view;
    }

    public void setLayoutElements() {
        btnInit = (Button) this.view.findViewById(R.id.btnInit);
    }

    private void setClickListener() {
        this.btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(MessageEvent.HIDE_CATEGORY_TUTORIAL);
            }
        });
    }
}
