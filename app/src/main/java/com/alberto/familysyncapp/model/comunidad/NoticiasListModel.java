package com.alberto.familysyncapp.model.comunidad;

import android.content.Context;
import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.comunicadad.NoticiasListContract;
import com.alberto.familysyncapp.contract.residente.ResidentesListContract;
import com.alberto.familysyncapp.domain.Noticias;
import com.alberto.familysyncapp.domain.Residente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiasListModel implements NoticiasListContract.Model {

    private Context context;

    @Override
    public void loadAllNoticias(OnLoadNoticiasListener listener) {
        GesResApiInterface apiInterface = GesResApi.buildInstance();
        Call<List<Noticias>> callNoticias = apiInterface.getNoticias();
        Log.d("Noticias", "Llamada desde model");
        callNoticias.enqueue(new Callback<List<Noticias>>() {
            @Override
            public void onResponse(Call<List<Noticias>> call, Response<List<Noticias>> response) {
                Log.d("Noticias", "Llamada desde model ok");
                List<Noticias> noticiasList = response.body();
                listener.onLoadNoticiasSuccess(noticiasList);
            }
            @Override
            public void onFailure(Call<List<Noticias>> call, Throwable t) {
                Log.d("Noticias", "Llamada desde model error");
                Log.d("Noticias", t.getMessage());
                t.printStackTrace();
                String message = "Error al conseguir todos las Noticias";
                listener.onLoadNoticiasError(t.getMessage());
            }
        });
    }

}
