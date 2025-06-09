package com.example.aniclips.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.MeGustaCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LikeController extends AsyncTask<Void, Void, JSONObject> {

    private final WeakReference<Context> contextRef;
    private final MeGustaCallback callback;
    private final long clipId;

    public LikeController(Context context, long clipId, MeGustaCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.clipId = clipId;
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

            String url = "/meGusta/" + clipId;

            String responseJSON = OkHttpTools.postWithToken(url, "{}", token);

            return new JSONObject(responseJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        if (response == null) {
            callback.onError("Sin respuesta del servidor");
        } else {
            callback.onMegustaSuccess(response);
        }
    }
}
