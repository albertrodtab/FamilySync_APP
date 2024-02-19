package com.alberto.familysyncapp.model.usuario;

import com.alberto.familysyncapp.api.GesResApi;
import com.alberto.familysyncapp.api.GesResApiInterface;
import com.alberto.familysyncapp.domain.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel {

    public interface OnLoginListener {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    public void login(Usuario usuario, OnLoginListener listener) {
        // Realizar la lógica de la llamada a la API aquí
        // Puedes mantener la lógica específica de Retrofit y la llamada a la API aquí
        GesResApiInterface apiInterface = GesResApi.buildInstance();
        Call<ResponseBody> loginCall = apiInterface.login(usuario);

        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Acceder al encabezado de Authorization para obtener el token
                    String token = response.headers().get("Authorization");

                    // Verificar si el token es null o vacío
                    if (token != null && !token.isEmpty()) {
                        // Llamar al método onSuccess con el token
                        listener.onSuccess(token);
                    } else {
                        // Si no se puede obtener el token, notificar un error
                        listener.onError("No se pudo obtener el token de la respuesta");
                    }
                } else {
                    // Manejar errores en la respuesta de la API
                    listener.onError("Error en la respuesta de la API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onError("Error en la llamada a la API: " + t.getMessage());
            }
        });
    }
}