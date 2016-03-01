package com.voudeonibus.views.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {

    public static void slideToRight(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // To animate view slide out from right to left
    public static void slideToLeft(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from bottom to top
    public static void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    private static ValueAnimator slideAnimator(int start, int end, final View view) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });

        return animator;
    }

    private static void collapse(final View view) {
        int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, view);


        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();

    }

    public static void expandCollapse(final View view) {
        if (view.getVisibility()==View.GONE){
            expand(view);
        }else{
            collapse(view);
        }
    }

    private static void expand(final View view) {

        //set Visible
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, view.getMeasuredHeight(), view);
        mAnimator.start();


//        final int targetHeight = view.getMeasuredHeight();
//
//        Animation a = new Animation() {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//
//                view.getLayoutParams().height = interpolatedTime == 1
//                        ? ViewGroup.LayoutParams.WRAP_CONTENT
//                        : (int)(targetHeight * interpolatedTime);
//                view.requestLayout();
//            }
//
//            @Override
//            public boolean willChangeBounds() {
//                return super.willChangeBounds();
//            }
//        };
//
//        a.setDuration(500);
//        view.startAnimation(a);
    }

}
