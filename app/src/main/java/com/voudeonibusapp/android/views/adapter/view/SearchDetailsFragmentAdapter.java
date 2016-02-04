package com.voudeonibusapp.android.views.adapter.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.voudeonibusapp.android.models.api.Line;
import com.voudeonibusapp.android.views.activity.BaseActivity;
import com.voudeonibusapp.android.views.adapter.list.AdapterSearchLine;
import com.voudeonibusapp.android.views.components.SearchDetailItemView;

import java.util.ArrayList;

public class SearchDetailsFragmentAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private Line line;
    private ArrayList<Fragment> listFragment;
    private AdapterSearchLine.TypeDetails typeDetails;
    private BaseActivity baseActivity;
    private View wrapper_content_base;

    public SearchDetailsFragmentAdapter(FragmentManager fm, Line line, AdapterSearchLine.TypeDetails typeDetails, BaseActivity baseActivity, View wrapper_content_base) {
        super(fm);
        this.fm = fm;
        this.line = line;
        this.typeDetails = typeDetails;
        this.listFragment = new ArrayList<>();
        this.baseActivity = baseActivity;
        this.wrapper_content_base = wrapper_content_base;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new SearchDetailItemView(0, line, typeDetails, baseActivity, wrapper_content_base);
                break;
            case 1:
                fragment = new SearchDetailItemView(1, line, typeDetails, baseActivity, wrapper_content_base);
                break;
            case 2:
                fragment = new SearchDetailItemView(2, line, typeDetails, baseActivity, wrapper_content_base);
                break;

        }

        listFragment.add(fragment);

        return fragment;

    }

    public Fragment getViewPosition(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
