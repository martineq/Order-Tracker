package com.example.uriel.ordertracker.App.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Impl.RestService;
import com.example.uriel.ordertracker.App.Services.Impl.UserService;
import com.example.uriel.ordertracker.App.Services.Interface.IUserService;
import com.example.uriel.ordertracker.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogInActivity extends AppCompatActivity {

    private IUserService userService;
    private View entryScreen;
    private View loggingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Order Tracker");

        this.entryScreen = findViewById(R.id.entry_screen);
        this.loggingScreen = findViewById(R.id.login_in_screen);
        this.userService = new UserService();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPref.getString(RestService.LOGIN_RESPONSE_NAME, "");
        String password = sharedPref.getString(RestService.LOGIN_PASSWORD, "");
        if(username != "" && password != ""){
            try {
                userService.validateUser(username, password, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.showProgress(false);
    }

    public void validateUser(View v){
        SweetAlertDialog dialog = null;
        String username = String.valueOf(((TextView) findViewById(R.id.user_id_startup)).getText());
        String password = String.valueOf(((TextView) findViewById(R.id.password_id_startup)).getText());

        if(username.equals("")){
            dialog = Helpers.getErrorDialog(this, "Error", "Ingrese un nombre de usuario");
            dialog.show();
        }else if(password.equals("")){
            dialog = Helpers.getErrorDialog(this, "Error", "Ingrese una contraseña");
            dialog.show();
        }else {
            userService.validateUser(username, password, this);
        }
    }

    public void processLoginResponse(User user){
        TextView username = (TextView) findViewById(R.id.user_id_startup);
        TextView password = (TextView) findViewById(R.id.password_id_startup);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String a = username.getText().toString();
        editor.putInt(RestService.LOGIN_RESPONSE_ID, user.getId());
        editor.putString(RestService.LOGIN_RESPONSE_NAME, user.getUsername());
        editor.putString(RestService.LOGIN_PASSWORD, password.getText().toString());
        editor.putString(RestService.LOGIN_TOKEN, user.getToken());
        editor.commit();

        Intent intent = new Intent(this, DiaryActivity.class);
        //cambiar a id
        intent.putExtra(RestService.LOGIN_RESPONSE_ID, String.valueOf(user.getId()));
        intent.putExtra(RestService.LOGIN_RESPONSE_NAME, user.getUsername());
        intent.putExtra(RestService.LOGIN_TOKEN, user.getToken());
        intent.putExtra(RestService.LOGIN_PASSWORD, password.getText().toString());
        startActivity(intent);
        finish();
    }

    public void handleUnexpectedError(String error){
        SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error de autenticación", error);
        dialog.show();
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            entryScreen.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    entryScreen.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                }
            });

            loggingScreen.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loggingScreen.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });

        } else {
            entryScreen.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            loggingScreen.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        SweetAlertDialog dialog = Helpers.getConfirmationDialog(this, "Salir", "Esta seguro que desea salir?", "Salir", "Cancelar");

        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                finish();
                sweetAlertDialog.cancel();
            }
        });

        dialog.show();
    }
}
