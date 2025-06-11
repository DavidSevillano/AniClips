// app/src/main/java/com/example/aniclips/controllers/DescriptionController.java
package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.PerfilCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

public class DescriptionController extends AsyncTask<Void, Void, JSONObject> {
    private final Activity activity;
    private final PerfilCallback callback;
    private final String descripcion;

    public DescriptionController(Activity activity, PerfilCallback callback, String descripcion) {
        this.activity = activity;
        this.callback = callback;
        this.descripcion = descripcion;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            Context context = activity.getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);

            JSONObject body = new JSONObject();
            body.put("descripcion", descripcion);

            String responseJSON = OkHttpTools.putWithToken("/perfil/descripcion/edit", body.toString(), token);
            return new JSONObject(responseJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject perfil) {
        if (perfil != null) {
            callback.onPerfilSuccess(perfil);
        } else {
            callback.onPerfilError("Error al actualizar la descripción");
        }
    }
}