package com.alberto.familysyncapp.model.centro;

import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.centro.DeleteCentroContract;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCentroModel implements DeleteCentroContract.Model {

    @Override
    public void deleteCentro(long id, OnDeleteCentroListener listener) {
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            Call<Void> call = apiInterface.removeCentro(id);
            Log.d("centros", "Llamada desde model");
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("centros", "Llamada desde model ok");
                   if(response.code() == 418) {
                       listener.onDeleteCentroError("Error al borrar el centro");
                   } else {
                       listener.onDeleteCentroSuccess("Centro eliminado");
                   }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("centros", "Llamada desde model error");
                    Log.d("centros", t.getMessage());
                    t.printStackTrace();
                    String message = "Error al conseguir todos los centros";
                    listener.onDeleteCentroError("Error al borrar el centro");
                }
            });
    }

}
