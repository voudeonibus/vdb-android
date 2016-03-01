package com.voudeonibus.views.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.voudeonibus.models.api.Direction;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.cards.lines.CardTripDeparture;

import io.realm.RealmList;

public class AdapterCardTripDeparture extends ArrayAdapter<Trip> {

    private Context context;
    private RealmList<Direction> list;
    private RealmList<Direction> listAux;
    private RealmList<Direction> listIda;
    private RealmList<Direction> listVolta;
    private int category_day;

    private boolean withoutCategoryDay;


    public AdapterCardTripDeparture(Context context, RealmList<Direction> list, int category_day) {
        super(context, -1);

        this.context = context;
        this.listAux = new RealmList<>();
        this.listAux.addAll(list.where().equalTo("departures.category_day", category_day).findAll());

        ordenation(this.listAux);

        this.category_day = category_day;
        this.withoutCategoryDay = false;
    }

    public AdapterCardTripDeparture(Context context, RealmList<Direction> list) {
        super(context, -1);

        this.context = context;
        this.list.addAll(list.where().findAll());
        this.withoutCategoryDay = true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardTripDeparture cardTripDeparture = new CardTripDeparture(this.context, parent, list.get(position), withoutCategoryDay);

        return cardTripDeparture.getView();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    /**
     * Ordenação da list Direction
     * @param list
     */
    private void ordenation(RealmList<Direction> list) {
        this.list = new RealmList<>();
        this.listIda = new RealmList<>();
        this.listVolta = new RealmList<>();

        if (!list.isEmpty()) {
            int countList = list.size();
            for (int i = 0; i < countList; i++) {
                Direction element = list.get(i);
                int direction = element.getDirection();
                if (direction == 1) {
                    this.listIda.add(element);
                } else if(direction == 0) {
                    this.listVolta.add(element);
                }
            }

            int idaCount = this.listIda.size();
            int voltaCount = this.listVolta.size();

            int auxCount = idaCount > voltaCount ? idaCount : voltaCount;

            for (int i = 0; i < auxCount; i++) {
                if (i < voltaCount) {
                    this.list.add(this.listVolta.get(i));
                }
                if (i < idaCount) {
                    this.list.add(this.listIda.get(i));
                }
            }
        }
    }
}