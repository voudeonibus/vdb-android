package com.voudeonibus.views.inter;

import com.voudeonibus.views.base.ObservableScrollView;

public interface CallbackObservableScroll {

    void setObservableView(ObservableScrollView observableView);
    void onUpOrCancelMotionEvent();
    void onDownMotionEvent();
    void onScrollChanged(int scrollY);
}
