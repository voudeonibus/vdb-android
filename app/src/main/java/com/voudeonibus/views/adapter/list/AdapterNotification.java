package com.voudeonibus.views.adapter.list;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voudeonibus.R;

import java.util.ArrayList;

public class AdapterNotification extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> list;
    private View rowView;

    public AdapterNotification(Context context, ArrayList<String> list) {
        super(context, -1, list);

        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.dialog_notification_item, parent, false);

        TextView notificationTitle = (TextView) rowView.findViewById(R.id.notificationTitleText);
        LinearLayout notificationContent = (LinearLayout) rowView.findViewById(R.id.notificationContent);
        LinearLayout notificationCategory = (LinearLayout) rowView.findViewById(R.id.notificationCategory);

        // Texto do conteudo
        TextView phraseOne = new TextView(getContext());

        phraseOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        phraseOne.setText(R.string.mk_one);
        phraseOne.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT));

        ImageView categoryOne = new ImageView(getContext());
        categoryOne.setImageResource(R.drawable.ic_category_study_sidebar);

        notificationTitle.setText(this.list.get(position));
        notificationContent.addView(phraseOne);
        notificationCategory.addView(categoryOne);



        return rowView;
    }
}


