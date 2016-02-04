package com.voudeonibusapp.android.views.cards.lines;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.models.api.Departure;
import com.voudeonibusapp.android.models.api.Direction;
import com.voudeonibusapp.android.models.api.Schedule;
import com.voudeonibusapp.android.models.api.Variation;
import com.voudeonibusapp.android.views.base.ViewGeneric;
import com.voudeonibusapp.android.views.utils.ScreenUtils;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmResults;

public class CardTripDeparture extends ViewGeneric {

    private int ITEM_PER_ROW = 5;

    private Context context;

    private Direction direction;
    private int category_day;
    private boolean withoutCategoryDay = false;

    private LinearLayout listDeparture;
    private TextView tripItineraryText;
    private ViewStub tripDepartureDirection;
    private LinearLayout tableHours;

    public CardTripDeparture(Context context, ViewGroup parent, Direction direction, int category_day) {
        super(context, parent, R.layout.trip_item_departure);

        this.context = context;

        this.direction = direction;
        this.category_day = category_day;

        switch (ScreenUtils.getDensity(getView().getContext())) {
            case "MDPI":
                ITEM_PER_ROW = 3;
                break;
            case "HDPI":
                ITEM_PER_ROW = 4;
                break;
        }

        setLayoutElements();
        setVariations();
        setDirection();
        setDeparture();
    }

    public CardTripDeparture(Context context, ViewGroup parent, Direction direction, boolean withoutCategoryDay) {
        this(context, parent, direction, 0);
        this.withoutCategoryDay = withoutCategoryDay;

    }

    @Override
    public void setLayoutElements() {

        this.listDeparture = (LinearLayout) this.view.findViewById(R.id.listDeparture);
        this.tripItineraryText = (TextView) this.view.findViewById(R.id.tripItineraryText);
        this.tripDepartureDirection = (ViewStub) this.view.findViewById(R.id.tripDepartureDirection);
        this.tableHours = (LinearLayout) this.view.findViewById(R.id.table_hours);

        if (this.withoutCategoryDay) {
            this.tripDepartureDirection.setVisibility(View.GONE);
        }
    }

    public void setVariations() {
        String variationName = null;
        RealmList<Variation> variations = this.direction.getVariations();

        if (variations.size() < 1) {
            this.tripItineraryText.setVisibility(View.GONE);
            return;
        }

        for (Variation variation : variations) {
            if (variationName == null) {
                variationName = variation.getName();
            } else {
                variationName += " - " + variation.getName();
            }
        }

        this.tripItineraryText.setText(variationName);
    }

    public void setDirection() {
        Integer direction = this.direction.getDirection();

        if (direction == 1) {
            this.tripDepartureDirection.setLayoutResource(R.layout.trip_item_departure_direction_back);
        } else {
            this.tripDepartureDirection.setLayoutResource(R.layout.trip_item_departure_direction_forward);
        }

        View inflated = this.tripDepartureDirection.inflate();
    }

    public void setDeparture() {


        RealmResults<Departure> departures = null;

        if (withoutCategoryDay) {
            departures = this.direction.getDepartures().where().findAll();
        } else {
            departures = this.direction.getDepartures().where().equalTo("category_day", category_day).findAll();
        }

        for (Departure departure : departures) {

            RealmList<Schedule> schedules = departure.getSchedules();

            if (schedules.size() < 1) {
                this.listDeparture.setVisibility(View.GONE);
                return;
            }

            int item_row = 0;
            ArrayList<ArrayList<Schedule>> table = new ArrayList<>();

            for (int i = 0; i < schedules.size(); i++) {

                if (item_row == ITEM_PER_ROW) {
                    item_row = 0;
                }

                Schedule schedule = schedules.get(i);

                if (item_row == 0) {
                    table.add(new ArrayList<Schedule>());
                }

                table.get(table.size() - 1).add(schedule);

                item_row++;
            }

            for (int x = 0; x < table.size(); x++) {
                ArrayList<Schedule> schedulesTable = table.get(x);

                LinearLayout tableRow = new LinearLayout(context);
                LinearLayout.LayoutParams tableRowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=0;
                int topMargin=20;
                int rightMargin=0;
                int bottomMargin=20;

                switch (ScreenUtils.getDensity(getView().getContext())) {
                    case "MDPI":
                        topMargin=10;
                        bottomMargin=10;
                        break;
                    case "HDPI":
                        topMargin=15;
                        bottomMargin=15;
                        break;
                }

                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                tableRow.setLayoutParams(tableRowParams);

                for (int i = 0; i < schedulesTable.size(); i++) {

                    Schedule schedule = schedulesTable.get(i);

                    TextView textView = new TextView(context);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, this.context.getResources().getDimension(R.dimen.trip_departure_font_size));
                    textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
                    textView.setText(schedule.getTime());

                    LinearLayout.LayoutParams textViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    int margin = 27;

                    switch (i) {
                        case 0:
                            textViewLP.setMargins(0, 0, margin, 00);
                            break;
                        case 1:
                        case 2:
                        case 3:
                            textViewLP.setMargins(margin, 0, margin, 00);
                            break;
                        case 4:
                            textViewLP.setMargins(margin, 0, 0, 00);
                            break;
                    }

                    textView.setLayoutParams(textViewLP);

                    tableRow.addView(textView);
                }


//
//                if (schedulesTable.size() < ITEM_PER_ROW) {
//
//                    for (int i = 0; i < (ITEM_PER_ROW - schedules.size()); i++) {
//
//                        TextView textView = new TextView(context);
//                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, this.context.getResources().getDimension(R.dimen.trip_departure_font_size));
//                        textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
//                        tableRow.addView(textView);
//                    }
//
//                }

                tableHours.addView(tableRow);


                if (x != (table.size() - 1)) {
                    View lineViewBorder = new View(getContext());

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.height = 3;

                    lineViewBorder.setLayoutParams(lp);

                    lineViewBorder.setBackgroundColor(Color.parseColor("#e5e5e5"));

                    tableHours.addView(lineViewBorder);

                }
            }
        }
    }
}
