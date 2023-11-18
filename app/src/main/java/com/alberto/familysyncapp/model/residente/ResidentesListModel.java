package com.alberto.familysyncapp.model.residente;

import android.content.Context;
import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.residente.ResidentesListContract;
import com.alberto.familysyncapp.domain.Residente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidentesListModel implements ResidentesListContract.Model {

    private Context context;

    @Override
    public void loadAllResidentes(ResidentesListContract.Model.OnLoadResidenteListener listener) {
        GesResApiInterface apiInterface = GesResApi.buildInstance();
        Call<List<Residente>> callResidentes = apiInterface.getResidentes();
        Log.d("Residentes", "Llamada desde model");
        callResidentes.enqueue(new Callback<List<Residente>>() {
            @Override
            public void onResponse(Call<List<Residente>> call, Response<List<Residente>> response) {
                Log.d("residentes", "Llamada desde model ok");
                List<Residente> residenteList = response.body();
                listener.onLoadResidenteSuccess(residenteList);
            }
            @Override
            public void onFailure(Call<List<Residente>> call, Throwable t) {
                Log.d("residentes", "Llamada desde model error");
                Log.d("residentes", t.getMessage());
                t.printStackTrace();
                String message = "Error al conseguir todos los residentes";
                listener.onLoadResidenteError(t.getMessage());
            }
        });
    }

}
