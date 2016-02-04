package com.voudeonibusapp.android.views.cards;

import android.view.ViewGroup;
import android.widget.TextView;

import com.voudeonibusapp.android.R;

public class CardText extends CardDefault {

    private TextView cardPhraseText;

    public CardText(ViewGroup parent) {
        super(parent, R.layout.card_text);
        setLayoutElements();

        this.cardPhraseText.setText("Essas rotas servem apenas para fins de planejamento. Obras, trânsito intenso, fatores climáticos ou outros eventos podem fazer com que as condições sejam diferentes dos resultados no mapa, por isso é preciso planejar o trajeto levando tudo isso em conta. Obedeça a todas as sinalizações ou avisos que aparecerem em seu trajeto.");
    }

    @Override
    public void setLayoutElements() {
        this.cardPhraseText = (TextView) this.itemView.findViewById(R.id.cardPhraseText);
    }
}
