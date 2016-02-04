package com.voudeonibusapp.android.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.LineController;
import com.voudeonibusapp.android.enums.ETypeActivity;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLine;
import com.voudeonibusapp.android.views.base.ObservableScrollView;
import com.voudeonibusapp.android.views.components.CategorySettingLineView;
import com.voudeonibusapp.android.views.utils.CategoryUtils;

public class CategorySearchActivity extends BaseActivity {

    private AdapterSearchLine searchLineAdapter;
    private RecyclerView categorySearchList;

    private View wrapperNameCategory;
    private View wrapperEditSearch;
    private View placeholder;

    private ImageView iconHeader;
    private TextView editNameCategory;

    private EditText lineSearchEdit;

    private ObservableScrollView observableScrollView;
    private ObservableScrollView.Callbacks callbacks;

    private boolean hasCategory = false;
    private int idCategory;
    private String nameCategory;
    private int iiconHeader;

    public CategorySearchActivity() {
        super(ETypeActivity.CATEGORY_SEARCH);
        this.setAnimationOnTransition(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_category_search);

        this.title = this.getString(R.string.title_header_category);

        this.color = this.getIntent().getIntExtra("Color", 0);
        this.hasCategory = this.getIntent().getBooleanExtra("Has_Category", false);
        this.idCategory = this.getIntent().getIntExtra("Category_Id", 0);
        this.nameCategory = this.getIntent().getStringExtra("Category_Name");
        this.iiconHeader = this.getIntent().getIntExtra("Category_Icon", 100);

        reloadHeader();

        this.searchLineAdapter = new AdapterSearchLine(this, LineController.all(), AdapterSearchLine.TypeDetails.DETAILS_CATEGORY, hasCategory, idCategory, color, nameCategory, iiconHeader, true);

        settingLayoutElements();
        setListItems();
        setClickListener();
    }

    private void settingLayoutElements() {
        this.categorySearchList = (RecyclerView) this.findViewById(R.id.categorySearchList);
        final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.categorySearchList.setLayoutManager(layoutManager);

        this.iconHeader = (ImageView) findViewById(R.id.iconHeader);
        this.editNameCategory = (TextView) findViewById(R.id.editNameCategory);

        this.iconHeader.setImageResource(CategoryUtils.getIconCategory(this.iiconHeader));

        this.editNameCategory.setText(this.nameCategory);

        this.wrapperNameCategory = findViewById(R.id.wrapperNameCategory);
        this.wrapperEditSearch = findViewById(R.id.wrapperEditSearch);
        this.placeholder = findViewById(R.id.placeholder);
        this.placeholder.setBackgroundColor(this.color);
        this.lineSearchEdit = (EditText) findViewById(R.id.lineSearchEdit);

        this.observableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        this.callbacks = new ObservableScrollView.Callbacks(wrapperEditSearch, placeholder, observableScrollView);
        this.callbacks.setBaseActivity(this);
        this.callbacks.setViewHeight(wrapperNameCategory);
        this.callbacks.setTitle1(getResources().getString(R.string.title_header_category));
        this.callbacks.setTitle2(this.nameCategory);


        this.setTitle(getResources().getString(R.string.title_header_category));

    }

    private void setListItems() {
        this.categorySearchList.setAdapter(this.searchLineAdapter);
    }

    private void setClickListener() {
        lineSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CategorySearchActivity.this.searchLineAdapter = new AdapterSearchLine(CategorySearchActivity.this, lineSearchEdit.getText().toString(), AdapterSearchLine.TypeDetails.DETAILS_CATEGORY, CategorySearchActivity.this.hasCategory, CategorySearchActivity.this.idCategory, CategorySearchActivity.this.color, CategorySearchActivity.this.nameCategory, CategorySearchActivity.this.iiconHeader, true);
                setListItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CategorySettingLineView.REQUEST_TRIP && resultCode == CategorySettingLineView.RESULT_TRIP) {
            setResult(CategorySettingLineView.RESULT_TRIP, data);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
}
