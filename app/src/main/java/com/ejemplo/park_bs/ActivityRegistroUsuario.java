package com.ejemplo.park_bs;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityRegistroUsuario extends AppCompatActivity {

    EditText edt_nombre_usuario;
    EditText edt_apellidos_usuario;
    EditText edt_telefono_usuario;
    EditText edt_correo_usuario;
    EditText edt_password_usuario;
    EditText edt_confirmar_password;

    private String webservice_url = "http://130.100.17.77:8080/api_usuarios?user_hash=12345&action=put&";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        initComponents();
    }

    private void initComponents(){
        edt_nombre_usuario = findViewById(R.id.edt_nombre_usuario);
        edt_apellidos_usuario = findViewById(R.id.edt_apellidos_usuario);
        edt_telefono_usuario = findViewById(R.id.edt_telefono_usuario);
        edt_correo_usuario = findViewById(R.id.edt_correo_usuario);
        edt_password_usuario = findViewById(R.id.edt_password_usuario);
        edt_confirmar_password = findViewById(R.id.edt_confirm_password_usuario);
    }


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
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* Retorna un hash MD5 a partir de un texto */
    private static String md5(String txt) {
        return ActivityRegistroUsuario.getHash(txt, "MD5");
    }

    /* Retorna un hash SHA1 a partir de un texto */
    private static String sha1(String txt) {
        return ActivityRegistroUsuario.getHash(txt, "SHA1");
    }

    public void btn_insertregistroOnClick(View view){

            String password = edt_password_usuario.getText().toString();
            String confirm_pass = edt_confirmar_password.getText().toString();

            if (password.equals(confirm_pass)) {

                password = ActivityRegistroUsuario.md5(edt_password_usuario.getText().toString());

                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(webservice_url);
                    sb.append("nombre=" + edt_nombre_usuario.getText());
                    sb.append("&");
                    sb.append("apellidos=" + edt_apellidos_usuario.getText());
                    sb.append("&");
                    sb.append("telefono=" + edt_telefono_usuario.getText());
                    sb.append("&");
                    sb.append("correo=" + edt_correo_usuario.getText());
                    sb.append("&");
                    sb.append("password=" + password);
                    sb.append("&");
                    sb.append("rol=1");

                    webServicePut(sb.toString());
                    Log.e("URL", sb.toString());
                    Toast.makeText(ActivityRegistroUsuario.this, "Registro Creado y Guardado!!", Toast.LENGTH_SHORT).show();

                    clean_EditText();//Limpiar los Edit Text del usuario


                    Intent login = new Intent(this, ActivityLogin.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
                    startActivity(login);


                } catch (Exception ex) {
                    Toast.makeText(ActivityRegistroUsuario.this, "Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(ActivityRegistroUsuario.this, "Contraseña invalida!!", Toast.LENGTH_SHORT).show();
            }


    }

    private void clean_EditText(){
        edt_nombre_usuario.setText("");
        edt_apellidos_usuario.setText("");
        edt_telefono_usuario.setText("");
        edt_correo_usuario.setText("");
        edt_password_usuario.setText("");
        edt_confirmar_password.setText("");
    }

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
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }
}
