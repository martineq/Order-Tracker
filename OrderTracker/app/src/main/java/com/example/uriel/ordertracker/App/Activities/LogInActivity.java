package com.example.uriel.ordertracker.App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Impl.UserService;
import com.example.uriel.ordertracker.App.Services.Interface.IUserService;
import com.example.uriel.ordertracker.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogInActivity extends AppCompatActivity {

    private IUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Order Tracker");

        this.userService = new UserService();
    }

    public void validateUser(View v){
        SweetAlertDialog dialog = null;
        String username = String.valueOf(((TextView)findViewById(R.id.user_id_startup)).getText());
        String password = String.valueOf(((TextView) findViewById(R.id.password_id_startup)).getText());

        if(username.equals("")){
            dialog = Helpers.getErrorDialog(this, "Error", "Ingrese un nombre de usuario");
            dialog.show();
        }else if(password.equals("")){
            dialog = Helpers.getErrorDialog(this, "Error", "Ingrese una contraseña");
            dialog.show();
        }else {
            String validation = userService.validateUser(username, password);
            if(validation.equals(Constants.USER_OK)){
                User loggedUser = userService.getByUsername(username);
                Intent intent = new Intent(this, DiaryActivity.class);
                intent.putExtra("userId", loggedUser.getId());
                startActivity(intent);
            }else {
                if(validation.equals(Constants.USER_INVALID)){
                    dialog = Helpers.getErrorDialog(this, "Error", "El nombre de usuario es invalido, intente nuevamente");
                }else if(validation.equals(Constants.PASSWORD_INVALID)){
                    dialog = Helpers.getErrorDialog(this, "Error", "La constraseña es invalida, intente nuevamente");
                }
                dialog.show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        SweetAlertDialog dialog = Helpers.getConfirmationDialog(this, "Salir", "Esta seguro que desea salir?", "Salir", "Cancelar");

        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                finish();
                moveTaskToBack(true);
                sweetAlertDialog.cancel();
            }
        });

        dialog.show();
    }
}
