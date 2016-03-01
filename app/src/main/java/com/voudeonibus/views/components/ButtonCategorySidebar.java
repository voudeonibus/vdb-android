package com.voudeonibus.views.components;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.voudeonibus.R;
import com.voudeonibus.controller.CategoryController;
import com.voudeonibus.enums.ETypeActivity;
import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.views.adapter.list.AdapterSidebarCategory;

import de.greenrobot.event.EventBus;

public class ButtonCategorySidebar extends Fragment {

    private View view;
    private Button sbCategoryAction;
    private LinearLayout listSidebarCategory;
    private ETypeActivity eTypeActivity = ETypeActivity.MAIN;
    private AdapterSidebarCategory categories;
    private View wrapp_sidebar_category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.f_button_category_sidebar, container, false);

        setLayoutElements();
        setClickListener();
        getCategoryList();

        EventBus.getDefault().register(this);

        return this.view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void setTypeActivity(ETypeActivity eTypeActivity) {
        this.eTypeActivity = eTypeActivity;
        getCategoryList();
    }

    private void setLayoutElements() {
        sbCategoryAction = (Button) this.view.findViewById(R.id.sbCategoryAction);
        this.listSidebarCategory = (LinearLayout) this.view.findViewById(R.id.listSidebarCategory);
        this.wrapp_sidebar_category = this.view.findViewById(R.id.wrapp_sidebar_category);
    }

    private void setClickListener() {

    }

    /**
     * Carrega a lista de categorias do usuário
     */
    public void getCategoryList() {


        listSidebarCategory.removeAllViews();

        categories = new AdapterSidebarCategory(this.view.getContext(), this, CategoryController.all(), eTypeActivity);
        final int categoriesCount = categories.getCount();



        for (int i = 0; i < categoriesCount; i++) {

            Log.d("Ops a", String.valueOf(i));
            View item = categories.getView(i, null, listSidebarCategory);
            listSidebarCategory.addView(item);
        }

    }


    public void onEvent(MessageEvent event){

        if (event == MessageEvent.UPDATE_CATEGORY) {
            getCategoryList();
        }

    }
}
