package com.ejemplo.park_bs;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class ActivityRegistroEmpresa extends AppCompatActivity {

    // ############ Variables para el time picker ############# //

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener hora
    public final Calendar c = Calendar.getInstance();

    //obtener hora

    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    // ######################################################## //

    EditText edt_nombre_empresa;
    EditText edt_titular;
    EditText edt_colonia, edt_calle, edt_numero, edt_cod_postal;
    EditText edt_longitud, edt_latitud;
    EditText edt_tarifa;
    EditText edt_hora_inicio, edt_hora_final;
    EditText edt_dia_inicio, edt_dia_final;
    EditText edt_telefono;
    EditText edt_correo, edt_password, edt_confirmar_password;

    private String webservice_url = "http://130.100.17.77:8080/api_estacionamientos?user_hash=12345&action=put&";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        initComponents();
    }

    private void initComponents(){
        edt_nombre_empresa = findViewById(R.id.edt_nombre_empresa);
        edt_titular = findViewById(R.id.edt_titular_empresa);
        edt_colonia = findViewById(R.id.edt_colonia_empresa);
        edt_calle = findViewById(R.id.edt_calle_empresa);
        edt_numero = findViewById(R.id.edt_numero_empresa);
        edt_cod_postal = findViewById(R.id.edt_codpost_empresa);
        edt_longitud = findViewById(R.id.edt_longitud_empresa);
        edt_latitud = findViewById(R.id.edt_latitud_empresa);
        edt_tarifa = findViewById(R.id.edt_tarifa_empresa);
        edt_hora_inicio = findViewById(R.id.edt_horain_empresa);
        edt_hora_final = findViewById(R.id.edt_horafin_empresa);
        edt_dia_inicio = findViewById(R.id.edt_diain_empresa);
        edt_dia_final = findViewById(R.id.edt_diafin_empresa);
        edt_telefono = findViewById(R.id.edt_telefono_empresa);
        edt_correo = findViewById(R.id.edt_correo_empresa);
        edt_password = findViewById(R.id.edt_password_empresa);
        edt_confirmar_password = findViewById(R.id.edt_confirm_password_empresa);
    }


    public void btn_insertregistroOnClick(View view){

        String password = edt_password.getText().toString();
        String confirm_pass = edt_confirmar_password.getText().toString();

        if (password.equals(confirm_pass)) {

            password = ActivityRegistroEmpresa.md5(edt_password.getText().toString());

            try {
                StringBuilder sb = new StringBuilder();
                sb.append(webservice_url);
                sb.append(("nombre=" + edt_nombre_empresa.getText()));
                sb.append("&");
                sb.append(("titular=" + edt_titular.getText()));
                sb.append("&");
                sb.append(("colonia=" + edt_colonia.getText()));
                sb.append("&");
                sb.append(("calle=" + edt_calle.getText()));
                sb.append("&");
                sb.append(("numero=" + edt_numero.getText()));
                sb.append("&");
                sb.append(("cp=" + edt_cod_postal.getText()));
                sb.append("&");
                sb.append(("latitud=" + edt_latitud.getText()));
                sb.append("&");
                sb.append(("longitud=" + edt_longitud.getText()));
                sb.append("&");
                sb.append(("tarifa=" + edt_tarifa.getText()));
                sb.append("&");
                sb.append(("telefono=" + edt_telefono.getText()));
                sb.append("&");
                sb.append(("correo=" + edt_correo.getText()));
                sb.append("&");
                sb.append(("hora_apertura=" + edt_hora_inicio.getText()));
                sb.append("&");
                sb.append(("hora_cierre=" + edt_hora_final.getText()));
                sb.append("&");
                sb.append(("dia_inicio=" + edt_dia_inicio.getText()));
                sb.append("&");
                sb.append(("dia_final=" + edt_dia_final.getText()));
                sb.append("&");
                sb.append(("password=" + password));
                sb.append("&");
                sb.append(("rol=2"));



                webServicePut(sb.toString());
                Log.e("URL", sb.toString());
                Toast.makeText(ActivityRegistroEmpresa.this, "Registro Creado y Guardado!!", Toast.LENGTH_SHORT).show();

                cleanComponents();//Limpiar los Edit Text del usuario


                Intent login = new Intent(this, ActivityLogin.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
                startActivity(login);


            } catch (Exception ex) {
                Toast.makeText(ActivityRegistroEmpresa.this, "Error Insert " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(ActivityRegistroEmpresa.this, "Contraseña invalida!!", Toast.LENGTH_SHORT).show();
        }


    }


    private void cleanComponents(){
        edt_nombre_empresa.setText("");
        edt_titular.setText("");
        edt_colonia.setText("");
        edt_calle.setText("");
        edt_numero.setText("");
        edt_cod_postal.setText("");
        edt_longitud.setText("");
        edt_latitud.setText("");
        edt_tarifa.setText("");
        edt_hora_inicio.setText("");
        edt_hora_final.setText("");
        edt_dia_inicio.setText("");
        edt_dia_final.setText("");
        edt_telefono.setText("");
        edt_correo.setText("");
        edt_password.setText("");
        edt_confirmar_password.setText("");
    }





    // ######################### METODO PARA EL TIMEPICKER ########################## //


    public void iniciohrOnClick(View v){
        obtenerHora(edt_hora_inicio);
    }

    public void finhrOnClick(View v){
        obtenerHora(edt_hora_final);
    }


    private void obtenerHora(final EditText selecionahora){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                /*String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }*/

                selecionahora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + ":00");
            }

        }, hora, minuto, false);

        recogerHora.show();
    }




    // ######################### METODO PARA HASHEAR Y CIFRAR CON MD5 ########################## //

    private static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e("#####Error HASH: #####",e.getMessage());
        }
        return null;
    }

    /* Retorna un hash MD5 a partir de un texto */
    private static String md5(String txt) {
        return ActivityRegistroEmpresa.getHash(txt, "MD5");
    }

    /* Retorna un hash SHA1 a partir de un texto */
    private static String sha1(String txt) {
        return ActivityRegistroEmpresa.getHash(txt, "SHA1");
    }



    //################################## CONEXION CON EL WEB SERVICE ##################################

    private void webServicePut(String requestURL){
        try{
            URL url = new URL(requestURL);

            /*Variable que crea la conexion*/
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            /* Se utiliza InputStreamReader y la conexion creada para almacenar los datos que regresa el webservice */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line=""; //Variable que guarda los datos que se guardan del webservice
            String webServiceResult=""; //almacena las lineas de datos

            /*Ciclo que cuenta las lineas de la BD y las guarda en webserviceResult*/
            while((line=bufferedReader.readLine())!=null){
                webServiceResult += line;
            }

            bufferedReader.close();

            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100 Web Service:",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("#######Error 101#######",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("#######STATUS#######",status);
                Log.e("#######DESCRIPTION#####",description);
            }catch (JSONException e){
                Log.e("#######Error 102#######",e.getMessage());
            }
        }
    }
}
