package com.voudeonibusapp.android.views.cards.lines;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.LineController;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.models.api.Variation;
import com.voudeonibusapp.android.views.base.ViewGeneric;

import io.realm.RealmList;

public class CardLine extends ViewGeneric {

    /**
     * Variable LinearLayout Identification
     */
    private TextView lineFrom;
    private TextView lineDestination;
    private TextView lineNumber;
    private ImageView lineOrientation;

    /**
     * Variable LinearLayout Description
     */
    private TextView lineDepartureTime;
    private TextView lineDescription;
    private TextView linePassageValue;

    private Trip trip;
    private Line line;

    /**
     * Construct method
     * @param context
     * @param parent
     */
    public CardLine(Context context, ViewGroup parent, Trip trip) {
        super(context, parent, R.layout.card_groupline_line);

        this.trip = trip;
        this.line = LineController.findByTrip(this.trip);

        setLayoutElements();

        this.lineOrientation.setImageResource(R.drawable.ic_arrow_line_up);

        if (trip.getDirection() == 0) {
            this.lineOrientation.setRotation(180);
        }

        this.lineFrom.setText(trip.getOrigin());
        this.lineDestination.setText(trip.getDestination());
        this.lineNumber.setText(line.getRoute_short_name());
        this.lineDepartureTime.setText(trip.getSchedule().getTime());
        this.lineDescription.setText(transformVariations(trip.getVariations()));
        this.linePassageValue.setText("R$ 3,40");
    }

    private String transformVariations(RealmList<Variation> variations) {

        String variationsS = null;

        for (Variation variation : variations) {

            if (variationsS == null) {
                variationsS = variation.getName();
            } else {
                variationsS += ", " + variation.getName();
            }
        }

        return variationsS;
    }

    @Override
    public void setLayoutElements() {
        /**
         * Set view ID's identification elements
         */
        this.lineFrom = (TextView) this.view.findViewById(R.id.lineFromText);
        this.lineDestination = (TextView) this.view.findViewById(R.id.lineDestinationText);
        this.lineNumber = (TextView) this.view.findViewById(R.id.lineNumberText);
        this.lineOrientation = (ImageView) this.view.findViewById(R.id.lineOrientation);

        /**
         * Set view ID's description elements
         */
        this.lineDepartureTime = (TextView) this.view.findViewById(R.id.lineDepartureTime);
        this.lineDescription = (TextView) this.view.findViewById(R.id.lineDescrition);
        this.linePassageValue = (TextView) this.view.findViewById(R.id.linePassageValue);
    }
}
