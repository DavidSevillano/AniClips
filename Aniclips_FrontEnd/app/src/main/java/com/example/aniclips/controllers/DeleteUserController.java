package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aniclips.interfaces.DeleteCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

public class DeleteUserController extends AsyncTask<Void, Void, JSONObject> {
    private final Context context;
    private final String userId;
    private final DeleteCallback callback;

    public DeleteUserController(Context context, String userId, DeleteCallback callback) {
        this.context = context;
        this.userId = userId;
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        String token = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE)
                .getString(Constantes.PREF_TOKEN_JWT, null);
        String resp = OkHttpTools.deleteWithToken("/usuario/" + userId, null, token);
        if (resp == null || resp.trim().isEmpty()) {
            return new JSONObject();
        }
        try {
            return new JSONObject(resp);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        if (response != null && !response.has("error")) {
            callback.onDeleteSuccess(response);
        } else {
            callback.onDeleteError(response);
        }
    }
}