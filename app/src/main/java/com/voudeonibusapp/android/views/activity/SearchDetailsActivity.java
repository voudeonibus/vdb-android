package com.voudeonibusapp.android.views.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.LineController;
import com.voudeonibusapp.android.enums.ETypeHeader;
import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLine;
import com.voudeonibusapp.android.views.adapter.view.SearchDetailsFragmentAdapter;
import com.voudeonibusapp.android.views.base.CustomViewPager;
import com.voudeonibusapp.android.views.base.ObservableScrollView;
import com.voudeonibusapp.android.views.base.ScrollSettleHandler;
import com.voudeonibusapp.android.views.components.SearchDetailItemView;
import com.voudeonibusapp.android.views.inter.CallbackObservableScroll;

public class SearchDetailsActivity extends BaseActivity {

    private Line line;
    private AdapterSearchLine.TypeDetails typeDetails;

    private EditText editSearch;
    private Button btnDay0;
    private Button btnDay1;
    private Button btnDay2;

    private View line_tabs;


    private int btnDay0w = 0;
    private int btnDay1w = 0;
    private int btnDay2w = 0;

    private int lineTabw = 0;

    private int lineTabp = 0;

    private CustomViewPager mPager;
    private SearchDetailsFragmentAdapter mPagerAdapter;


    private ObservableScrollView observableDetails;
    private ParallaxSearchDetails parallaxSearchDetails;
    private View placeholder;
    private View wrapper;
    private View wrapperSearch;
    private View wrapper_content_base;

    public static int HEIGHT_BAR;

    public SearchDetailsActivity() {
        super(ETypeHeader.DETAILS_LINE);
        this.title = "Todas Linhas";
        this.idLayout = R.layout.a_base_details_search;
        this.setAnimationOnTransition(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_search_details);
        this.line = LineController.findById((String) getIntent().getSerializableExtra("Line_id"));
        this.typeDetails = (AdapterSearchLine.TypeDetails) getIntent().getSerializableExtra("TypeDetails");
        setLayoutElements();
        setClickListeners();
        setLine(this.line);
    }

    private void setLayoutElements() {
        this.observableDetails = (ObservableScrollView) this.findViewById(R.id.observableDetails);
        this.placeholder = this.findViewById(R.id.placeholder);
        this.wrapper = this.findViewById(R.id.wrapper);
        this.wrapperSearch = this.findViewById(R.id.wrapperSearch);
        this.parallaxSearchDetails = new ParallaxSearchDetails(wrapper, placeholder, wrapperSearch );
        this.parallaxSearchDetails.setObservableView(this.observableDetails);
        this.wrapper_content_base = this.findViewById(R.id.wrapper_content_base);

        this.btnDay0 = (Button) this.findViewById(R.id.btnDay0);
        this.btnDay1 = (Button) this.findViewById(R.id.btnDay1);
        this.btnDay2 = (Button) this.findViewById(R.id.btnDay2);
        this.line_tabs = findViewById(R.id.line_tabs);
        this.editSearch = (EditText) this.findViewById(R.id.editSearch);
        this.mPager = (CustomViewPager) this.findViewById(R.id.pager);
        this.mPager.setPagingEnabled(false);
        this.mPagerAdapter = new SearchDetailsFragmentAdapter(getSupportFragmentManager(), line, typeDetails, this, wrapper_content_base);
        this.mPager.setAdapter(this.mPagerAdapter);

        btnDay0.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btnDay0w = btnDay0.getWidth();

                if (lineTabw == 0) {
                    lineTabw = btnDay0w;
                    ViewGroup.LayoutParams lp = line_tabs.getLayoutParams();
                    lp.width = lineTabw;
                    line_tabs.setLayoutParams(lp);
                    line_tabs.setX(btnDay0.getX());

                    btnDay0.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }
        });

        btnDay1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btnDay1w = btnDay1.getWidth();
            }
        });

        btnDay2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btnDay2w = btnDay2.getWidth();
            }
        });

        HEIGHT_BAR = wrapperSearch.getLayoutParams().height;

    }

    private void searchOnCurrentFragment(String name) {
        SearchDetailItemView view = (SearchDetailItemView) mPagerAdapter.getViewPosition(mPager.getCurrentItem());
        view.searchTrip(name);
    }

    private void setClickListeners() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchOnCurrentFragment(editSearch.getText().toString());
            }
        });

        btnDay0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide(0);
                mPager.setCurrentItem(0);
            }
        });
        btnDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide(1);
                mPager.setCurrentItem(1);
            }
        });
        btnDay2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                slide(2);
                mPager.setCurrentItem(2);
            }
        });
    }

    public void slide(final int bt) {


            if (bt == lineTabp) {
                return;
            }

            Animation slide = null;
            float pStart = 0.0f;
            float pTo = 0.0f;


        ViewGroup.LayoutParams lp = line_tabs.getLayoutParams();

        switch (bt) {
            case 0:
                lp.width = btnDay0w;
                break;
            case 1:
                lp.width = btnDay1w;
                break;
            case 2:
                lp.width = btnDay2w;
                break;
        }

        line_tabs.setLayoutParams(lp);

            switch (bt) {
                case 0:

                    if (lineTabp == 1)  {
                        pStart = 0.3f;
                        btnDay1.setTypeface(Typeface.DEFAULT);
                    }

                    if (lineTabp == 2) {
                        pStart = 0.62f;
                        btnDay2.setTypeface(Typeface.DEFAULT);
                    }

                    pTo = 0.0f;
                    btnDay0.setTypeface(Typeface.DEFAULT_BOLD);
                    break;
                case 1:

                    if (lineTabp == 0)  {
                        pStart = 0.0f;
                        btnDay0.setTypeface(Typeface.DEFAULT);
                    }

                    if (lineTabp == 2) {
                        pStart = 0.62f;
                        btnDay2.setTypeface(Typeface.DEFAULT);
                    }

                    pTo = 0.3f;
                    btnDay1.setTypeface(Typeface.DEFAULT_BOLD);

                    break;
                case 2:

                    if (lineTabp == 0)  {
                        pStart = 0.0f;
                        btnDay0.setTypeface(Typeface.DEFAULT);

                    }

                    if (lineTabp == 1) {
                        pStart = 0.3f;
                        btnDay1.setTypeface(Typeface.DEFAULT);
                    }

                    pTo = 0.62f;
                    btnDay2.setTypeface(Typeface.DEFAULT_BOLD);

                    break;
            }

            slide = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, pStart,
                    Animation.RELATIVE_TO_PARENT, pTo, Animation.RELATIVE_TO_PARENT,
                    0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

            slide.setDuration(300);
            slide.setFillAfter(true);
            slide.setFillEnabled(true);
            line_tabs.startAnimation(slide);

            slide.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {


                }

                @Override
                public void onAnimationEnd(Animation animation) {


                }

            });

        lineTabp = bt;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    class ParallaxSearchDetails implements CallbackObservableScroll {

        private ScrollSettleHandler scrollSettleHandler;
        private int mMinRawY = 0;
        private int mState;
        private int mQuickReturnHeight;
        private int mMaxScrollY;

        private ObservableScrollView observableScrollView;

        private View viewMain;
        private View viewPlaceholder;
        private View contentSwipe;

        private BaseActivity baseActivity;
        private View viewHeight;
        int scrollCurrent = 0;

        int targetHeight;


        public ParallaxSearchDetails(View viewMain, View viewPlaceholder, View contentSwipe) {
            this.scrollSettleHandler = new ScrollSettleHandler(viewMain, viewPlaceholder);
            mState = this.scrollSettleHandler.STATE_ONSCREEN;
            this.viewMain = viewMain;
            this.viewPlaceholder = viewPlaceholder;
            this.contentSwipe = contentSwipe;
        }



        @Override
        public void onScrollChanged(int scrollY) {
            scrollY = Math.min(mMaxScrollY, scrollY);


            scrollSettleHandler.onScroll(scrollY);

            int rawY = (viewPlaceholder.getBottom() - contentSwipe.getHeight() - ((ViewGroup.MarginLayoutParams) contentSwipe.getLayoutParams()).bottomMargin - ((ViewGroup.MarginLayoutParams) contentSwipe.getLayoutParams()).topMargin) - scrollY;
            int translationY = 0;

            switch (mState) {
                case ScrollSettleHandler.STATE_OFFSCREEN:
                    if (rawY <= mMinRawY) {
                        mMinRawY = rawY;
                    } else {
                        mState = ScrollSettleHandler.STATE_RETURNING;
                    }
                    translationY = rawY;
                    break;

                case ScrollSettleHandler.STATE_ONSCREEN:
                    if (rawY < -mQuickReturnHeight) {
                        mState = ScrollSettleHandler.STATE_OFFSCREEN;
                        mMinRawY = rawY;
                    }
                    translationY = rawY;
                    break;

                case ScrollSettleHandler.STATE_RETURNING:
                    translationY = (rawY - mMinRawY) - mQuickReturnHeight;
                    if (translationY > 0) {
                        translationY = 0;
                        mMinRawY = rawY - mQuickReturnHeight;
                    }

                    if (rawY > 0) {
                        mState = ScrollSettleHandler.STATE_ONSCREEN;
                        translationY = rawY;
                    }

                    if (translationY < -mQuickReturnHeight) {
                        mState = ScrollSettleHandler.STATE_OFFSCREEN;
                        mMinRawY = rawY;
                    }
                    break;
            }

            scrollSettleHandler.setMState(mState);
            scrollSettleHandler.setMMinRawY(mMinRawY);
            viewMain.animate().cancel();
            contentSwipe.animate().cancel();

            if (targetHeight == 0) targetHeight = viewMain.getLayoutParams().height;
            if (scrollY == 0) {
                viewMain.getLayoutParams().height = targetHeight;
                viewMain.requestLayout();
                viewMain.setTranslationY(scrollY);
                contentSwipe.setTranslationY(scrollY + targetHeight);
            } else if (scrollY < targetHeight && scrollY < 712) {
                viewMain.getLayoutParams().height = targetHeight - scrollY;
                viewMain.requestLayout();
                viewMain.setTranslationY(scrollY);

            } else {
                viewMain.getLayoutParams().height = targetHeight - 712;
                viewMain.requestLayout();
                viewMain.setTranslationY(scrollY);
            }

            if (scrollY > 0 && scrollY < targetHeight && scrollY < 712) {
                contentSwipe.setTranslationY(scrollY + viewMain.getLayoutParams().height);
            }

            if (scrollY > 0 && scrollY < scrollCurrent) {
                contentSwipe.setTranslationY(scrollY + viewMain.getLayoutParams().height + translationY);
            }

            scrollCurrent = scrollY;
        }

        @Override
        public void onDownMotionEvent() {
            scrollSettleHandler.setSettleEnabled(false);
        }

        @Override
        public void onUpOrCancelMotionEvent() {
            scrollSettleHandler.setSettleEnabled(true);
            scrollSettleHandler.onScroll(observableScrollView.getScrollY());
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
                            mMaxScrollY = observableScrollView.computeVerticalScrollRange()
                                    - observableScrollView.getHeight();
                            mQuickReturnHeight = contentSwipe.getHeight();
                            scrollSettleHandler.setMQuickReturnHeight(mQuickReturnHeight);
                        }
                    });
        }
    }
}
