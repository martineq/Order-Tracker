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
import com.example.uriel.ordertracker.App.Services.Impl.RestService;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DiaryActivity extends DrawerActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int userId;
    private String username;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Actividad diaria");

        Bundle args = getIntent().getExtras();
        userId = args.getInt("userId");
        username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        token = args.getString(RestService.LOGIN_TOKEN);

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

        public static DiaryFragment newInstance(int sellerId, String username, String token) {
            DiaryFragment fragment = new DiaryFragment();
            Bundle args = new Bundle();
            args.putInt("userId", sellerId);
            args.putString(RestService.LOGIN_RESPONSE_NAME, username);
            args.putString(RestService.LOGIN_TOKEN, token);
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
        private View rootView;

        public OffRouteFragment() {
        }

        public static OffRouteFragment newInstance(int sellerId, String username, String token) {
            OffRouteFragment fragment = new OffRouteFragment();
            Bundle args = new Bundle();
            args.putInt("userId", sellerId);
            args.putString(RestService.LOGIN_RESPONSE_NAME, username);
            args.putString(RestService.LOGIN_TOKEN, token);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_off_route, container, false);

            final int userId = getArguments().getInt("userId");
            final String username = getArguments().getString(RestService.LOGIN_RESPONSE_NAME);
            final String token = getArguments().getString(RestService.LOGIN_TOKEN);

            clientService = new ClientService();
            clientService.getBySeller(username, token, this, this.getActivity());

            return rootView;
        }

        public void populateClients(final ArrayList<Client> clientList){
            final ListView listView = (ListView) rootView.findViewById(R.id.listView);
            ClientsAdapter clientsAdapter = new ClientsAdapter(getActivity(), clientList);
            listView.setAdapter(clientsAdapter);

            final Activity context = getActivity();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Client client = clientList.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("clientId", client.getId());
                    startActivity(intent);
                }
            });
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
                    return DiaryFragment.newInstance(sellerId, username, token);
                case 1:
                    return OffRouteFragment.newInstance(sellerId, username, token);
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
