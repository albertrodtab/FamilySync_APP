package com.alberto.familysyncapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.domain.Usuario;
import com.alberto.familysyncapp.presenter.usuario.LoginPresenter;
import com.alberto.familysyncapp.view.centro.CentrosListView;
import com.alberto.familysyncapp.view.comunidad.NoticiasListView;
import com.alberto.familysyncapp.view.profesional.ProfesionalesListView;
import com.alberto.familysyncapp.view.residente.ResidentesListView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityView extends AppCompatActivity{

    Button btCentros;
    Button btProfesionales;
    Button btComunidad;

    Button btLogin;

    TextInputEditText etCorreo;
    TextInputEditText etContrasena;

    private LoginPresenter loginPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtén el rol del usuario
        String rol = TokenManager.getRol(this);


        // Inicializar el presentador
        loginPresenter = new LoginPresenter(this);


        btCentros = findViewById(R.id.elevatedButtonCentros);
        //btProfesionales = findViewById(R.id.elevatedButtonProfesionales);
        btComunidad = findViewById(R.id.elevatedButtonComunidad);
        etCorreo = findViewById(R.id.etemail);
        etContrasena = findViewById(R.id.etpassword);
        btLogin = findViewById(R.id.btlogin);

        /*btLogin.setOnClickListener(v-> {
            // Obtiene el correo y la contraseña ingresados
            String correo = etCorreo.getText().toString();
            String contrasena = etContrasena.getText().toString();

            // Realiza el login (puedes llamar al método MakeLogin que has creado)
            makeLogin(new Usuario(correo, contrasena));
        });*/

        // Configurar clic en el botón de login
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos del usuario desde las vistas
                String correo = Objects.requireNonNull(etCorreo.getText()).toString();
                String contrasena = Objects.requireNonNull(etContrasena.getText()).toString();

                // Crear un objeto Usuario con los datos
                Usuario usuario = new Usuario(correo, contrasena);

                // Llamar al método de login del presentador
                loginPresenter.performLogin(usuario);
            }
        });

        btCentros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityView.this, CentrosListView.class));
            }
        });

        /*btProfesionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityView.this, ProfesionalesListView.class));
            }
        });*/

        btComunidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityView.this, NoticiasListView.class));
            }
        });
    }

    public void onLoginSuccess(String token) {
        // Manejar el éxito del login, por ejemplo, navegar a la siguiente actividad

        Intent intent = new Intent(this, MainActivityViewLog.class);
        startActivity(intent);
        //finish();

        // Guardar el token
        TokenManager.saveToken(getApplicationContext(), token);

        // Obtener y mostrar el token en el log solo a efectos de depuración
        TokenManager.logToken(getApplicationContext());

    }

    public void onLoginError(String errorMessage) {
        // Manejar el error del login, por ejemplo, mostrar un mensaje al usuario
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }



    /*private void makeLogin(Usuario usuario) {

        GesResApiInterface apiInterface = GesResApi.buildInstance();
        Call<Usuario> login = apiInterface.login(usuario);
        Log.d("centros", "Llamada desde model");
        login.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("usuario", "Llamada desde model ok");
                List<Usuario> centroList = response.body();
                listener.onLoadCentrosSuccess(centroList);
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("usuario", "Llamada desde model error");
                Log.d("usuario", t.getMessage());
                t.printStackTrace();
                String message = "Error al conseguir el token";
                listener.onLoadCentrosError(t.getMessage());
            }
        });
    }*/

}