package com.alberto.familysyncapp.model.centro;

import android.content.Context;
import android.util.Log;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.contract.centro.CentrosListMapsContract;
import com.alberto.familysyncapp.domain.Centro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentrosListMapsModel implements CentrosListMapsContract.Model {

    private Context context;

    //public CentrosListModel(Context context) {
        //this.context = context;
    //}
    @Override
    public void loadAllCentros(OnLoadCentroListener listener) {
        /*final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        return db.centroDao().getAll();*/
            GesResApiInterface apiInterface = GesResApi.buildInstance();
            Call<List<Centro>> callCentros = apiInterface.getCentros();
            Log.d("centros", "Llamada desde model");
            callCentros.enqueue(new Callback<List<Centro>>() {
                @Override
                public void onResponse(Call<List<Centro>> call, Response<List<Centro>> response) {
                    Log.d("centros", "Llamada desde model ok");
                    List<Centro> centroList = response.body();
                    listener.onLoadCentrosSuccess(centroList);
                }
                @Override
                public void onFailure(Call<List<Centro>> call, Throwable t) {
                    Log.d("centros", "Llamada desde model error");
                    Log.d("centros", t.getMessage());
                    t.printStackTrace();
                    String message = "Error al conseguir todos los centros";
                    listener.onLoadCentrosError(t.getMessage());
                }
            });
    }


/*    @Override
    public List<Centro> loadCentrosByName(String name) {
        return null;
    }

    @Override
    public boolean deleteCentro(String name) {
        return false;
    }*/
}
