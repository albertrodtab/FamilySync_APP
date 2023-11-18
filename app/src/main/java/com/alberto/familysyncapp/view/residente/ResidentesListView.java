package com.alberto.familysyncapp.view.residente;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.adapter.ResidenteAdapter;
import com.alberto.familysyncapp.contract.residente.ResidentesListContract;
import com.alberto.familysyncapp.domain.Residente;
import com.alberto.familysyncapp.presenter.residente.ResidentesListPresenter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class ResidentesListView extends AppCompatActivity implements ResidentesListContract.view {

    public static List<Residente> residentesList = new ArrayList<>();
    private ResidenteAdapter adapter;

    private ResidentesListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residentes);

        presenter = new ResidentesListPresenter(this);

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
        residentesList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.residentes_list);
        //esto le dice que tenga un tamaño fijo y que ocupe el máximo espacio asignado
        recyclerView.setHasFixedSize(true);
        //Esto le dice que lo va a gestionar un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //así se ciñe al Layout manager
        recyclerView.setLayoutManager(layoutManager);
        //hago mi adapter propio no utilizo el arrayadapter de android
        adapter = new ResidenteAdapter(this, residentesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllResidentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registrar) {
            //Con Intent de digo donde estoy y a donde quiero ir
            Intent intent = new Intent(this, RegisterResidenteActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public void cancel(View view) {
        onBackPressed();
    }

    @Override
    public void showResidentes(List<Residente> residentes) {
        residentesList.clear();
        residentesList.addAll(residentes);
        // con esto la lista siempre estára actualizada cuando vuelva de un segundo plano.
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {

    }
}