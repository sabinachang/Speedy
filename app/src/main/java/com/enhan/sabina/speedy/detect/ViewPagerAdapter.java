package com.enhan.sabina.speedy.detect;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String mTabTitles[] = new String[] {"Result","Words"};
    private List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return mFragmentList.get(0);
            case 1:
                return mFragmentList.get(1);
            default:
                return mFragmentList.get(0);
            }
    }

    @Override
    public int getCount() {
            return 2;
        }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }

    public void addToFragmentList(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public void updateTabTitle(int count) {
        mTabTitles[1] =  "Words (" + count + ")";

    }
}
