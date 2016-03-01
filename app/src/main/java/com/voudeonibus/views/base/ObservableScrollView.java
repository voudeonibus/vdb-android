package com.voudeonibus.views.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.voudeonibus.views.activity.BaseActivity;
import com.voudeonibus.views.inter.CallbackObservableScroll;

public class ObservableScrollView extends ScrollView {

    private CallbackObservableScroll mCallbacks;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(t);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mCallbacks.onDownMotionEvent();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mCallbacks.onUpOrCancelMotionEvent();
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(CallbackObservableScroll listener) {
        mCallbacks = listener;
    }

    public static class Callbacks implements CallbackObservableScroll {

        private ScrollSettleHandler scrollSettleHandler;
        private int mMinRawY = 0;
        private int mState;
        private int mQuickReturnHeight;
        private int mMaxScrollY;

        private ObservableScrollView observableScrollView;

        private View viewMain;
        private View viewPlaceholder;

        private BaseActivity baseActivity;
        private View viewHeight;

        private String title1;
        private String title2;


        public Callbacks(View viewMain, View viewPlaceholder) {
            this.scrollSettleHandler = new ScrollSettleHandler(viewMain, viewPlaceholder);
            mState = this.scrollSettleHandler.STATE_ONSCREEN;
            this.viewMain = viewMain;
            this.viewPlaceholder = viewPlaceholder;
        }

        public Callbacks(View viewMain, View viewPlaceholder, ObservableScrollView observableScrollView) {
            this(viewMain, viewPlaceholder);
            this.setObservableView(observableScrollView);
        }


        @Override
        public void onScrollChanged(int scrollY) {
            scrollY = Math.min(mMaxScrollY, scrollY);

            scrollSettleHandler.onScroll(scrollY);

            int rawY = (viewPlaceholder.getBottom() - viewMain.getHeight() - ((MarginLayoutParams) viewMain.getLayoutParams()).bottomMargin - ((MarginLayoutParams) viewMain.getLayoutParams()).topMargin) - scrollY;
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
            viewMain.setTranslationY(translationY + scrollY);


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
                            mQuickReturnHeight = viewMain.getHeight();
                            scrollSettleHandler.setMQuickReturnHeight(mQuickReturnHeight);
                        }
                    });
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public View getViewHeight() {
            return viewHeight;
        }

        public void setViewHeight(View viewHeight) {
            this.viewHeight = viewHeight;
        }

        public BaseActivity getBaseActivity() {
            return baseActivity;
        }

        public void setBaseActivity(BaseActivity baseActivity) {
            this.baseActivity = baseActivity;
        }
    }

}
