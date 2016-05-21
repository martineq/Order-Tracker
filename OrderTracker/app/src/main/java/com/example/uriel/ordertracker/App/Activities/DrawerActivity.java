package com.example.uriel.ordertracker.App.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.App.Services.Impl.RestService;
import com.example.uriel.ordertracker.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DrawerActivity extends AppCompatActivity {

    public DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    public ActionBarDrawerToggle mDrawerToggle;

    public CharSequence mDrawerTitle;
    public CharSequence mTitle;
    public String[] optionsTitles;

    public int userId;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        userId = sharedPref.getInt(RestService.LOGIN_RESPONSE_ID, 0);
        userId = SessionInformation.getEditor().loadUserInformation().getId();
        username = SessionInformation.getEditor().loadUserInformation().getUsername();
    }

    public void configDrawerAfterCreate(Bundle savedInstanceState) {

        mTitle = mDrawerTitle = getTitle();
        optionsTitles = getResources().getStringArray(R.array.arrayItems);
        optionsTitles[optionsTitles.length - 1] = username;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, optionsTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */

        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectAction(position);
        }
    }

    /* Manejar qué pasa cuando se selecciona cada elemento de la lista.
     */
    private void selectAction(int position) {
        mDrawerList.setItemChecked(position, true);
        final Activity context = this;

        switch (position) {
            //El orden del case es el orden en el que estan las opciones en arrayItems (en archivo strings.xml)
            case 0:
                Intent intent0;
                intent0 = new Intent(this, DiaryActivity.class);
                intent0.putExtra("FIRST", false);
                startActivity(intent0);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            case 1:
                Intent intent1;
                intent1 = new Intent(this, OrderActivity.class);
                intent1.putExtra("ReadOnly", true);
                startActivity(intent1);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            case 2:
                Intent intent2;
                intent2 = new Intent(this, OrderHistoryActivity.class);
                startActivity(intent2);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            case 3:
                SweetAlertDialog dialog3 = Helpers.getConfirmationDialog(this, "Cerrar sesión", "Esta seguro que desea cerrar sesión?", "Cerrar sesión", "Cancelar");
                dialog3.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        SessionInformation.getEditor().removeUserInformation();

                        Intent intent3;
                        intent3 = new Intent(getApplicationContext(),LogInActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        sweetAlertDialog.dismiss();
                        finish();
                        startActivity(intent3);
                    }
                });
                dialog3.show();
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
        }
    }


    /* llamar a esta función para ponerle titulo al drawer Activity*/
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}