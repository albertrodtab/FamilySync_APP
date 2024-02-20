package com.alberto.familysyncapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.domain.Usuario;
import com.alberto.familysyncapp.presenter.usuario.LoginPresenter;
import com.alberto.familysyncapp.view.centro.CentrosListView;
import com.alberto.familysyncapp.view.comunidad.NoticiasListView;
import com.alberto.familysyncapp.view.profesional.ProfesionalesListView;
import com.alberto.familysyncapp.view.residente.ResidentesListView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivityViewLog extends AppCompatActivity{

    Button btCentros;
    Button btResidentes;
    Button btProfesionales;
    Button btComunidad;

    ImageButton ibCentros;
    ImageButton ibResidentes;
    ImageButton ibProfesionales;
    ImageButton ibComunidad;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar al hacer clic en el ícono de navegación
                // Por ejemplo, cerrar la actividad o realizar alguna acción específica
                //onBackPressed(); // Ejemplo: retroceder a la actividad anterior metodo deprecated
                TokenManager.clearRol(MainActivityViewLog.this);
                getOnBackPressedDispatcher().onBackPressed();
            }

        });



        // Obtén el rol del usuario
        String rol = TokenManager.getRol(this);

        btCentros = findViewById(R.id.elevatedButtonCentros);
        btProfesionales = findViewById(R.id.elevatedButtonProfesionales);
        btResidentes = findViewById(R.id.elevatedButtonResidentes);
        btComunidad = findViewById(R.id.elevatedButtonComunidad);

        ibCentros = findViewById(R.id.imageButtonCentros);
        ibResidentes = findViewById(R.id.imageButtonResidentes);
        ibProfesionales = findViewById(R.id.imageButtonProfesionales);
        ibComunidad = findViewById(R.id.imageButtonComunidad);

        // Controla qué botones mostrar basado en el rol del usuario
        if (rol.equals("admin")) {
            btCentros.setVisibility(View.VISIBLE);
            btResidentes.setVisibility(View.VISIBLE);
            btProfesionales.setVisibility(View.VISIBLE);
            btComunidad.setVisibility(View.VISIBLE);

        } else if (rol.equals("user")) {
            btCentros.setVisibility(View.GONE);
            ibCentros.setVisibility(View.GONE);
            btResidentes.setVisibility(View.VISIBLE);
            btProfesionales.setVisibility(View.VISIBLE);
            btComunidad.setVisibility(View.VISIBLE);
        }


        btCentros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityViewLog.this, CentrosListView.class));
            }
        });

        btProfesionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityViewLog.this, ProfesionalesListView.class));
            }
        });

        btResidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityViewLog.this, ResidentesListView.class));
            }
        });

        btComunidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityViewLog.this, NoticiasListView.class));
            }
        });
    }

}