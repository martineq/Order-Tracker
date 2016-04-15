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
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Impl.ClientService;
import com.example.uriel.ordertracker.App.Services.Impl.RestService;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DiaryActivity extends DrawerActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String username;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Actividad diaria");

        User user = SessionInformation.getEditor().loadUserInformation();
        username = user.getUsername();
        token = user.getToken();

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
            args.putString(RestService.LOGIN_RESPONSE_NAME, username);
            args.putString(RestService.LOGIN_TOKEN, token);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_diary, container, false);

            final String username = getArguments().getString(RestService.LOGIN_RESPONSE_NAME);
            final String token = getArguments().getString(RestService.LOGIN_TOKEN);

            return rootView;
        }

        public void handleUnexpectedError(String error){
            SweetAlertDialog dialog = Helpers.getErrorDialog(getActivity(), "Error en la obtención de la agenda", error);
            dialog.show();
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
            args.putString(RestService.LOGIN_RESPONSE_NAME, username);
            args.putString(RestService.LOGIN_TOKEN, token);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_off_route, container, false);

            final String username = SessionInformation.getEditor().loadUserInformation().getUsername();
            final String token = SessionInformation.getEditor().loadUserInformation().getToken();

            clientService = new ClientService();
            clientService.getBySeller(username, token, this, this.getActivity());

            return rootView;
        }

        public void populateClients(final ArrayList<Client> clientList) {
            final ListView listView = (ListView) rootView.findViewById(R.id.listView);
            ClientsAdapter clientsAdapter = new ClientsAdapter(getActivity(), clientList);
            listView.setAdapter(clientsAdapter);

            final Activity context = getActivity();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Client client = clientList.get(position);
                    HashMap<String, String> clientDetails = new HashMap<String, String>();
                    clientDetails.put("id", String.valueOf(client.getId()));
                    clientDetails.put("name", client.getName());
                    clientDetails.put("address", client.getAddress());
                    clientDetails.put("city", client.getCity());
                    clientDetails.put("state", client.getState());
                    clientDetails.put("visitDate", String.valueOf(client.getVisitDate()));
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("client", clientDetails);
                    startActivity(intent);
                }
            });
        }

        public void handleUnexpectedError(String error){
            SweetAlertDialog dialog = Helpers.getErrorDialog(getActivity(), "Error en la obtención de clientes", error);
            dialog.show();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int sellerId;

        public SectionsPagerAdapter(FragmentManager fm, int sellerId) {
            super(fm);
            this.sellerId = sellerId;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
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

        Boolean yourBool = getIntent().getExtras().getBoolean("FIRST");

        if (yourBool==true) {
            final Activity context = this;
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(this, "Salir", "Esta seguro que desea salir", "Salir", "Cancelar");

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    finish();
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();

        }

        else {
            super .onBackPressed();
        }
    }
}