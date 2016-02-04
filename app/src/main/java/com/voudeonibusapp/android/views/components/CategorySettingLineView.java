package com.voudeonibusapp.android.views.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.aux.CallbackListAdapater;
import com.voudeonibusapp.android.controller.LineController;
import com.voudeonibusapp.android.models.api.Line;

import com.voudeonibusapp.android.views.activity.CategorySearchActivity;
import com.voudeonibusapp.android.views.adapter.list.AdapterCategorySettingLine;
import com.voudeonibusapp.android.views.utils.ListViewsUtils;

import java.util.ArrayList;

import io.realm.RealmList;


public class CategorySettingLineView extends Fragment {

    public static final int REQUEST_TRIP = 20;
    public static final int RESULT_TRIP = 20;

    private View view;
    private Context context;
    private Activity activity;

    private AdapterCategorySettingLine categorySettingLineAdapter;

    private ListView categorySettingLinesList;
    private Button addButton;
    private View categorySettingDefaultText;
    private LinearLayout categoryDumbArrow;

    private ArrayList<Line> listLines;

    private int color;
    private boolean hasCategory;
    private int idCategory;
    private String nameCategory;
    private int iconHeader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.category_setting_line, container, false);

        this.context = this.view.getContext();
        this.activity = (Activity) this.context;

        this.listLines = new ArrayList<>();

        // Add header of the card setting
        String settingTitle = this.getString(R.string.ct_setting_title_line);
        Drawable settingIcon = this.getResources().getDrawable(R.drawable.ic_trip);
        new CategorySettingLayoutTitleView(this.view, settingTitle, settingIcon);

        setLayoutElements();
        setListItems();
        settingClickListener();

        return this.view;
    }

    private void setLayoutElements() {
        this.categorySettingLinesList = (ListView) this.view.findViewById(R.id.categorySettingLinesList);
        this.categorySettingDefaultText = this.view.findViewById(R.id.categorySettingDefaultText);
        this.categoryDumbArrow = (LinearLayout) this.view.findViewById(R.id.categoryDumbArrow);
        this.addButton = (Button) this.view.findViewById(R.id.addButton);
    }

    private void setListItems() {

        this.categorySettingLineAdapter = new AdapterCategorySettingLine(this.view.getContext(), listLines, new CallbackListAdapater() {
            @Override
            public void exec(int i) {
                CategorySettingLineView.this.listLines.remove(i);
                CategorySettingLineView.this.setListItems();
            }
        });

        if (this.listLines.size() > 0) {
            this.categorySettingDefaultText.setVisibility(View.GONE);
            this.categoryDumbArrow.setVisibility(View.GONE);
        } else {
            this.categorySettingDefaultText.setVisibility(View.VISIBLE);
            this.categoryDumbArrow.setVisibility(View.VISIBLE);
        }

        this.categorySettingLinesList.setAdapter(this.categorySettingLineAdapter);

        ListViewsUtils.setListViewHeightBasedOnItems(this.categorySettingLinesList);
    }

    private void settingClickListener() {

        View.OnClickListener clickAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategorySearchActivity.class);
                intent.putExtra("Color", color);
                intent.putExtra("Has_Category", CategorySettingLineView.this.hasCategory);
                intent.putExtra("Category_Id", CategorySettingLineView.this.idCategory);
                intent.putExtra("Category_Name", CategorySettingLineView.this.nameCategory);
                intent.putExtra("Category_Icon", CategorySettingLineView.this.iconHeader);
                activity.startActivityForResult(intent, REQUEST_TRIP);
                activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        };

        this.addButton.setOnClickListener(clickAdd);
        this.categorySettingDefaultText.setOnClickListener(clickAdd);
    }

    public void setTrips(RealmList<Line> lines) {
        for (Line line: lines) {
            this.listLines.add(line);
        }
        setListItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CategorySettingLineView.REQUEST_TRIP && resultCode == CategorySettingLineView.RESULT_TRIP) {

            if (data.hasExtra("Line_id")) {
                listLines.add(LineController.findById(data.getStringExtra("Line_id")));
                setListItems();
            }
        }

    }

    public RealmList<Line> getLines() {

        RealmList<Line> lines = new RealmList<>();

        for (Line line : listLines) {
            lines.add(line);
        }

        return lines;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public void setHasCategory(boolean hasCategory) {
        this.hasCategory = hasCategory;
    }

    public void setIconHeader(int iconHeader) {
        this.iconHeader = iconHeader;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
