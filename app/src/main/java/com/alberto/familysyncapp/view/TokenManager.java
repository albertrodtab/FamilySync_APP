package com.alberto.familysyncapp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenManager {

    private static final String TOKEN_KEY = "token_key";

    private static final String ROL_KEY = "rol_key";
    private static final String PREFERENCES_NAME = "app_preferences";

    // Método para guardar el token en SharedPreferences
    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();

        String rol = extractRolFromToken(token);
        if (rol != null) {
            saveRol(context, rol);
        } else {
            String rolDefecto = "user";
            saveRol(context, rolDefecto);
        }

        Log.d("TokenManager", "Token guardado en SharedPreferences: " + token);
        Log.d("TokenManager", "Rol guardado en SharedPreferences: " + rol);
    }

    // Método para obtener el token de SharedPreferences
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    // Método para obtener el rol de SharedPreferences
    public static String getRol(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ROL_KEY, null);
    }

    // Método para decodificar el token y extraer el rol
    public static String extractRolFromToken(String token) {
        try {
            String[] splitToken = token.split("\\.");
            String base64EncodedBody = splitToken[1];
            String body = new String(Base64.decode(base64EncodedBody, Base64.DEFAULT));
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getString("rol");
        } catch (JSONException e) {
            Log.e("TokenManager", "Error al extraer el rol del token", e);
            return null;
        }
    }

    // Método para guardar el rol en SharedPreferences
    public static void saveRol(Context context, String rol) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ROL_KEY, rol);
        editor.apply();

        Log.d("TokenManager", "Rol guardado en SharedPreferences: " + rol);
    }

    public static void clearRol(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //esto elimina el rol y lo deja nulo
        //editor.remove(ROL_KEY);
        //de este modo establezco el rol en el rol por defecto user
        editor.putString(ROL_KEY, "user");
        editor.apply();
    }

    // Método para mostrar el token en el log (solo con fines de depuración)
    public static void logToken(Context context) {
        String retrievedToken = getToken(context);
        if (retrievedToken != null) {
            Log.d("Token", "Token recuperado: " + retrievedToken);
        } else {
            Log.e("Token", "Error al recuperar el token");
        }
    }
}
