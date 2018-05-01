package com.yleaf.stas.if_citypizza.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.yleaf.stas.if_citypizza.fragment.PizzaFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stas on 26.03.18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private final List<String> mFragmentTitleList = new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //adding fragments and title method
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

        notifyDataSetChanged();
    }

    public void removeFragment(int position) {
        mFragmentList.remove(position);
        mFragmentTitleList.remove(position);

        notifyDataSetChanged();
    }

    public void refreshData() {
        for(int i = 0; i < mFragmentList.size(); i++) {
            ((PizzaFragment)mFragmentList.get(i)).refreshAdapter();
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
