package com.ejemplo.park_bs;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class ActivityReservarUsuario extends AppCompatActivity {

    TextView tv_id_empresa_reservar;
    TextView tv_nombre_empresa_reservar;
    TextView tv_telefono_emppresa_reservar;
    TextView tv_hora_apertura_reservar;
    TextView tv_hora_cierre_reservar;
    TextView tv_disponibilidad;
    EditText et_horade_reservar;

    private String url_mostrarestacinamiento = "http://192.168.1.69:8080/api_estacionamientos?user_hash=12345&action=get&id=";
    private String url_enviareserva = "http://192.168.1.69:8080/api_login?user_hash=12345&action=put&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_usuario);

        initComponenst();

        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Almacenar el id del cliente de la actividad BuscarEstacionamiento
        String id_estacionamiento= intent.getStringExtra(BuscarEstacionamientoFragment.ID_ESTACIOONAMIENTO);
        url_mostrarestacinamiento += id_estacionamiento; //Concatena el id para la busqueda de JSON

        webServiceRest(url_mostrarestacinamiento);

    }


    public void comienzaOnClick(View view){

        // ############ Variables para el time picker ############# //

        final String CERO = "0";
        final String DOS_PUNTOS = ":";

        //Calendario para obtener hora
        Calendar c = Calendar.getInstance();

        //obtener hora

        final int hora = c.get(Calendar.HOUR_OF_DAY);
        final int minuto = c.get(Calendar.MINUTE);

        // ######################################################## //

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

                et_horade_reservar.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + ":00");
            }

        }, hora, minuto, false);

        recogerHora.show();
    }

    private void initComponenst() {
        tv_id_empresa_reservar = findViewById(R.id.tv_id_empresa_reservar);
        tv_nombre_empresa_reservar = findViewById(R.id.tv_nombre_empresa_reservar);
        tv_telefono_emppresa_reservar = findViewById(R.id.tv_telefono_empresa_reservar);
        tv_hora_apertura_reservar = findViewById(R.id.tv_hora_apertura_reservar);
        tv_hora_cierre_reservar = findViewById(R.id.tv_hora_cierre_reservar);
        tv_disponibilidad = findViewById(R.id.tv_disponibilidad_reservar);
        et_horade_reservar = findViewById(R.id.edt_horade_reservar);
    }

    /**
     *Para realizar la petición de conexión y el almacenamiento de la información
     *que regrese el webservice, los datos recibidos se almacenan en forma de lineas, por lo que
     * posteriormente se requiere un parseo para separar cada uno de los campos
     * @param requestURL
     */
    private void webServiceRest(String requestURL){
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


        }catch(Exception ex){
            Log.e("Error WebServRest 001: ",ex.getMessage());
        }
    }

    private void parseInformation(String jsonResult) {
        JSONArray jsonArray = null;

        //Variables que guardan el valor de los atributos de la BD
        String id;
        String nombre;
        String titular;
        String colonia;
        String calle;
        String numero;
        String cp;
        String latitud;
        String longitud;
        String tarifa;
        String telefono;
        String correo;
        String hora_apertura;
        String hora_cierre;
        String dia_inicio;
        String dia_final;
        String password;
        String rol;
        String disponibilidad;
        String estado;
        String imagen;
        String puntuacion;


        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error JSONArray 001: ",e.getMessage());
        }

        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getString("id");
                nombre = jsonObject.getString("nombre");
                titular = jsonObject.getString("titular");
                colonia = jsonObject.getString("colonia");
                calle = jsonObject.getString("calle");
                numero = jsonObject.getString("numero");
                cp = jsonObject.getString("cp");
                latitud = jsonObject.getString("latitud");
                longitud = jsonObject.getString("longitud");
                tarifa= jsonObject.getString("tarifa");
                telefono = jsonObject.getString("telefono");
                correo = jsonObject.getString("telefono");
                hora_apertura = jsonObject.getString("hora_apertura");
                hora_cierre = jsonObject.getString("hora_cierre");
                dia_inicio = jsonObject.getString("dia_inicio");
                dia_final = jsonObject.getString("dia_final");
                password = jsonObject.getString("password");
                rol = jsonObject.getString("rol");
                disponibilidad = jsonObject.getString("disponibilidad");
                estado = jsonObject.getString("estado");
                imagen = jsonObject.getString("imagen");
                puntuacion = jsonObject.getString("puntuacion");

                tv_id_empresa_reservar.setText(id);
                tv_nombre_empresa_reservar.setText(nombre);
                tv_telefono_emppresa_reservar.setText(telefono);
                tv_hora_apertura_reservar.setText(hora_apertura);
                tv_hora_cierre_reservar.setText(hora_cierre);
                tv_disponibilidad.setText(disponibilidad);

            }catch (JSONException e){
                Log.e("Error JSONArray 002;",e.getMessage());
            }
        }

    }

    public void btn_insertarReservaOnClick(View view){

        String hora = et_horade_reservar.getText().toString();
        String disponible = tv_disponibilidad.getText().toString();
        Log.e("###########", disponible);
        Log.e("HORA######", hora);
        if (disponible != "null"  && hora != "") {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(url_enviareserva);
                sb.append("id_estacionamiento=" + tv_id_empresa_reservar.getText());
                sb.append("&");
                sb.append("nombre_estacionamiento=" + tv_nombre_empresa_reservar.getText());
                sb.append("&");
                sb.append("hora=" + et_horade_reservar.getText());
                sb.append("&");
                sb.append("nombre_usuario");

                webServicePut(sb.toString());
                Log.e("URL: ", sb.toString());

                Toast.makeText(ActivityReservarUsuario.this, "Reserva Realizada!!", Toast.LENGTH_SHORT).show();

                et_horade_reservar.setText("");

                Intent login = new Intent(this, ActivityContenedorUsuario.class);//Ayuda a crear fucniones para pasar de una pantalla a otra
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            } catch (Exception ex) {
                Toast.makeText(ActivityReservarUsuario.this, "Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ActivityReservarUsuario.this, "No hay lugares disponibles!!", Toast.LENGTH_SHORT).show();
        }
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

            parseInformation_reserva(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }



    private void parseInformation_reserva(String jsonResult){
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
