package com.voudeonibus.views.base;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class ScrollSettleHandler extends Handler {

    public static final int STATE_ONSCREEN = 0;
    public static final int STATE_OFFSCREEN = 1;
    public static final int STATE_RETURNING = 2;

    public static final int SETTLE_DELAY_MILLIS = 100;

    private int mSettledScrollY = Integer.MIN_VALUE;
    private boolean mSettleEnabled;

    private int mMinRawY;
    private int mState;
    private int mQuickReturnHeight;

    private View viewMain;
    private View viewPlaceholder;



    public ScrollSettleHandler(View viewMain, View viewPlaceholder) {
        this.viewMain = viewMain;
        this.viewPlaceholder = viewPlaceholder;
    }

    public void onScroll(int scrollY) {
        if (mSettledScrollY != scrollY) {
            // Clear any pending messages and post delayed
            removeMessages(0);
            sendEmptyMessageDelayed(0, SETTLE_DELAY_MILLIS);
            mSettledScrollY = scrollY;
        }
    }

    public void setSettleEnabled(boolean settleEnabled) {
        mSettleEnabled = settleEnabled;
    }

    @Override
    public void handleMessage(Message msg) {
        // Handle the scroll settling.
        if (STATE_RETURNING == mState && mSettleEnabled) {
            int mDestTranslationY;
            if (mSettledScrollY - viewMain.getTranslationY() > mQuickReturnHeight / 2) {
                mState = STATE_OFFSCREEN;
                mDestTranslationY = Math.max(
                        mSettledScrollY - mQuickReturnHeight,
                        viewPlaceholder.getTop());
            } else {
                mDestTranslationY = mSettledScrollY;
            }

            mMinRawY = viewPlaceholder.getTop() - mQuickReturnHeight - mDestTranslationY;
            viewMain.animate().translationY(mDestTranslationY);
        }
        mSettledScrollY = Integer.MIN_VALUE; // reset
    }

    public void setMState(int mState) {
        this.mState = mState;
    }

    public void setMQuickReturnHeight(int mQuickReturnHeight) {
        this.mQuickReturnHeight = mQuickReturnHeight;
    }

    public void setMMinRawY(int mMinRawY) {
        this.mMinRawY = mMinRawY;
    }

}