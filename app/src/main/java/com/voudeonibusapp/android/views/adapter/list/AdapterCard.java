package com.voudeonibusapp.android.views.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.voudeonibusapp.android.controller.TripController;
import com.voudeonibusapp.android.controller.VouAgoraController;
import com.voudeonibusapp.android.models.aux.VouAgora;
import com.voudeonibusapp.android.views.cards.CardDefault;
import com.voudeonibusapp.android.views.cards.CardEmpty;
import com.voudeonibusapp.android.views.cards.CardGroupLine;
import com.voudeonibusapp.android.views.cards.CardInit;
import com.voudeonibusapp.android.views.cards.CardNoTime;
import com.voudeonibusapp.android.views.cards.CardReportBug;
import com.voudeonibusapp.android.views.cards.CardSearchButton;
import com.voudeonibusapp.android.views.cards.CardSupporter;

import io.realm.RealmList;

public class AdapterCard extends RecyclerView.Adapter<CardDefault> {

    private RealmList<VouAgora> vouAgoraCards;

    private boolean withoutVouAgora;
    private boolean showEmptyCard = false;
    private boolean showReportBugCard = false;
    private boolean hasTime = true;

    private int countEmpty = 0;

    public AdapterCard() {
        getTrips();
    }



    @Override
    public CardDefault onCreateViewHolder(ViewGroup parent, int i) {

        CardDefault cardDefault = null;

        switch (i) {
            case 0:
                cardDefault = new CardInit(parent);
                break;
            case 1:
                cardDefault = new CardNoTime(parent);
                break;
            case 2:
                cardDefault = new CardGroupLine(parent);
                break;
            case 3:
                cardDefault = new CardReportBug(parent);
                break;
            case 4:
                cardDefault = new CardEmpty(parent);
                break;
            case 5:
                cardDefault = new CardSearchButton(parent);
                break;
            case 6:
                cardDefault = new CardSupporter(parent);
                break;
        }

        return cardDefault;

    }

    @Override
    public void onBindViewHolder(CardDefault viewHolder, int i) {

        try {
            if (viewHolder instanceof CardGroupLine) {
                ((CardGroupLine) viewHolder).setVouAgora(vouAgoraCards.get(i));
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }



    @Override
    public int getItemCount() {

        if (!(vouAgoraCards.size() > 0) && !VouAgoraController.hasVouAgora()) {
            this.withoutVouAgora = true;
            return 3;
        }

        return vouAgoraCards.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        // CardSearchButton
        if (this.withoutVouAgora && position == 2) {
            return 5;
        }

        // CardSupporter
        if (this.withoutVouAgora && position == 1) {
            return 6;
        }

        // CardInit
        if (this.withoutVouAgora) {
            return 0;
        }

        // CardReportBug
        if (position == vouAgoraCards.size() ) {
            return 3;
        }

        // CardNoTime
        if (!hasTime && position == 0 && !this.withoutVouAgora) {
            return 1;
        }

        // CardGroupLine
        if (TripController.getCardGroupLine(vouAgoraCards.get(position)).size() > 0) {
            return 2;
        }

        // CardEmpty
        return 4;

    }

    private void getTrips() {
        vouAgoraCards = VouAgoraController.getAllVouAgora();

        hasTime = false;

        for (VouAgora vouAgora : vouAgoraCards) {
            if (vouAgora.getTrips().size() > 0) {
                hasTime = true;
            }
        }
    }

}
