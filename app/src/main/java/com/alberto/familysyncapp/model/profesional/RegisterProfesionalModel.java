package com.alberto.familysyncapp.model.profesional;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.profesional.RegisterProfesionalContract;
import com.alberto.familysyncapp.domain.Profesional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProfesionalModel implements  RegisterProfesionalContract.Model{

    public boolean registerProfesional(Profesional profesional, OnRegisterProfesionalListener listener){
        try {
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            Call<Profesional> callProfesionales = apiInterface.addProfesional(profesional);
            Log.d("profesional", "Llamada desde model");
            callProfesionales.enqueue(new Callback<Profesional>() {
                @Override
                public void onResponse(Call<Profesional> call, Response<Profesional> response) {
                    Log.d("profesional", "Llamada desde model ok");
                    Profesional profesional = response.body();
                    listener.onRegisterProfesionalSuccess(profesional);
                }
                @Override
                public void onFailure(Call<Profesional> call, Throwable t) {
                    Log.d("profesional", "Llamada desde model error");
                    Log.d("profesional", t.getMessage());
                    t.printStackTrace();
                    String message = "Error al añadir el centro";
                    listener.onRegisterProfesionalError(message);
                }
            });


        } catch (SQLiteConstraintException sce){
            sce.printStackTrace();

        }

        return true;
    }

}
