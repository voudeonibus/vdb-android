package com.voudeonibusapp.android.views.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.aux.CallbackListAdapater;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.models.api.Trip;

import java.util.ArrayList;

public class AdapterCategorySettingLine extends ArrayAdapter<Trip> {

    private Context context;
    private ArrayList<Line> list;

    private View rowView;
    private TextView lineNumberText;
    private TextView lineNameText;
    private View lineDeleteButton;

    private CallbackListAdapater callbackListAdapater;

    public AdapterCategorySettingLine(Context context, ArrayList<Line> list, CallbackListAdapater callbackListAdapater) {
        super(context, -1);

        this.context = context;
        this.list = list;
        this.callbackListAdapater = callbackListAdapater;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setLayoutInflater(parent);
        setLayoutElements();
        setClickListener(position);

        this.lineNumberText.setText(this.list.get(position).getRoute_short_name());
        this.lineNameText.setText(this.list.get(position).getRoute_long_name());

        return this.rowView;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    private void setLayoutInflater(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rowView = inflater.inflate(R.layout.category_setting_line_item, parent, false);
    }

    private void setLayoutElements() {
        this.lineNumberText = (TextView) this.rowView.findViewById(R.id.lineNumberText);
        this.lineNameText = (TextView) this.rowView.findViewById(R.id.lineNameText);
        this.lineDeleteButton = this.rowView.findViewById(R.id.lineDeleteButton);
    }

    private void setClickListener(final int position) {
        this.lineDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Line line = AdapterCategorySettingLine.this.list.get(position);
                AdapterCategorySettingLine.this.callbackListAdapater.exec(position);

//                ModalUtils.confirmation(getContext(), "Tem certeza que deseja deletar " + trip.getOrigin() + "/" + trip.getDestination() + "?", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AdapterCategorySettingLine.this.callbackListAdapater.exec(position);
//                    }
//                });


            }
        });
    }
}
