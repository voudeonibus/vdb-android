package com.voudeonibus.views.cards;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.voudeonibus.R;

public class CardSupporter extends CardDefault {

    private ImageView supporterLogoImage;

    public CardSupporter(ViewGroup parent) {
        super(parent, R.layout.card_supporter);
        setLayoutElements();

        this.supporterLogoImage.setImageResource(R.drawable.ic_canarinho);
    }

    @Override
    public void setLayoutElements() {
        this.supporterLogoImage = (ImageView) this.itemView.findViewById(R.id.supporterLogoImage);
    }
}
