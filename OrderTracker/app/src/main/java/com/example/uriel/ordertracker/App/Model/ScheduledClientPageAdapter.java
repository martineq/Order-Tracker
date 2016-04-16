package com.example.uriel.ordertracker.App.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by poly on 15/04/16.
 */
public class ScheduledClientPageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    ArrayList<Client> list;
    ArrayList<ScheduledClientFragment> fragments;
    ScheduledClientFragment fragment0;
    ScheduledClientFragment fragment1;
    ScheduledClientFragment fragment2;
    ScheduledClientFragment fragment3;

    public ScheduledClientPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

        fragments=new ArrayList<ScheduledClientFragment>();

        fragment0 = new ScheduledClientFragment();
        fragments.add(fragment0);

        fragment1 = new ScheduledClientFragment();
        fragments.add(fragment1);

        fragment2 = new ScheduledClientFragment();
        fragments.add(fragment2);

        fragment3= new ScheduledClientFragment();
        fragments.add(fragment3);
    }


    public void populateClients(final ArrayList<Client> clientList) {

        for (ScheduledClientFragment curInstance: fragments) {
            curInstance.setList(clientList);
        }

        if( clientList!=null && !clientList.isEmpty() ) list=clientList;

    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case(0):
                return fragment0;
            case(1):
                return fragment1;
            case(2):
                return fragment2;
            case(3):
                return fragment3;
            default:
                return new ScheduledClientFragment();
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
