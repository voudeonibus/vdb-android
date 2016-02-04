package com.voudeonibusapp.android.views.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.LineController;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.models.api.Trip;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLineCategoryDetails;
import com.voudeonibusapp.android.views.base.ObservableScrollView;
import com.voudeonibusapp.android.views.utils.CategoryUtils;

import io.realm.RealmList;

public class CategorySearchDetailsActivity extends BaseActivity {


    private String nameCategory;
    private int iiconHeader;
    private boolean hasCategory;
    private int idCategory;
    private String idLine;
    private Line line;

    private RecyclerView listTrips;
    private AdapterSearchLineCategoryDetails adapterSearchLineCategoryDetails;
    private View placeholder;
    private ImageView iconHeader;
    private TextView textNameCategory;

    private ObservableScrollView observableScrollView;
    private CategoryActivity.Callbacks callbacks;


    public CategorySearchDetailsActivity() {
        super();
        this.setAnimationOnTransition(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_category_search_details);


        this.color = this.getIntent().getIntExtra("Color", 0);
        this.hasCategory = this.getIntent().getBooleanExtra("Has_Category", false);
        this.idCategory = this.getIntent().getIntExtra("Category_Id", idCategory);
        this.idLine = this.getIntent().getStringExtra("Line_id");
        this.nameCategory = this.getIntent().getStringExtra("Category_Name");
        this.iiconHeader = this.getIntent().getIntExtra("Category_Icon", 100);

        this.line = LineController.findById(idLine);

        reloadHeader();

        setLayoutElements();
    }

    private void setLayoutElements() {



        listTrips = (RecyclerView) findViewById(R.id.listTrips);
        this.placeholder = findViewById(R.id.placeholder);
        this.iconHeader = (ImageView) findViewById(R.id.iconHeader);
        this.textNameCategory = (TextView) findViewById(R.id.textNameCategory);
        final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.listTrips.setLayoutManager(layoutManager);

        RealmList<Trip> trips = new RealmList<>();

        trips.addAll(line.getTrips());

        this.adapterSearchLineCategoryDetails = new AdapterSearchLineCategoryDetails(this, trips, hasCategory, idCategory);
        this.listTrips.setAdapter(adapterSearchLineCategoryDetails);

        this.placeholder.setBackgroundColor(color);
        this.iconHeader.setImageResource(CategoryUtils.getIconCategory(this.iiconHeader));
        this.textNameCategory.setText(this.nameCategory);

        this.observableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);

        this.callbacks = new CategoryActivity.Callbacks(this, observableScrollView, textNameCategory, getResources().getString(R.string.title_header_category), this.nameCategory);
        this.observableScrollView.setCallbacks(this.callbacks);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
}
