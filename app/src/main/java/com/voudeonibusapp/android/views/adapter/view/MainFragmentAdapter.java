package com.voudeonibusapp.android.views.adapter.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.voudeonibusapp.android.views.components.TodasLinhasView;
import com.voudeonibusapp.android.views.components.VouAgoraView;

import java.util.ArrayList;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        if (position == 0) {
            fragment = new VouAgoraView();
        } else {
            fragment = new TodasLinhasView();
        }

        fragments.add(fragment);

        return fragment;

    }

    @Override
    public int getCount() {
        return 2;
    }

}
