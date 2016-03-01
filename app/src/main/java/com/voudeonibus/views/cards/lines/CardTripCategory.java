package com.voudeonibus.views.cards.lines;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.voudeonibus.R;
import com.voudeonibus.models.api.Category;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.adapter.list.AdapterCardTripCategory;
import com.voudeonibus.views.base.ViewGeneric;
import com.voudeonibus.views.utils.ModalUtils;

public class CardTripCategory extends ViewGeneric {

    private Trip trip;
    private Category category;

    private TextView tripCategoryNameText;
    private ImageButton tripCategoryRemoveButton;

    private AdapterCardTripCategory adapterCardTripCategory;

    public CardTripCategory(Context context, ViewGroup parent, Category category, Trip trip, AdapterCardTripCategory adapterCardTripCategory) {
        super(context, parent, R.layout.trip_item_category);

        this.trip = trip;
        this.category = category;

        this.adapterCardTripCategory = adapterCardTripCategory;

        setLayoutElements();
        setCategory();
        setClickListener();
    }

    @Override
    public void setLayoutElements() {
        this.tripCategoryNameText = (TextView) this.view.findViewById(R.id.tripCategoryNameText);
        this.tripCategoryRemoveButton = (ImageButton) this.view.findViewById(R.id.tripCategoryRemoveButton);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setCategory() {
        this.tripCategoryNameText.setText(this.category.getName());


//        this.tripCategoryNameText.setBackground(TagArrowUtils.createTag(this.category.getColor().getColor()));

        this.tripCategoryNameText.getBackground().setLevel(this.category.getColor().getLevel());



    }

    private void removeCategory() {
        this.adapterCardTripCategory.remove(this.category);
//        CategoryController.removeTrip(this.category, this.trip);
    }

    private void setClickListener() {

        this.tripCategoryRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModalUtils.confirmation(
                    CardTripCategory.this.getContext(),
                        CardTripCategory.this.category.getName(),
                    CardTripCategory.this.getContext().getString(R.string.messagem_remove_line_category),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeCategory();
                        }
                    });
            }
        });

    }
}
