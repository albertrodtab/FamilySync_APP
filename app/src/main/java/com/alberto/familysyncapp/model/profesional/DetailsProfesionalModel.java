package com.alberto.familysyncapp.model.profesional;

import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.profesional.DetailsProfesionalContract;
import com.alberto.familysyncapp.domain.Profesional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsProfesionalModel implements DetailsProfesionalContract.Model {

    @Override
    public void loadProfesional (long id, OnLoadProfesionalListener listener) {
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            Call<Profesional> profesionalCall = apiInterface.getProfesional(id);
            Log.d("Profesional", "Llamada desde model");
            profesionalCall.enqueue(new Callback<Profesional>() {
                @Override
                public void onResponse(Call<Profesional> call, Response<Profesional> response) {
                    Log.d("Profesional", "Llamada desde model ok");
                       listener.onLoadProfesionalSuccess(response.body());
                }
                @Override
                public void onFailure(Call<Profesional> call, Throwable t) {
                    Log.d("Profesional", "Llamada desde model error");
                    Log.d("Profesional", t.getMessage());
                    t.printStackTrace();
                    String message = "Error al conseguir todos los el profesional";
                    listener.onLoadProfesionalError(message);
                }
            });
    }

}
