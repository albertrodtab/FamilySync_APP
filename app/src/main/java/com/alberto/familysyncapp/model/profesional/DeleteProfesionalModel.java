package com.alberto.familysyncapp.model.profesional;

import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.profesional.DeleteProfesionalContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProfesionalModel implements DeleteProfesionalContract.Model {

    @Override
    public void deleteProfesional(long id, OnDeleteProfesionalListener listener) {
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            Call<Void> call = apiInterface.removeProfesional(id);
            Log.d("Profesional", "Llamada desde model");
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("Profesional", "Llamada desde model ok");
                   if(response.code() == 418) {
                       listener.onDeleteProfesionalError("Error al borrar el profesional");
                   } else {
                       listener.onDeleteProfesionalSuccess("Profesional eliminado");
                   }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("Profesional", "Llamada desde model error");
                    Log.d("Profesional", t.getMessage());
                    t.printStackTrace();
                    String message = "Error al conseguir el profesional";
                    listener.onDeleteProfesionalError("Error al borrar el rofesional");
                }
            });
    }

}
