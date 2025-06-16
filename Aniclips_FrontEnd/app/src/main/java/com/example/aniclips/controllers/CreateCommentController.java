package com.example.aniclips.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class CreateCommentController extends AsyncTask<Void, Void, JSONObject> {

    public interface CreateCommentCallback {
        void onSuccess();
        void onError(String errorMsg);
    }

    private final WeakReference<Context> contextRef;
    private final long clipId;
    private final String texto;
    private final CreateCommentCallback callback;

    public CreateCommentController(Context context, long clipId, String texto, CreateCommentCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.clipId = clipId;
        this.texto = texto;
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        Context context = contextRef.get();
        if (context == null) return null;
        try {
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);
            if (token == null) return null;

            JSONObject body = new JSONObject();
            body.put("clipId", String.valueOf(clipId));
            body.put("texto", texto);

            String responseJSON = OkHttpTools.postWithToken("/comentario/", body.toString(), token);
            return new JSONObject(responseJSON);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        if (callback == null) return;
        if (response == null) {
            callback.onError("Error desconocido");
            return;
        }
        String status = response.optString("status", "ERROR");
        if ("OK".equals(status)) {
            callback.onSuccess();
        } else {
            String msg = response.optString("message", "Error al crear comentario");
            callback.onError(msg);
        }
    }
}