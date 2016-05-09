package com.recipeapp.plip.plipapp.viewcontroller.Swiping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by SugarPalaces on 5/1/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    public static int pos = 0;

    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }


    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        PagerAdapter.pos = pos;
    }

}