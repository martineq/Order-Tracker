package com.example.uriel.ordertracker.App.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.example.uriel.ordertracker.App.Model.ScheduledClientPageAdapter;
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

    AgendaFragment fragment1;
    AgendaFragment fragment2;
    private IClientService clientService;

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

        clientService = new ClientService();
        clientService.getBySeller(username, token, this, this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this,userId);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        configDrawerAfterCreate(savedInstanceState);
    }


    public void populateClients(final ArrayList<Client> clientList) {
        try {
            fragment1.populateClients(clientList);
        } catch (NullPointerException e) {}

        try {
            fragment2.populateClients(clientList);
        } catch (NullPointerException e) {}
    }

    /*public void setButton(final HashMap<Integer, String> adresses){
        final Activity context = this;
        FloatingActionButton routeButton = (FloatingActionButton) findViewById(R.id.routeButton);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adresses.size() > 0){
                    Intent intent = new Intent(context, ViewRouteActivity.class);
                    intent.putExtra("adresses", adresses);
                    startActivity(intent);
                }
                else {
                    SweetAlertDialog dialog = Helpers.getErrorDialog(context, "Atencion", "No hay clientes el dia seleccionado");
                    dialog.show();
                }
            }
        });
    }*/

    public void handleUnexpectedError(String error){
        SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error en la obtención de clientes", error);
        dialog.show();
    }

    public interface AgendaFragment {

        public void populateClients(final ArrayList<Client> clientList);
    }

    public static class DiaryFragment extends Fragment implements AgendaFragment {

        private View rootView;
        ScheduledClientPageAdapter adapter;

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

        public void populateClients(final ArrayList<Client> clientList) {
            final Activity context = this.getActivity();
            if(clientList.size() == 0){
                SweetAlertDialog dialog = Helpers.getErrorDialog(context, "Atención", "No hay ningun cliente disponible");
                dialog.show();
            }

            adapter.populateClients(clientList);

            /*final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
            HashMap<Integer, String> adresses = new HashMap<>();
            int i = 1;
            for (Client client: adapter.getList(viewPager.getCurrentItem())) {
                adresses.put(i, client.getAddress() + ", " + client.getCity());
                i++;
            }
            DiaryActivity act = (DiaryActivity) this.getActivity();
            act.setButton(adresses);*/
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_diary, container, false);

            final String username = getArguments().getString(RestService.LOGIN_RESPONSE_NAME);
            final String token = getArguments().getString(RestService.LOGIN_TOKEN);

            TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("D"));
            tabLayout.addTab(tabLayout.newTab().setText("L"));
            tabLayout.addTab(tabLayout.newTab().setText("M"));
            tabLayout.addTab(tabLayout.newTab().setText("M"));
            tabLayout.addTab(tabLayout.newTab().setText("J"));
            tabLayout.addTab(tabLayout.newTab().setText("V"));
            tabLayout.addTab(tabLayout.newTab().setText("S"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);

            FragmentActivity activity = (FragmentActivity)this.getContext();
            FragmentManager manager = activity.getSupportFragmentManager();

            adapter = new ScheduledClientPageAdapter(manager, tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            return rootView;
        }

        public void handleUnexpectedError(String error){
            SweetAlertDialog dialog = Helpers.getErrorDialog(getActivity(), "Error en la obtención de la agenda", error);
            dialog.show();
        }
    }

    public static class OffRouteFragment extends Fragment implements AgendaFragment {

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
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setRetainInstance(true);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_off_route, container, false);

            final String username = SessionInformation.getEditor().loadUserInformation().getUsername();
            final String token = SessionInformation.getEditor().loadUserInformation().getToken();

            return rootView;
        }

        public void populateClients(final ArrayList<Client> clientList) {
            final Activity context = this.getActivity();
            if(clientList.size() == 0){
                SweetAlertDialog dialog = Helpers.getErrorDialog(context, "Atención", "No hay ningun cliente disponible");
                dialog.show();
            }

            final ListView listView = (ListView) rootView.findViewById(R.id.listView);
            ClientsAdapter clientsAdapter = new ClientsAdapter(getActivity(), clientList);
            listView.setAdapter(clientsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Client client = clientList.get(position);
                    HashMap<String, String> clientDetails = new HashMap<String, String>();
                    clientDetails.put("id", String.valueOf(client.getId()));
                    clientDetails.put("visit_id", String.valueOf(client.getVisit_id()));
                    clientDetails.put("name", client.getName());
                    clientDetails.put("address", client.getAddress());
                    clientDetails.put("city", client.getCity());
                    clientDetails.put("state", client.getState());
                    clientDetails.put("date", String.valueOf(client.getLongDate()));
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("client", clientDetails);
                    startActivity(intent);
                }
            });
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int sellerId;
        DiaryActivity par;

        public SectionsPagerAdapter(FragmentManager fm, DiaryActivity act, int sellerId) {
            super(fm);
            par=act;
            this.sellerId = sellerId;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DiaryFragment f= DiaryFragment.newInstance(sellerId, username, token);
                    fragment1=f;
                    return f;
                case 1:
                    OffRouteFragment f2= OffRouteFragment.newInstance(sellerId, username, token);
                    fragment2=f2;
                    return f2;
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