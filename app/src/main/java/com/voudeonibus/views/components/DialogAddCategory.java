package com.voudeonibus.views.components;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.voudeonibus.R;
import com.voudeonibus.controller.CategoryController;
import com.voudeonibus.models.api.Category;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.adapter.list.AdapterCategoryModal;

import java.util.ArrayList;

import io.realm.RealmResults;

public class DialogAddCategory extends Dialog {

    private Context context;
    private Trip trip;

    private TextView title;
    private ListView listCategory;
    private Button btnSave;

    private TripItem tripItem;

    private AdapterCategoryModal arrayAdapter;

    public DialogAddCategory(Context context, Trip trip, TripItem tripItem) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.trip = trip;
        this.tripItem = tripItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_category);
        setLayoutElements();
        setContent();
        setClickListener();

    }

    private void setLayoutElements() {
        this.title = (TextView) this.findViewById(R.id.title);
        this.listCategory = (ListView) this.findViewById(R.id.list_category);
        this.btnSave = (Button) this.findViewById(R.id.btnSave);
    }

    private void setContent() {

        this.title.setText("Eu uso a variação " + trip.getOrigin() + " / " + trip.getDestination() +  " para:");

        RealmResults<Category> categories = CategoryController.findOthersCategoryToTrip(trip);
        arrayAdapter = new AdapterCategoryModal(getContext(), categories, trip);
        this.listCategory.setAdapter(arrayAdapter);

    }

    private void setClickListener() {

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Category> categories = DialogAddCategory.this.arrayAdapter.getCategories();

//                CategoryController.updateCategory(categories, DialogAddCategory.this.trip);

                // tripItem.setCategories();

                Toast toast = Toast.makeText(DialogAddCategory.this.getContext(), "Sucesso!", Toast.LENGTH_SHORT);
                toast.show();

                DialogAddCategory.this.dismiss();
            }
        });
    }
}
