package com.ejemplo.park_bs;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarEstacionamientoFragment extends Fragment {


    private ListView lv_estacionamientos_buscar;
    private ArrayAdapter adapter;

    private String url_estacionamientos = "http://192.168.1.69:8080/api_estacionamientos?user_hash=12345&action=get";


    public static final String ID_ESTACIOONAMIENTO = "1";

    public BuscarEstacionamientoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_estacionamiento, container, false);

        /* Strict = Establece conexion externa y requiere permiso de mantenerla abierta */
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        initComponents(view);
        webServiceRest(url_estacionamientos);
        lv_estacinamientoOnClick();
        return view;
    }

    public void lv_estacinamientoOnClick(){
        lv_estacionamientos_buscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.e("ITEM", lv_estacionamientos_buscar.getItemAtPosition(position).toString());
                String datos_cliente[] = lv_estacionamientos_buscar.getItemAtPosition(position).toString().split(":");
                String id_cliente = datos_cliente[0];
                Log.e("ID_CLIENTE",id_cliente);
                Intent i = new Intent(getActivity(), ActivityReservarUsuario.class);
                i.putExtra(ID_ESTACIOONAMIENTO,id_cliente);
                startActivity(i);
            }
        });
    }

    private void initComponents(View view) {
        lv_estacionamientos_buscar = (ListView)view.findViewById(R.id.lv_estacionamientos_list_buscar);
        adapter = new ArrayAdapter(getActivity(), R.layout.list_item_mostrar_estacionamientos);
        lv_estacionamientos_buscar.setAdapter(adapter);

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

    /**
     * Metodo que recibe una cadena String y la almacena en un array de tipo JSONArray, lo que
     * permite separar los campos y valores de la cadena.
     * @param jsonResult
     */
    private void parseInformation(String jsonResult) {
        JSONArray jsonArray = null; //variable de tipo Json Array

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
        }catch (JSONException ex){
            Log.e("Error JSONArray 001;", ex.getMessage());
        }

        //Ciclo que cuenta los valores de la BD mediante JSON y lo guarda el las variables ya declaradas
        for(int i=0; i<jsonArray.length();i++) {
            try {
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

                /*Une todos las variables con sus valores reales de la BD*/
                adapter.add("\t" + id+": " + "\tNOMBRE: "+ nombre
                        + "\n\t\t TITULAR: \t" + titular + "\n\t\t TARIFA: \t" + tarifa
                        + "\n\t\t TELÉFONO: \t" + telefono+ "\n\t\t HORA DE APERTURA: \t"
                        + hora_apertura+ "\n\t\t HORA DE CIERRE: \t" + hora_cierre+ "\n");


            } catch (JSONException ex) {
                Log.e("Error JSONArray 002;", ex.getMessage());
            }

        }

    }


}
