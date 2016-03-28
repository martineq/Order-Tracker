package com.example.uriel.ordertracker.App.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.ClientsAdapter;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Services.Impl.ClientService;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DiaryActivity extends DrawerActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Actividad diaria");

        Bundle args = getIntent().getExtras();
        userId = args.getInt("userId");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), userId);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        configDrawerAfterCreate(savedInstanceState);
    }

    public static class DiaryFragment extends Fragment {

        public DiaryFragment() {
        }

        public static DiaryFragment newInstance(int sellerId) {
            DiaryFragment fragment = new DiaryFragment();
            Bundle args = new Bundle();
            args.putInt("userId", sellerId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_diary, container, false);

            return rootView;
        }
    }

    public static class OffRouteFragment extends Fragment {
        private IClientService clientService;
        private ArrayList<Client> clients;

        public OffRouteFragment() {
        }

        public static OffRouteFragment newInstance(int sellerId) {
            OffRouteFragment fragment = new OffRouteFragment();
            Bundle args = new Bundle();
            args.putInt("userId", sellerId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_off_route, container, false);

            clientService = new ClientService();
            final int userId = getArguments().getInt("userId");
            clients = clientService.getBySeller(userId);
            final ListView listView = (ListView) rootView.findViewById(R.id.listView);
            ClientsAdapter clientsAdapter = new ClientsAdapter(getActivity(), clients);
            listView.setAdapter(clientsAdapter);

            final Activity context = getActivity();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Client client = clients.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("clientId", client.getId());
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int sellerId;

        public SectionsPagerAdapter(FragmentManager fm, int sellerId){
            super(fm);
            this.sellerId = sellerId;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return DiaryFragment.newInstance(sellerId);
                case 1:
                    return OffRouteFragment.newInstance(sellerId);
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Agenda";
                case 1:
                    return "Fuera de ruta";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        final Activity context = this;
        SweetAlertDialog dialog = Helpers.getConfirmationDialog(this, "Volver", "Esta seguro que desea cerrar sesión?", "Volver", "Cancelar");

        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(context, LogInActivity.class);
                startActivity(intent);
                sweetAlertDialog.cancel();
            }
        });

        dialog.show();
    }

}
