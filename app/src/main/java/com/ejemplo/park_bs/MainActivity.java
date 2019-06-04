package com.ejemplo.park_bs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void RollUsuarioOnclick(View view){
        Intent roll_usuario = new Intent(this, ActivityRollUsuario.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
        //roll_usuario.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(roll_usuario);
    }

    public void LoginOnClick(View view){
        Intent login = new Intent(this, ActivityLogin.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
    }
}
