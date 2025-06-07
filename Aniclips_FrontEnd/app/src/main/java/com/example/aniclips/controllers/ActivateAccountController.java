package com.example.aniclips.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aniclips.interfaces.ActivateAccountCallback;
import com.example.aniclips.interfaces.CreateUserCallback;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ActivateAccountController extends AsyncTask<Void, Void,JSONObject> {
    private final WeakReference<Context> contextRef;
    private final ActivateAccountCallback callback;
    private final String code;


    public ActivateAccountController(Context context, String code, ActivateAccountCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.callback = callback;
        this.code = code;

    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        if (contextRef == null) return null;

        try {
            JSONObject loginJson = new JSONObject();
            loginJson.put("token", code);

            String responseJSON = OkHttpTools.post("/activate/account", loginJson.toString());
            JSONObject response = new JSONObject(responseJSON);

            Log.i("response", response.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject userJson) {
        if (userJson == null) {
            callback.onError("No se activó la cuenta, código inválido o expirado");
        } else if (userJson.has("username")) {
            String username = userJson.optString("username");
            callback.onAccountActivated(username);
        } else {
            callback.onError("Error inesperado al activar la cuenta");
        }
    }
}
