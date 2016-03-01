package com.voudeonibus.views.components;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.voudeonibus.R;
import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.views.activity.CategoryActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by maikco on 19/10/15.
 */
public class ChoiceCategoryFragment extends Fragment {

    private View view;

    private Button btnWork;
    private Button btnStudent;
    private Button btnWorkStudent;

    private LinearLayout bgChoiceCategory;

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

        if (messageEvent == MessageEvent.SHOW_CHOICE_CATEGORY) {
            this.view.setVisibility(View.VISIBLE);
        }

        if (messageEvent == MessageEvent.HIDE_CHOICE_CATEGORY) {
            this.view.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.dialog_choice_category, container, false);

        this.view.setVisibility(View.GONE);

        setLayoutElements();
        setClickListener();

        return this.view;
    }

    public void setLayoutElements() {
        btnWork = (Button) this.view.findViewById(R.id.btnWork);
        btnStudent = (Button) this.view.findViewById(R.id.btnStudent);
        btnWorkStudent = (Button) this.view.findViewById(R.id.btnWorkStudent);

        bgChoiceCategory = (LinearLayout) this.view.findViewById(R.id.bgChoiceCategory);
    }

    private void setClickListener() {

        this.bgChoiceCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceCategoryFragment.this.view.setVisibility(View.GONE);
            }
        });

        this.btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceCategoryFragment.this.view.getContext(), CategoryActivity.class);
                intent.putExtra("Category_id", 0);
                intent.putExtra("Is_first", 0);
                ChoiceCategoryFragment.this.view.getContext().startActivity(intent);
                ChoiceCategoryFragment.this.view.setVisibility(View.GONE);
            }
        });

        this.btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceCategoryFragment.this.view.getContext(), CategoryActivity.class);
                intent.putExtra("Category_id", 1);
                intent.putExtra("Is_first", 1);
                ChoiceCategoryFragment.this.view.getContext().startActivity(intent);
                ChoiceCategoryFragment.this.view.setVisibility(View.GONE);
            }
        });

        this.btnWorkStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceCategoryFragment.this.view.getContext(), CategoryActivity.class);
                intent.putExtra("Category_id", 0);
                intent.putExtra("Is_first", 2);
                ChoiceCategoryFragment.this.view.getContext().startActivity(intent);
                ChoiceCategoryFragment.this.view.setVisibility(View.GONE);
            }
        });
    }
}
