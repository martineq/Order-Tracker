package com.example.uriel.ordertracker.App.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

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
    ScheduledClientFragment fragment4;
    ScheduledClientFragment fragment5;
    ScheduledClientFragment fragment6;

    public ScheduledClientPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

        fragments=new ArrayList<ScheduledClientFragment>();

        fragment0 = new ScheduledClientFragment();
        fragment0.setDay("Domingo",6);
        fragments.add(fragment0);

        fragment1 = new ScheduledClientFragment();
        fragment1.setDay("Lunes", 0);
        fragments.add(fragment1);

        fragment2 = new ScheduledClientFragment();
        fragment2.setDay("Martes", 1);
        fragments.add(fragment2);

        fragment3= new ScheduledClientFragment();
        fragment3.setDay("Miercoles", 2);
        fragments.add(fragment3);

        fragment4= new ScheduledClientFragment();
        fragment4.setDay("Jueves", 3);
        fragments.add(fragment4);

        fragment5= new ScheduledClientFragment();
        fragment5.setDay("Viernes", 4);
        fragments.add(fragment5);

        fragment6= new ScheduledClientFragment();
        fragment6.setDay("Sabado", 5);
        fragments.add(fragment6);
    }

    public List<Client> getList(int position){
        switch (position) {
            case(0):
                return fragment0.getList();
            case(1):
                return fragment1.getList();
            case(2):
                return fragment2.getList();
            case(3):
                return fragment3.getList();
            case(4):
                return fragment4.getList();
            case(5):
                return fragment5.getList();
            case(6):
                return fragment6.getList();
            default:
                return null;
        }
    }

    public void populateClients(final ArrayList<Client> clientList) {

        for (ScheduledClientFragment curInstance: fragments) {
            curInstance.setList(clientList);
            curInstance.populateClients();
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
            case(4):
                return fragment4;
            case(5):
                return fragment5;
            case(6):
                return fragment6;
            default:
                return new ScheduledClientFragment();
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
