package com.example.aniclips.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;
import com.example.aniclips.interfaces.PerfilCallback;

import org.json.JSONObject;

public class PerfilController extends AsyncTask<Void, Void, JSONObject> {
    private final Activity activity;
    private final PerfilCallback callback;
    private final boolean desdeDialogo;
    private final String userId;

    private ProgressDialog progressDialog;

    public PerfilController(Activity activity, PerfilCallback callback) {
        this(activity, callback, false);
    }

    public PerfilController(Activity activity, PerfilCallback callback, boolean desdeDialogo) {
        this.activity = activity;
        this.callback = callback;
        this.userId = null;
        this.desdeDialogo = desdeDialogo;
    }
    public PerfilController(Activity activity, PerfilCallback callback, String userId) {
        this.activity = activity;
        this.callback = callback;
        this.userId = userId;
        this.desdeDialogo = false;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            Context context = activity.getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);
            String responseJSON;
            if (userId != null) {
                responseJSON = OkHttpTools.getWithToken("/perfil/" + userId, token);
            } else if (desdeDialogo) {
                responseJSON = OkHttpTools.postWithToken("/auth/login", "", token);
            } else {
                responseJSON = OkHttpTools.getWithToken("/perfil/", token);
            }
            return new JSONObject(responseJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject perfil) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (perfil != null) {
            callback.onPerfilSuccess(perfil);
        } else {
            callback.onPerfilError("Error al obtener el perfil");
        }
    }
}