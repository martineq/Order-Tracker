package com.example.uriel.ordertracker.App.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by poly on 15/04/16.
 */
public class ScheduledClientPageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public ScheduledClientPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ScheduledClientFragment tab1 = new ScheduledClientFragment();
                return tab1;
            default:
                ScheduledClientFragment tab2 = new ScheduledClientFragment();
                return tab2;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
