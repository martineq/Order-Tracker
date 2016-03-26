package com.example.uriel.ordertracker.App.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.ClientsAdapter;
import com.example.uriel.ordertracker.App.Services.Impl.ClientService;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Bundle args = getIntent().getExtras();
        userId = args.getInt("userId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Actividad diaria");
        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), userId);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ir a leer QR para comenzar pedido
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            ArrayList<Client> clients = clientService.getBySeller(getArguments().getInt("userId"));
            ListView listView = (ListView) rootView.findViewById(R.id.listView);
            ClientsAdapter clientsAdapter = new ClientsAdapter(getActivity(), clients);
            listView.setAdapter(clientsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //ir al detalle del cliente
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
}
