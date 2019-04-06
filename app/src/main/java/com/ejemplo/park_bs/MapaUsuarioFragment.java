package com.ejemplo.park_bs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaUsuarioFragment extends Fragment{

    private Button btn_ubicarme;

    public MapaUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa_usuario, container, false);
        initComponents(view);

        btn_ubicarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityMapsUsuario.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initComponents(View view) {
        btn_ubicarme=(Button)view.findViewById(R.id.btn_ubicarme);
    }

}
