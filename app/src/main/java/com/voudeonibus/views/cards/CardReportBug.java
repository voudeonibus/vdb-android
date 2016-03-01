package com.voudeonibus.views.cards;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.voudeonibus.R;

public class CardReportBug extends CardDefault {

    private Button reportBugButton;

    public CardReportBug(ViewGroup parent) {
        super(parent, R.layout.card_report_bug);
        setClickListener();
    }

    @Override
    public void setLayoutElements() {
        reportBugButton = (Button) this.itemView.findViewById(R.id.reportBugButton);
    }

    private void setClickListener() {
        this.reportBugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:falecom@voudeonibus.com?subject=Encontrei%20um%20erro&amp;body=Oi%2C%20eu%20encontrei%20um%20erro%3A%0A%0ANa%20tela%20de%3A%0AEle%20aconteceu%20quando%3A`"));
                CardReportBug.this.itemView.getContext().startActivity(browserIntent);
                System.out.println("Report bug!");
            }
        });
    }
}
