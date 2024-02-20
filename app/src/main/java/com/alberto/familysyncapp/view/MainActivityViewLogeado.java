package com.alberto.familysyncapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.domain.Usuario;
import com.alberto.familysyncapp.presenter.usuario.LoginPresenter;
import com.alberto.familysyncapp.view.centro.CentrosListView;
import com.alberto.familysyncapp.view.profesional.ProfesionalesListView;
import com.alberto.familysyncapp.view.residente.ResidentesListView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivityViewLogeado extends AppCompatActivity {
    Button btCentros;
    Button btProfesionales;
    Button btResidentes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logeado);

        btProfesionales = findViewById(R.id.elevatedButtonProfesionales);
        btResidentes = findViewById(R.id.elevatedButtonResidentes);


        btProfesionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityViewLogeado.this, ProfesionalesListView.class));
            }
        });

        btResidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityViewLogeado.this, ResidentesListView.class));
            }
        });
    }

}
