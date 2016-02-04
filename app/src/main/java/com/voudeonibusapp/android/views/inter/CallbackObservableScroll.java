package com.voudeonibusapp.android.views.inter;

import com.voudeonibusapp.android.views.base.ObservableScrollView;

public interface CallbackObservableScroll {

    void setObservableView(ObservableScrollView observableView);
    void onUpOrCancelMotionEvent();
    void onDownMotionEvent();
    void onScrollChanged(int scrollY);
}
