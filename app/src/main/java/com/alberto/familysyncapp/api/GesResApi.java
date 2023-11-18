package com.alberto.familysyncapp.api;

import static com.alberto.familysyncapp.api.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GesResApi {

    //Parte del código que se dedicará a istanciar el objeto que se va a comunicar con la API.

    public static GesResApiInterface buildInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GesResApiInterface.class);
    }
}
