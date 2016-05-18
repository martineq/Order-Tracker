package com.example.uriel.ordertracker.App.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Impl.LoginService;
import com.example.uriel.ordertracker.App.Services.Impl.MiInstanceIDListenerService;
import com.example.uriel.ordertracker.App.Services.Impl.RestService;
import com.example.uriel.ordertracker.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogInActivity extends AppCompatActivity {
    private View entryScreen;
    private View loggingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SessionInformation.InitializeSessionInformation(this.getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Order Tracker");

        this.entryScreen = findViewById(R.id.entry_screen);
        this.loggingScreen = findViewById(R.id.login_in_screen);

        if ( SessionInformation.isValidSession() == false ) {
            SessionInformation.getEditor().removeUserInformation();
            this.showProgress(false);
        }

        else try {
            this.showProgress(true);
            LoginService.newRequest(this).login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateUser(View v){
        String username = String.valueOf(((TextView) findViewById(R.id.user_id_startup)).getText());
        String password = String.valueOf(((TextView) findViewById(R.id.password_id_startup)).getText());

        if(username.equals(""))
            Helpers.getErrorDialog(this, "Error", "Ingrese un nombre de usuario").show();

        else if(password.equals(""))
            Helpers.getErrorDialog(this, "Error", "Ingrese una contraseña").show();

        else {
            this.showProgress(true);
            SessionInformation.getEditor().saveUserInformation( new User(0, username, password) );
            LoginService.newRequest(this).login();
        }
    }

    public void processLoginResponse(User user){
        SessionInformation.getEditor().updateUserInformation(user);

        MiInstanceIDListenerService miInstanceIDListenerService = new MiInstanceIDListenerService(this.getApplicationContext());
        miInstanceIDListenerService.onTokenRefresh();

        //startService(new Intent(this, NotificationsService.class));

        /*Intent i = new Intent(this, PushService.class);
        i.putExtra(RestService.LOGIN_RESPONSE_NAME, user.getUsername());
        i.putExtra(RestService.LOGIN_TOKEN, user.getToken());
        startService(i);*/

        Intent intent = new Intent(this, DiaryActivity.class);
        intent.putExtra(RestService.LOGIN_RESPONSE_ID, String.valueOf(user.getId()));
        intent.putExtra(RestService.LOGIN_RESPONSE_NAME, user.getUsername());
        intent.putExtra(RestService.LOGIN_TOKEN, user.getToken());
        intent.putExtra("FIRST", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        loggingScreen.setVisibility(View.INVISIBLE);
        loggingScreen.clearAnimation();

        finish();
    }

    public void handleUnexpectedError(String error){
        SessionInformation.getEditor().removeUserInformation();
        this.showProgress(false);
        SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error de autenticación", error);
        dialog.show();
    }

    public void showProgress(final boolean show) {
        System.out.println ("show progress: "+show);
        entryScreen.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        loggingScreen.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        if ( show == true ) {
            // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
            // for very easy animations. If available, use these APIs to fade-in
            // the progress spinner.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
                loggingScreen.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {}
                });

            }

        } else loggingScreen.clearAnimation();
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
