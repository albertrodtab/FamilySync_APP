package com.alberto.familysyncapp.api;

import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.domain.Noticias;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.domain.Residente;
import com.alberto.familysyncapp.domain.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GesResApiInterface {

    //Cuales son las operaciones a las que queremos dar visibilidad dentro de la app android

    //Centros
    @GET("centros")
    Call<List<Centro>> getCentros();

    @GET("centro/{id}")
    Call<Centro> getCentro(@Path("id") long id);

    @POST("centros")
    Call<Centro> addCentro(@Header("Authorization") String authorizationHeader, @Body Centro centro);

    @DELETE ("centro/{id}")
    Call<Void> removeCentro(@Path("id") long id);

    @PUT("centro/{id}")
    Call<Centro> modifyCentro(@Path("id") long id, @Body Centro centro);


    //Profesionales
    @GET("profesionales")
    Call<List<Profesional>> getProfesionales();

    @GET ("profesional/{id}")
    Call<Profesional> getProfesional(@Path("id") long id);

    @POST("profesionales")
    Call<Profesional> addProfesional(@Body Profesional profesional);

    @DELETE ("profesional/{id}")
    Call<Void> removeProfesional(@Path("id") long id);

    @PUT("profesional/{id}")
    Call<Profesional> modifyProfesional(@Path("id") long id, @Body Profesional profesional);

    //Noticias
    @GET("noticias")
    Call<List<Noticias>> getNoticias();





    //Residentes
    @GET("residentes")
    Call<List<Residente>> getResidentes();

    //Usuario
    @POST("login")
    Call<ResponseBody> login (@Body Usuario usuario);

    // Método adicional para obtener el token de las cabeceras
    @GET("token")
    Call<Void> obtenerToken(@Header("Authorization") String authorizationHeader);
}

