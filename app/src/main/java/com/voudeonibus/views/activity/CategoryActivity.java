package com.voudeonibus.views.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerSwatch;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.voudeonibus.R;
import com.voudeonibus.controller.CategoryController;
import com.voudeonibus.controller.ColorsCategoryController;
import com.voudeonibus.enums.ETypeActivity;
import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.models.api.Category;
import com.voudeonibus.models.aux.ColorsCategory;
import com.voudeonibus.views.base.ObservableScrollView;
import com.voudeonibus.views.components.CategorySettingDayView;
import com.voudeonibus.views.components.CategorySettingLineView;
import com.voudeonibus.views.components.CategorySettingTimeView;
import com.voudeonibus.views.components.HeaderBase;
import com.voudeonibus.views.inter.CallbackObservableScroll;
import com.voudeonibus.views.utils.ColorUtils;
import com.voudeonibus.views.utils.ModalUtils;

import de.greenrobot.event.EventBus;

public class CategoryActivity extends BaseActivity {

    public static int REQUEST_CODE_FIRST_CONFIG = 10;
    public static int RESULT_CODE_FIRST_CONFIG = 10;

    private Category categoryExist;
    private ColorsCategory colorsCategoryChoosed;

    private CategorySettingLineView fragment_line;
    private CategorySettingTimeView fragment_time;
    private CategorySettingDayView fragment_day;

    private View wrapperIcEdit;
    private View headerCategory;
    private View wrapperColorChoose;
    private View wrapperBtnAddCategory;
    private View wrapperBtnAddCategoryFirst;
    private View wrapperBtnRemoveCategory;

    private View wrapperEditNameCatory;

    private Button btnAdd;
    private EditText editNameCategory;
    private Button btnColoChoose;
    private Button btnAddFirst;
    private Button btnRemoveCategory;

    private ImageView iconHeader;

    private Callbacks callbacks;

    private ObservableScrollView observableScrollView;

    private boolean HAS_CATEGORY;
    private int CATEGORY_ID;
    private int IS_FIRST;
    private boolean HAS_IS_FIRST;

    public CategoryActivity() {
        super(ETypeActivity.CATEGORY);
        this.isCategory = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_category);

        this.setClickCustomBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryActivity.this.onBackPressed();
            }
        });

        loadVariables();
        setLayoutElements();
        setClickListener();
        loadCategory();

        setLabelBackButton(getResources().getString(R.string.label_save));


//        if (!DatabaseController.isShowTutorial()) {
//            EventBus.getDefault().post(MessageEvent.SHOW_CATEGORY_TUTORIAL);
//            DatabaseController.saveShowTutorial();
//        }
    }

    private void loadVariables() {
        this.HAS_CATEGORY = getIntent().hasExtra("Category_id");
        this.CATEGORY_ID = getIntent().getIntExtra("Category_id", 0);
        this.HAS_IS_FIRST = getIntent().hasExtra("Is_first");
        this.IS_FIRST = getIntent().getIntExtra("Is_first", 100);
    }

    private void loadCategory() {
        this.title = getString(R.string.title_header_category);
        if (HAS_CATEGORY) {
            this.categoryExist = CategoryController.findCategoryById(CATEGORY_ID);

            this.color = Color.parseColor(this.categoryExist.getColor().getColor());

            this.colorsCategoryChoosed =  this.categoryExist.getColor();

            this.reloadHeader();

            this.fragment_line.setTrips(this.categoryExist.getLines());
            this.fragment_line.setColor(this.color);
            this.fragment_line.setHasCategory(true);
            this.fragment_line.setIdCategory(CATEGORY_ID);
            this.fragment_line.setNameCategory(this.categoryExist.getName());
            this.fragment_line.setIconHeader(this.categoryExist.getIcon());

            this.fragment_time.setHours(this.categoryExist.getHours());
            this.fragment_day.setDays(this.categoryExist.getDays());

            if (HAS_IS_FIRST || (this.categoryExist.getLines().size() == 0 && this.categoryExist.getHours().size() == 0 && this.categoryExist.getDays().size() == 0)) {
                this.fragment_day.setIsFirst(true);
            }

            this.editNameCategory.setText(this.categoryExist.getName());
            this.callbacks.setTitle2(this.categoryExist.getName());
            this.wrapperBtnAddCategory.setVisibility(View.GONE);

            if (this.categoryExist.isDefault()) {
                this.btnColoChoose.setVisibility(View.GONE);
                this.editNameCategory.setEnabled(false);
                this.wrapperIcEdit.setVisibility(View.GONE);
                this.wrapperColorChoose.setVisibility(View.GONE);
                this.wrapperBtnRemoveCategory.setVisibility(View.GONE);

                switch (this.categoryExist.getIcon()) {
                    case 0:
                        this.iconHeader.setImageResource(R.drawable.ic_category_work);
                        break;
                    case 1:
                        this.iconHeader.setImageResource(R.drawable.ic_category_study);
                        break;
                }

            } else {
                this.iconHeader.setImageResource(R.drawable.ic_category_star);
            }

            headerCategory.setBackgroundColor(Color.parseColor(this.categoryExist.getColor().getColor()));
        } else {
            ColorsCategory colorsCategory = ColorsCategoryController.findFirstColorsNotUsed();
            headerCategory.setBackgroundColor(Color.parseColor(colorsCategory.getColor()));
            this.color = Color.parseColor(colorsCategory.getColor());
            this.fragment_line.setColor(this.color);
            this.fragment_line.setIconHeader(100);
            this.fragment_day.setIsFirst(true);

            this.callbacks.setTitle2(getResources().getString(R.string.title_header_category));


            this.iconHeader.setImageResource(R.drawable.ic_category_star);
            colorsCategoryChoosed = colorsCategory;

            this.wrapperBtnRemoveCategory.setVisibility(View.GONE);

            this.reloadHeader();
        }

        if (HAS_IS_FIRST) {
            this.wrapperBtnAddCategory.setVisibility(View.GONE);

            if (IS_FIRST == 0 || IS_FIRST == 1) {
                this.btnAddFirst.setText(getString(R.string.btn_add_ready));
            }

        } else {
            this.wrapperBtnAddCategoryFirst.setVisibility(View.GONE);
        }
    }

    private void setLayoutElements() {

        this.wrapperEditNameCatory = findViewById(R.id.wrapperEditNameCatory);

        this.observableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);

        this.callbacks = new Callbacks(this, observableScrollView, wrapperEditNameCatory, getResources().getString(R.string.title_header_category), "");
        this.observableScrollView.setCallbacks(this.callbacks);

        this.fragment_line = (CategorySettingLineView) getFragmentManager().findFragmentById(R.id.fragment_line);
        this.fragment_time = (CategorySettingTimeView) getSupportFragmentManager().findFragmentById(R.id.fragment_time);
        this.fragment_day = (CategorySettingDayView) getSupportFragmentManager().findFragmentById(R.id.fragment_day);

        this.wrapperColorChoose = findViewById(R.id.wrapperColorChoose);
        this.wrapperIcEdit = findViewById(R.id.wrapperIcEdit);
        this.headerCategory = findViewById(R.id.headerCategory);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.editNameCategory = (EditText) findViewById(R.id.editNameCategory);
        this.btnColoChoose = (Button) findViewById(R.id.btnColoChoose);
        this.btnAddFirst = (Button) findViewById(R.id.btnAddFirst);
        this.btnRemoveCategory = (Button) findViewById(R.id.btnRemoveCategory);
        this.iconHeader = (ImageView) findViewById(R.id.iconHeader);

        this.wrapperBtnRemoveCategory = findViewById(R.id.wrapperBtnRemoveCategory);
        this.wrapperBtnAddCategory = findViewById(R.id.wrapperBtnAddCategory);
        this.wrapperBtnAddCategoryFirst = findViewById(R.id.wrapperBtnAddCategoryFirst);


        this.setTitle(getResources().getString(R.string.title_header_category));
    }

    private void setClickListener() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveCategory()) {

                    Intent i = new Intent();

                    CategoryActivity.this.setResult(CategoryActivity.RESULT_CODE_FIRST_CONFIG, i);

                    CategoryActivity.this.finish();
                }
            }
        });

        btnColoChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorUtils.showDialog(CategoryActivity.this, CategoryActivity.this.getFragmentManager(), new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        colorsCategoryChoosed = ColorsCategoryController.findByColor(color);
                        headerCategory.setBackgroundColor(Color.parseColor(colorsCategoryChoosed.getColor()));
                        CategoryActivity.this.color = Color.parseColor(colorsCategoryChoosed.getColor());
                        fragment_line.setColor(CategoryActivity.this.color);

                        CategoryActivity.this.reloadHeader();
                    }
                });
            }
        });

        btnAddFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (IS_FIRST == 0 || IS_FIRST == 1) {
                    if (saveCategory()) {

                        if (CategoryActivity.this.getIntent().hasExtra("requestCode")) {
                            CategoryActivity.this.setResult(RESULT_CODE_FIRST_CONFIG, new Intent());
                        }

                        CategoryActivity.this.finish();
                    }

                } else {

                    Intent intent = new Intent(CategoryActivity.this.getBaseContext(), CategoryActivity.class);
                    intent.putExtra("Category_id", 1);
                    intent.putExtra("Is_first", 1);
                    intent.putExtra("requestCode", REQUEST_CODE_FIRST_CONFIG);
                    CategoryActivity.this.startActivityForResult(intent, REQUEST_CODE_FIRST_CONFIG);
                }
            }
        });

        btnRemoveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalUtils.confirmation(
                        CategoryActivity.this,
                        CategoryActivity.this.getString(R.string.messagem_remove_category),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCategory();
                    }
                });
            }
        });


        editNameCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    CategoryActivity.this.fragment_line.setNameCategory(editable.toString().trim());
                    CategoryActivity.this.callbacks.setTitle2(editable.toString().trim());
                } else {
                    CategoryActivity.this.fragment_line.setNameCategory(CategoryActivity.this.getResources().getString(R.string.title_header_without_name));
                    CategoryActivity.this.callbacks.setTitle2(CategoryActivity.this.getResources().getString(R.string.title_header_without_name));
                }
            }
        });
    }

    private void removeCategory() {

        if (HAS_CATEGORY) {
            CategoryController.removeCategory(this.categoryExist);
            Toast toast = Toast.makeText(this, this.getString(R.string.toast_category_removed), Toast.LENGTH_SHORT);
            toast.show();
            EventBus.getDefault().post(MessageEvent.UPDATE_CATEGORY);
            EventBus.getDefault().post(MessageEvent.UPDATE_VOU_AGORA);
            finish();
        }

    }

    private boolean saveCategory() {

        boolean returns = true;

        if (this.editNameCategory.getText().toString().trim().isEmpty()) {
            Toast toast = Toast.makeText(this, this.getString(R.string.toast_necessary_name_category), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if (fragment_line.getLines().size() == 0 && (!HAS_CATEGORY || (HAS_CATEGORY && !this.categoryExist.isDefault()))) {
            Toast toast = Toast.makeText(this, this.getString(R.string.toast_necessary_line_category), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if (fragment_time.getHours().size() == 0 && (!HAS_CATEGORY || (HAS_CATEGORY && !this.categoryExist.isDefault()))) {
            Toast toast = Toast.makeText(this, this.getString(R.string.toast_necessary_time_category), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if (fragment_day.getDays().size() == 0 && (!HAS_CATEGORY || (HAS_CATEGORY && !this.categoryExist.isDefault()))) {
            Toast toast = Toast.makeText(this, this.getString(R.string.toast_necessary_days_category), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        EventBus.getDefault().post(MessageEvent.OPEN_LOADING);

        if (HAS_CATEGORY) {

            CategoryController.updateCategory(this.editNameCategory.getText().toString(), this.colorsCategoryChoosed, this.categoryExist, fragment_line.getLines(), fragment_time.getHours(), fragment_day.getDays());

            Answers.getInstance().logCustom(new CustomEvent("Edit a category"));


        } else {

            Category category = new Category(fragment_line.getLines(), fragment_time.getHours(), fragment_day.getDays());
            category.setName(this.editNameCategory.getText().toString());

            if (colorsCategoryChoosed != null) {
                category.setColor(colorsCategoryChoosed);
            }

            CategoryController.addCategory(category);

            Answers.getInstance().logCustom(new CustomEvent("Create new category")
            .putCustomAttribute("name", category.getName()));


        }


        EventBus.getDefault().post(MessageEvent.UPDATE_CATEGORY);
        EventBus.getDefault().post(MessageEvent.CLOSE_LOADING);

        return returns;
    }

    @Override
    public void onBackPressed() {

        if (HeaderBase.NOTIFICATION_IS_OPEN) {
            EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION);
        } else {
            if (HAS_CATEGORY && !HAS_IS_FIRST) {
                if (saveCategory()) {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment_line.onActivityResult(requestCode, resultCode, data);
        fragment_time.onActivityResult(requestCode, resultCode, data);
        fragment_day.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CategoryActivity.REQUEST_CODE_FIRST_CONFIG &&
                resultCode == CategoryActivity.RESULT_CODE_FIRST_CONFIG &&
                saveCategory()) {

            this.finish();

        }
    }

    public static class Callbacks implements CallbackObservableScroll {

        private int mMinRawY = 0;
        private int mState;
        private int mQuickReturnHeight;
        private int mMaxScrollY;

        private ObservableScrollView observableScrollView;
        private BaseActivity baseActivity;
        private View viewHeight;

        private String title1;
        private String title2;

        public Callbacks(BaseActivity baseActivity, ObservableScrollView observableScrollView, View viewHeight, String title1, String title2) {
            this.baseActivity = baseActivity;
            this.viewHeight = viewHeight;
            this.title1 = title1;
            this.title2 = title2;
            this.setObservableView(observableScrollView);
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        @Override
        public void onScrollChanged(int scrollY) {
            scrollY = Math.min(mMaxScrollY, scrollY);

            try {

                if (scrollY > this.viewHeight.getHeight()) {
                    baseActivity.setTitle(this.title2);
                } else {
                    baseActivity.setTitle(this.title1);
                }

            } catch (Exception e) {

            }
        }

        @Override
        public void onDownMotionEvent() {

        }

        @Override
        public void onUpOrCancelMotionEvent() {

        }

        @Override
        public void setObservableView(ObservableScrollView observableView) {
            this.observableScrollView = observableView;
            observableScrollView.setCallbacks(this);
            observableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            onScrollChanged(observableScrollView.getScrollY());
                            mMaxScrollY = observableScrollView.computeVerticalScrollRange();
                        }
                    });
        }
    }

}
