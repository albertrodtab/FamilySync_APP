package com.alberto.familysyncapp.view.centro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.adapter.CentroAdapter;
import com.alberto.familysyncapp.contract.centro.CentrosListContract;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.presenter.centro.CentrosListPresenter;
import com.alberto.familysyncapp.view.MapsActivityView;
import com.google.android.material.appbar.MaterialToolbar;


import java.util.ArrayList;
import java.util.List;

public class CentrosListView extends AppCompatActivity implements CentrosListContract.view {

    public static List<Centro> centroList = new ArrayList<>();
    private CentroAdapter adapter;

    private CentrosListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centros);

        presenter = new CentrosListPresenter(this);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar al hacer clic en el ícono de navegación
                // Por ejemplo, cerrar la actividad o realizar alguna acción específica
                onBackPressed(); // Ejemplo: retroceder a la actividad anterior
            }
        });
        initializeRecyclerView();
    }

    private void initializeRecyclerView(){
        centroList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.centros_list);
        //esto le dice que tenga un tamaño fijo y que ocupe el máximo espacio asignado
        recyclerView.setHasFixedSize(true);
        //Esto le dice que lo va a gestionar un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //así se ciñe al Layout manager
        recyclerView.setLayoutManager(layoutManager);
        //hago mi adapter propio no utilizo el arrayadapter de android
        adapter = new CentroAdapter(this, centroList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllCentros();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registrar) {
            //Con Intent de digo donde estoy y a donde quiero ir
            Intent intent = new Intent(this, RegisterCentroView.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.view_map) { //Para cuando pulsan en la boton del mapa en el menu options
            Intent intent = new Intent(this, MapsActivityView.class); //donde nos manda al pinchar sobre el boton mapas en el menu options
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item); // Llamar al método de la superclase
    }

    public void cancel(View view) {
        onBackPressed();
    }

    @Override
    public void showCentros(List<Centro> centros) {
        centroList.clear();
        centroList.addAll(centros);
        // con esto la lista siempre estára actualizada cuando vuelva de un segundo plano.
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showMessage(String message) {

    }
}