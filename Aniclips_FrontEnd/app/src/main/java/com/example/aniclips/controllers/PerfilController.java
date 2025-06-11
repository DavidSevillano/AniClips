package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;
import com.example.aniclips.interfaces.PerfilCallback;

import org.json.JSONObject;

public class PerfilController extends AsyncTask<Void, Void, JSONObject> {
    private final Activity activity;
    private final PerfilCallback callback;

    public PerfilController(Activity activity, PerfilCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            Context context = activity.getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);
            String responseJSON = OkHttpTools.getWithToken("/perfil/", token);
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
            callback.onPerfilError("Error al obtener el perfil");
        }
    }
}