package com.voudeonibus.views.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.voudeonibus.R;
import com.voudeonibus.models.api.Line;
import com.voudeonibus.views.activity.CategorySearchDetailsActivity;
import com.voudeonibus.views.activity.SearchDetailsActivity;
import com.voudeonibus.views.adapter.list.AdapterSearchLine;

public class SearchItem extends RecyclerView.ViewHolder {

    private Line line;
    private Context context;
    private AdapterSearchLine.TypeDetails typeDetails;

    // Apenas pra teste
    private TextView lineNumberText;
    private TextView lineNameText;
    private ImageView category_line_the_id;

    private int color;
    private boolean hasCategory;
    private boolean isCategorySearchLineItem;
    private int idCategory;

    private String nameCategory;
    private int iconHeader;

    public SearchItem(Context context, ViewGroup viewGroup, AdapterSearchLine.TypeDetails typeDetails, boolean notFound, boolean isCategorySearchLineItem) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate((notFound) ? R.layout.search_item_not_found :  R.layout.search_item, viewGroup, false));

        if (!notFound) {
            this.context = context;
            this.typeDetails = typeDetails;
            setLayoutElements();
            setClick();
        }

        this.isCategorySearchLineItem = isCategorySearchLineItem;
    }

    public void setLine(Line line) {
        this.line = line;
        setValue();
    }

    public void setLayoutElements() {
        lineNumberText = (TextView) this.itemView.findViewById(R.id.lineNumberText);
        lineNameText = (TextView) this.itemView.findViewById(R.id.lineNameText);
        category_line_the_id = (ImageView) this.itemView.findViewById(R.id.category_line_the_id);
        if (isCategorySearchLineItem) {
            category_line_the_id.setVisibility(View.GONE);
        }
    }

    private void setValue() {
        lineNumberText.setText(line.getRoute_short_name());
        lineNameText.setText(line.getRoute_long_name());
    }

    private void setClick() {
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                if (SearchItem.this.typeDetails == AdapterSearchLine.TypeDetails.DETAILS_CATEGORY) {
                    intent = new Intent(context, CategorySearchDetailsActivity.class);
                } else {
                    intent = new Intent(context, SearchDetailsActivity.class);

                }

                intent.putExtra("Color", color);
                intent.putExtra("Has_Category", hasCategory);
                intent.putExtra("Category_Id", idCategory);
                intent.putExtra("Line_id", line.get_id());
                intent.putExtra("TypeDetails", typeDetails);
                intent.putExtra("Category_Name", nameCategory);
                intent.putExtra("Category_Icon", iconHeader);

                Answers.getInstance().logCustom(new CustomEvent("Open Search Details"));



                if (context instanceof Activity) {
                    Activity activity = (Activity) context;

                    if (SearchItem.this.typeDetails == AdapterSearchLine.TypeDetails.DETAILS_CATEGORY) {
                        activity.setResult(CategorySettingLineView.RESULT_TRIP, intent);
                        activity.finish();
                    } else {
                        activity.startActivityForResult(intent, CategorySettingLineView.REQUEST_TRIP);
                        activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                    }
                } else {
                    context.startActivity(intent);
                }


            }

        });
    }

    public boolean isHasCategory() {
        return hasCategory;
    }

    public void setHasCategory(boolean hasCategory) {
        this.hasCategory = hasCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public int getIconHeader() {
        return iconHeader;
    }

    public void setIconHeader(int iconHeader) {
        this.iconHeader = iconHeader;
    }
}
