package com.voudeonibus.views.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.voudeonibus.R;
import com.voudeonibus.event.MessageEvent;
import com.voudeonibus.views.adapter.view.MainFragmentAdapter;
import com.voudeonibus.views.base.CustomViewPager;
import com.voudeonibus.views.components.HeaderMainView;
import com.voudeonibus.views.utils.ScreenUtils;

public class MainActivity extends BaseActivity {

    private CustomViewPager mPager;
    private MainFragmentAdapter mPagerAdapter;


    private HeaderMainView headerMainView;

    private View headerMain;
    private View viewPlaceholder;

    public MainActivity() {
        super(true);
        this.showTitleImg = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.a_main);

        Answers.getInstance().logCustom(new CustomEvent("Detecte density")
        .putCustomAttribute("typeDensity", ScreenUtils.getDensity(this)));

        mPager = (CustomViewPager) findViewById(R.id.pager);
        mPager.setPagingEnabled(false);
        mPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        headerMainView = new HeaderMainView(this);

        headerMainView.setClickTodasLinhas(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                headerMainView.SlideToRight();
                mPager.setCurrentItem(1);
            }

        });

        headerMainView.setClickVouAgora(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                headerMainView.SlideToLeft();
                mPager.setCurrentItem(0);
            }

        });

    }


    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onEvent(MessageEvent event) {

        super.onEvent(event);

        if (event == MessageEvent.SWITCH_ALL_LINES) {
            headerMainView.SlideToRight();
            mPager.setCurrentItem(1);
        }
    }



}
