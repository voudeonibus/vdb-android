package com.voudeonibusapp.android.views.cards;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.event.MessageEvent;

import de.greenrobot.event.EventBus;

public class CardInit extends CardDefault {

    private Button btnWork;
    private Button btnStudent;
    private Button btnWorkStudent;

    private Button vouAgoraButton;

    private boolean showChoiceCategory = false;

    public CardInit(ViewGroup parent) {
        super(parent, R.layout.card_init);

        setClickListener();
    }

    @Override
    public void setLayoutElements() {
        vouAgoraButton = (Button) this.itemView.findViewById(R.id.vouAgoraButton);
       // btnWork = (Button) this.itemView.findViewById(R.id.btnWork);
       // btnStudent = (Button) this.itemView.findViewById(R.id.btnStudent);
       // btnWorkStudent = (Button) this.itemView.findViewById(R.id.btnWorkStudent);
    }

    private void setClickListener() {

        this.vouAgoraButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                    showChoiceCategory = true;
                    EventBus.getDefault().post(MessageEvent.SHOW_CHOICE_CATEGORY);
                    Answers.getInstance().logCustom(new CustomEvent("Open Choice Category"));

            }
        });


/*        this.btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardInit.this.itemView.getContext(), CategoryActivity.class);
                intent.putExtra("Category_id", 0);
                intent.putExtra("Is_first", 0);
                CardInit.this.itemView.getContext().startActivity(intent);
            }
        });

        this.btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardInit.this.itemView.getContext(), CategoryActivity.class);
                intent.putExtra("Category_id", 1);
                intent.putExtra("Is_first", 1);
                CardInit.this.itemView.getContext().startActivity(intent);
            }
        });

        this.btnWorkStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardInit.this.itemView.getContext(), CategoryActivity.class);
                intent.putExtra("Category_id", 0);
                intent.putExtra("Is_first", 2);
                CardInit.this.itemView.getContext().startActivity(intent);
            }
        });*/
    }
}
