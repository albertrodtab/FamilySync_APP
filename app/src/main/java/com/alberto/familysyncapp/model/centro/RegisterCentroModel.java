package com.alberto.familysyncapp.model.centro;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.centro.RegisterCentroContract;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.view.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCentroModel implements  RegisterCentroContract.Model{
    private Context context;



    public RegisterCentroModel(Context context) {
        this.context = context;
    }

    public boolean registerCentro(Centro centro, OnRegisterCentroListener listener){
        try {
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            // Obtén el token del TokenManager
            String token = TokenManager.getToken(context);
            Log.d("Token", token);
            // Asegúrate de que el token no sea nulo antes de realizar la llamada
            if (token != null) {
                // Agrega el encabezado Authorization con el token en la llamada a la API
                Call<Centro> callCentros = apiInterface.addCentro("Bearer " + token, centro);
                Log.d("centros", "Llamada desde model");
                callCentros.enqueue(new Callback<Centro>() {
                    @Override
                    public void onResponse(Call<Centro> call, Response<Centro> response) {
                        Log.d("centros", "Llamada desde model ok");
                        Centro centro = response.body();
                        listener.onRegisterCentroSuccess(centro);
                    }

                    @Override
                    public void onFailure(Call<Centro> call, Throwable t) {
                        Log.d("centros", "Llamada desde model error");
                        Log.d("centros", t.getMessage());
                        t.printStackTrace();
                        String message = "Error al añadir el centro";
                        listener.onRegisterCentroError(message);
                    }
                });
            }


        } catch (SQLiteConstraintException sce){
            sce.printStackTrace();

        }

        return true;
    }

}
