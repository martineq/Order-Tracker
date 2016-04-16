package com.example.uriel.ordertracker.App.Model;

/**
 * Created by poly on 15/04/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uriel.ordertracker.R;

public class ScheduledClientFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scheduled_client_tab_fragment, container, false);
    }
}