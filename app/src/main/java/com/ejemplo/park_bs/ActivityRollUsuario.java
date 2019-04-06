package com.ejemplo.park_bs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityRollUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_usuario);
    }
    public void RegistroUsuarioOnclick(View view){
        Intent reg_usuario = new Intent(this, ActivityRegistroUsuario.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
        //reg_usuario.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(reg_usuario);
    }

    public void RegistroEmpresaOnclick(View view){
        Intent reg_empresa = new Intent(this, ActivityRegistroEmpresa.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
        //reg_empresa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(reg_empresa);
    }
}
