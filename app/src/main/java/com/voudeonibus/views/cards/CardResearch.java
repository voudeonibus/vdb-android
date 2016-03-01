package com.voudeonibus.views.cards;

import android.view.ViewGroup;
import com.voudeonibus.views.utils.SeekbarWithIntervals;

import com.voudeonibus.R;

import java.util.ArrayList;
import java.util.List;

public class CardResearch extends CardDefault {

    private SeekbarWithIntervals SeekbarWithIntervals;

    public CardResearch(ViewGroup parent) {
        super(parent, R.layout.card_research);

        setLayoutElements();

        List<String> seekbarIntervals = getIntervals();
        SeekbarWithIntervals.setIntervals(seekbarIntervals);
    }

    @Override
    public void setLayoutElements() {
        SeekbarWithIntervals = (SeekbarWithIntervals) this.itemView.findViewById(R.id.seekbarWithIntervals);
    }

    private List<String> getIntervals() {
        return new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("MAIS");
        }};
    }
}
