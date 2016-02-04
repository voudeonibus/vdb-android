package com.voudeonibusapp.android.views.components;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.LineController;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLine;
import com.voudeonibusapp.android.views.base.ObservableScrollView;

import de.greenrobot.event.EventBus;

public class TodasLinhasView extends Fragment {


    private ObservableScrollView.Callbacks callbacks;


    private ViewGroup rootView;


    private View wrapperEditSearch;
    private View placeholder;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView listLine;
    private EditText lineSearchEdit;

    private ObservableScrollView observableScrollView;

    public TodasLinhasView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.todas_linhas, container, false);
        setLayoutElements();
        setListLines();
        setListenersClick();

        EventBus.getDefault().register(this);

        return  rootView;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(MessageEvent message) {
        if (message == MessageEvent.UPDATE_LINES) {
            setListLines();
        }
    }

    private void setLayoutElements() {

        placeholder = rootView.findViewById(R.id.placeholder);
        wrapperEditSearch = rootView.findViewById(R.id.wrapperEditSearch);
        observableScrollView = (ObservableScrollView) rootView.findViewById(R.id.scroll_view);

        this.callbacks = new ObservableScrollView.Callbacks(wrapperEditSearch, placeholder);
        callbacks.setObservableView(observableScrollView);



        listLine = (RecyclerView) rootView.findViewById(R.id.listLines);
        layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        listLine.setLayoutManager(layoutManager);


        lineSearchEdit = (EditText) rootView.findViewById(R.id.lineSearchEdit);
    }

    private void setListLines() {
        AdapterSearchLine adapterSearchLine = new AdapterSearchLine(rootView.getContext(), LineController.all(), AdapterSearchLine.TypeDetails.DETAILS_TODAS_LINHAS, false);
        listLine.setAdapter(adapterSearchLine);
    }

    private void setListenersClick() {

        lineSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AdapterSearchLine adapterSearchLine = new AdapterSearchLine(rootView.getContext(), lineSearchEdit.getText().toString(), AdapterSearchLine.TypeDetails.DETAILS_TODAS_LINHAS, false);
                listLine.setAdapter(adapterSearchLine);
                Answers.getInstance().logCustom(new CustomEvent("Use search on 'todas linhas'")
                .putCustomAttribute("value", lineSearchEdit.getText().toString()));
            }
        });

    }

}
