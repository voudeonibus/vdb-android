package com.voudeonibus.views.cards;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.voudeonibus.R;

import com.voudeonibus.controller.TripController;
import com.voudeonibus.models.aux.VouAgora;
import com.voudeonibus.views.adapter.list.AdapterCardLine;
import com.voudeonibus.views.utils.ListViewsUtils;

public class CardGroupLine extends CardDefault {

    private VouAgora vouAgora;

    private LinearLayout cardLayout;
    private TextView cardTitle;
    private ListView listLines;
    private ImageView cardIcon;
    private TextView nextDayCardTitle;
    private LinearLayout linearCardTitle;

    private CardView cardLineList;

    public CardGroupLine(ViewGroup parent) {
        super(parent, R.layout.card_groupline);

        setLayoutElements();

    }

    @Override
    public void setLayoutElements() {
        this.cardLayout = (LinearLayout) this.itemView.findViewById(R.id.cardLayout);
        this.cardTitle = (TextView) this.itemView.findViewById(R.id.cardTitle);
        this.cardIcon = (ImageView) this.itemView.findViewById(R.id.cardIcon);
        this.listLines = (ListView) this.itemView.findViewById(R.id.listLines);
        this.nextDayCardTitle = (TextView) this.itemView.findViewById(R.id.nextDayCardTitle);
        this.cardLineList = (CardView) this.itemView.findViewById(R.id.cardLineList);
        this.linearCardTitle = (LinearLayout) this.itemView.findViewById(R.id.linearCardTitle);
    }

    public void setVouAgora(VouAgora vouAgora) {
        this.vouAgora = vouAgora;

        /**
         * Se o card é o primeiro da semana, sábado ou domingo
         * o título é mostrado.
         */

        if (vouAgora.isFirstDayTitle()) {
            this.linearCardTitle.setVisibility(View.VISIBLE);

            CardView.LayoutParams l = (CardView.LayoutParams) cardLineList.getLayoutParams();
            l.setMargins(0, 80, 0, 0);
            cardLineList.setLayoutParams(l);

        } else {
            this.linearCardTitle.setVisibility(View.GONE);

            /**
             * Aqui precisamos remover a margin
             */
            CardView.LayoutParams l = (CardView.LayoutParams) cardLineList.getLayoutParams();
            l.setMargins(0, 0, 0, 0);
            cardLineList.setLayoutParams(l);
        }

        /**
         * Aqui ele verifica qual é o dia do card e
         * coloca o título de acordo.
         */

        switch (vouAgora.getCategoryDays().getDay()) {
            case 1:
                this.nextDayCardTitle.setText("Sabado");
                break;
            case 2:
                this.nextDayCardTitle.setText("Domingos e feriados");
                break;
            default:
                this.nextDayCardTitle.setText("Dias da semana");
                break;
        }

        cardLayout.setBackgroundColor(Color.parseColor(vouAgora.getCategory().getColor().getColor()));
        cardTitle.setText(vouAgora.getCategory().getName());

        switch (vouAgora.getCategory().getIcon()) {
            case 0:
                cardIcon.setImageResource(R.drawable.ic_category_work);
                break;
            case 1:
                cardIcon.setImageResource(R.drawable.ic_category_study);
                break;
            default:
                cardIcon.setImageResource(R.drawable.ic_category_star);
                break;

        }

        AdapterCardLine cardLines = new AdapterCardLine(this.itemView.getContext(), TripController.breakTripInSchedule(vouAgora.getTrips(), vouAgora));
        listLines.setAdapter(cardLines);

        // Adiciona a altura conforme o tamanho de cada elemento
        ListViewsUtils.setListViewHeightBasedOnItems(listLines);
    }

}
