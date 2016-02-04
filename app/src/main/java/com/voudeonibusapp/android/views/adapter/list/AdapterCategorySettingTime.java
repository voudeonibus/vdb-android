package com.voudeonibusapp.android.views.adapter.list;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.aux.CallbackListAdapater;
import com.voudeonibusapp.android.models.aux.Hours;
import com.voudeonibusapp.android.views.utils.TimeUtils;

import java.util.ArrayList;

public class AdapterCategorySettingTime extends ArrayAdapter<Hours> {

    private Context context;
    private ArrayList<Hours> list;

    private View rowView;
    private TextView timeStartText;
    private TextView timeEndText;
    private View timeDeleteButton;

    private CallbackListAdapater callbackListAdapater;

    public AdapterCategorySettingTime(Context context, ArrayList<Hours> list, CallbackListAdapater callbackListAdapater) {
        super(context, -1, list);

        this.context = context;
        this.list = list;
        this.callbackListAdapater = callbackListAdapater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setLayoutInflater(parent);
        setLayoutElements();
        setClickListener(position);

        setTexts(position);

        return this.rowView;
    }

    private void setTexts(int position) {
        Hours hours = this.list.get(position);

        String start = String.format("%02d", hours.getHoursStart()) + ":" + String.format("%02d", hours.getMinuteStart());
        String end = String.format("%02d", hours.getHoursEnd()) + ":" + String.format("%02d", hours.getMinuteEnd());

        this.timeStartText.setText(start);
        this.timeEndText.setText(end);
    }

    private void setLayoutInflater(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rowView = inflater.inflate(R.layout.category_setting_time_item, parent, false);
    }

    private void setLayoutElements() {
        this.timeStartText = (TextView) this.rowView.findViewById(R.id.timeStartText);
        this.timeEndText = (TextView) this.rowView.findViewById(R.id.timeEndText);
        this.timeDeleteButton = this.rowView.findViewById(R.id.timeDeleteButton);
    }

    private void setClickListener(final int position) {
        this.timeDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterCategorySettingTime.this.callbackListAdapater.exec(position);
            }
        });

        this.timeStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Hours current = list.get(position);

                TimeUtils.showTimePicker("De:", context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        Hours previous = list.get(position);


                        Hours hours = new Hours();
                        hours.setHoursStart(hourOfDay);
                        hours.setMinuteStart(minute);
                        hours.setHoursEnd(previous.getHoursEnd());
                        hours.setMinuteEnd(previous.getMinuteEnd());

                        list.set(position, hours);

                        setTexts(position);
                    }
                }, current.getHoursStart(), current.getMinuteStart() );
            }
        });

        this.timeEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Hours current = list.get(position);


                TimeUtils.showTimePicker("Até:", context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hours previous = list.get(position);


                        Hours hours = new Hours();
                        hours.setHoursEnd(hourOfDay);
                        hours.setMinuteEnd(minute);
                        hours.setHoursStart(previous.getHoursStart());
                        hours.setMinuteStart(previous.getMinuteStart());

                        list.set(position, hours);

                        setTexts(position);
                    }
                }, current.getHoursEnd(), current.getMinuteEnd());
            }
        });
    }
}
