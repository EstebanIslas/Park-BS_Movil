package com.ejemplo.park_bs;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ActivityContenedorUsuario extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_usuario);

        initComponents();
        setUpViewPagerAdapter();
    }

    private void initComponents() {
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    private void setUpViewPagerAdapter() {
        viewPagerAdapter.addFragment(new MapaUsuarioFragment(), "Inicio");
        viewPagerAdapter.addFragment(new BuscarEstacionamientoFragment(), "Buscar");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Log.d("TAG1", "Posicion: " + tabLayout.getSelectedTabPosition() + " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;

                    case 1:
                        Log.d("TAG1", "Posicion: " + tabLayout.getSelectedTabPosition() + " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
