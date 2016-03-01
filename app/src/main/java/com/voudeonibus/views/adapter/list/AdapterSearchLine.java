package com.voudeonibus.views.adapter.list;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.voudeonibus.controller.SearchController;
import com.voudeonibus.models.api.Line;
import com.voudeonibus.views.components.SearchItem;

import io.realm.RealmResults;

public class AdapterSearchLine extends RecyclerView.Adapter<SearchItem> {



    private boolean notFound;

    public enum TypeDetails {
        DETAILS_CATEGORY,
        DETAILS_TODAS_LINHAS
    }

    private Context context;
    private Activity activity;
    private RealmResults<Line> list;
    private TypeDetails typeDetails;

    private int color;
    private boolean hasCategory;
    private int idCategory;
    private boolean isCategorySearchLineItem;

    private String nameCategory;
    private int iconHeader;

    public AdapterSearchLine(Context context, RealmResults<Line> list, TypeDetails typeDetails, boolean isCategorySearchItem) {

        this.context = context;
        this.list = list;
        this.typeDetails = typeDetails;
        this.isCategorySearchLineItem = isCategorySearchItem;

        if (this.context instanceof Activity) {
            this.activity = (Activity) context;
        }
    }

    public AdapterSearchLine(Context context, String search, TypeDetails typeDetails, boolean isCategorySearchLineItem) {
        this(context, SearchController.findByRouteName(search), typeDetails, isCategorySearchLineItem);
    }

    public AdapterSearchLine(Context context, RealmResults<Line> list, TypeDetails typeDetails, boolean hasCategory, int idCategory, int color, String nameCategory, int iconHeader, boolean isCategorySearchLineItem) {
        this(context, list, typeDetails, isCategorySearchLineItem);
        this.hasCategory = hasCategory;
        this.idCategory = idCategory;
        this.color = color;
        this.nameCategory = nameCategory;
        this.iconHeader = iconHeader;
    }

    public AdapterSearchLine(Context context, String search, TypeDetails typeDetails, boolean hasCategory, int idCategory, int color, String nameCategory, int iconHeader, boolean isCategorySearchLineItem) {
        this(context, SearchController.findByRouteName(search), typeDetails, hasCategory, idCategory, color, nameCategory, iconHeader, isCategorySearchLineItem);
    }

    @Override
    public SearchItem onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchItem item = new SearchItem(this.context, parent, typeDetails, notFound, isCategorySearchLineItem);
        item.setIdCategory(idCategory);
        item.setHasCategory(hasCategory);
        item.setColor(color);
        item.setIconHeader(iconHeader);
        item.setNameCategory(nameCategory);

        return item;
    }

    @Override
    public void onBindViewHolder(SearchItem holder, int position) {

        if (!notFound) {
            holder.setLine(this.list.get(position));
        }
    }

    @Override
    public int getItemCount() {

        if (list.size() > 0) {
            notFound = false;
            return this.list.size();
        } else {
            notFound = true;
            return 1;
        }
    }


}
