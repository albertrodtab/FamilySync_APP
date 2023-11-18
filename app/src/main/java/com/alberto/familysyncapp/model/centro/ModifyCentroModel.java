package com.alberto.familysyncapp.model.centro;

import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.centro.ModifyCentroContract;
import com.alberto.familysyncapp.domain.Centro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyCentroModel implements ModifyCentroContract.Model {

    @Override
    public void modifyCentro(Centro centro, OnModifyCentroListener listener) {
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            Call<Centro> centroCall = apiInterface.modifyCentro(centro.getId(), centro);
            Log.d("centros", "Llamada desde model");
            centroCall.enqueue(new Callback<Centro>() {
                @Override
                public void onResponse(Call<Centro> call, Response<Centro> response) {
                    Log.d("centros", "Llamada desde model ok");
                       listener.onModifyCentroSuccess(response.body());
                }
                @Override
                public void onFailure(Call<Centro> call, Throwable t) {
                    Log.d("centros", "Llamada desde model error");
                    Log.d("centros", t.getMessage());
                    t.printStackTrace();
                    String message = "Error al conseguir todos los centros";
                    listener.onModifyCentroError(message);
                }
            });
    }

}
