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
    private String username;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Actividad diaria");

        Bundle args = getIntent().getExtras();
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
            SweetAlertDialog dialog = Helpers.getErrorDialog(getActivity(), "Error de autenticación", error);
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

            final String username = getArguments().getString(RestService.LOGIN_RESPONSE_NAME);
            final String token = getArguments().getString(RestService.LOGIN_TOKEN);

            clientService = new ClientService();

            //borrar cuando ande el servicio
            /*ArrayList<Client> clients = new ArrayList<>();
            Client client1 = new Client(1, "Aldo Proieto", "Av Paseo colon 850", "CABA", Constants.VISITADO);
            Client client2 = new Client(2, "Federico Bulos", "Av Gral Las Heras 2214", "CABA", Constants.NO_VISITADO);
            Client client3 = new Client(3, "Sebestian Vignolo", "Lavalleja 173", "CABA", Constants.VISITADO);
            Client client4 = new Client(4, "Pablo Ladaga", "Av Scalabrini Ortiz 2417", "CABA", Constants.PENDIENTE);
            Client client5 = new Client(5, "Juan Carlos Pellegrini", "Av Angel Gallardo 265", "CABA", Constants.VISITADO);
            Client client6 = new Client(6, "Emiliano Raggi", "Av Cnel Apolinario Figueroa 1043", "CABA", Constants.PENDIENTE);
            Client client7 = new Client(7, "Roberto Leto", "Paysandu 1459", "CABA", Constants.NO_VISITADO);
            Client client8 = new Client(8, "Jose Amado", "Av Cordoba 1450", "CABA", Constants.NO_VISITADO);
            Client client9 = new Client(9, "Daniel Retamozo", "Av Corrientes 850", "CABA", Constants.VISITADO);
            Client client10 = new Client(10, "Juan Fernandez", "Araoz 657", "CABA", Constants.NO_VISITADO);
            Client client11 = new Client(11, "Renato De La Paulera", "Humboldt 1100", "CABA", Constants.VISITADO);
            Client client12 = new Client(12, "Walter Zafarian", "Sarmiento 350", "CABA", Constants.PENDIENTE);
            Client client13 = new Client(13, "Martin Liberman", "Lacarra 1000", "CABA", Constants.VISITADO);
            Client client14 = new Client(14, "Matias Garcia", "Jose Hernandez 420", "CABA", Constants.PENDIENTE);
            Client client15 = new Client(15, "Diego Fuks", "Av Cabildo 2530", "CABA", Constants.NO_VISITADO);
            Client client16 = new Client(16, "Marcelo Grandio", "Roosevelt 1655", "CABA", Constants.NO_VISITADO);

            clients.add(client1);
            clients.add(client2);
            clients.add(client3);
            clients.add(client4);
            clients.add(client5);
            clients.add(client6);
            clients.add(client7);
            clients.add(client8);
            clients.add(client9);
            clients.add(client10);
            clients.add(client11);
            clients.add(client12);
            clients.add(client13);
            clients.add(client14);
            clients.add(client15);
            clients.add(client16);

            populateClients(clients);*/

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
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("clientId", client.getId());
                    startActivity(intent);
                }
            });
        }

        public void handleUnexpectedError(String error){
            SweetAlertDialog dialog = Helpers.getErrorDialog(getActivity(), "Error de autenticación", error);
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