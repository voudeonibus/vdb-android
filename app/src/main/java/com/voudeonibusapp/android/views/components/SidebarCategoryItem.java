package com.voudeonibusapp.android.views.components;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.enums.ETypeActivity;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.models.api.Category;
import com.voudeonibusapp.android.views.activity.CategoryActivity;
import com.voudeonibusapp.android.views.base.ViewGeneric;

import de.greenrobot.event.EventBus;

public class SidebarCategoryItem extends ViewGeneric {

    private boolean isButtonAdd = false;
    private Category category;

    private Fragment fragment;

    private TextView textView;
    private ImageView settingCardIcon;

    private ETypeActivity eTypeActivity;

    public SidebarCategoryItem(Context context, Fragment fragment, ViewGroup parent, Category category, ETypeActivity eTypeActivity) {
        super(context, parent, R.layout.sidebar_category_item);

        this.category = category;

        this.eTypeActivity = eTypeActivity;

        this.fragment = fragment;

        setBindingView();
        setClickListener();
    }

    public SidebarCategoryItem(Context context, Fragment fragment,  ViewGroup parent, boolean isButtonAdd, ETypeActivity eTypeActivity) {
        super(context, parent, R.layout.sidebar_category_item);
        this.isButtonAdd = isButtonAdd;

        this.eTypeActivity = eTypeActivity;

        this.fragment = fragment;

        setBindingView();
        setClickListener();

    }

    @Override
    public void setLayoutElements() {
        textView = (TextView) this.getView().findViewById(R.id.settingCardName);
        settingCardIcon = (ImageView) this.getView().findViewById(R.id.settingCardIcon);
    }

    private void setBindingView() {

        if (isButtonAdd) {
            settingCardIcon.setImageResource(R.drawable.ic_plus_category);

            textView.setText(context.getString(R.string.sidebar_add_category));
        } else {
            switch (category.getIcon()) {
                case 0:
                    settingCardIcon.setImageResource(R.drawable.ic_category_work_sidebar);
                    break;
                case 1:
                    settingCardIcon.setImageResource(R.drawable.ic_category_study_sidebar);
                    break;
                default:
                    settingCardIcon.setImageResource(R.drawable.ic_category_star);
                    break;

            }

            textView.setText(category.getName());
        }
    }

    private void setClickListener() {
        this.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(context, CategoryActivity.class);

            if (!SidebarCategoryItem.this.isButtonAdd) {
                intent.putExtra("Category_id", category.getIdLocal());
            }

            context.startActivity(intent);

                if (eTypeActivity == ETypeActivity.CATEGORY) {
                    fragment.getActivity().finish();
                }


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(MessageEvent.HIDE_SIDEBAR);
                }
            }, 100);

            }
        });
    }
}
