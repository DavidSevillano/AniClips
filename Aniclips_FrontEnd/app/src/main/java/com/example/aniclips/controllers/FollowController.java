package com.example.aniclips.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;
import com.example.aniclips.interfaces.FollowCallback;
import org.json.JSONObject;

import java.util.UUID;

public class FollowController extends AsyncTask<Void, Void, String> {
    private final Context context;
    private final UUID seguidoId;
    private final FollowCallback callback;

    public FollowController(Context context, UUID seguidoId, FollowCallback callback) {
        this.context = context;
        this.seguidoId = seguidoId;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            JSONObject body = new JSONObject();
            body.put("seguidoId", seguidoId);

            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);

            return OkHttpTools.postWithToken("/seguir/", body.toString(), token);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"ERROR\",\"message\":\"Exception\"}";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject response = new JSONObject(result);
            if (response.optString("status").equalsIgnoreCase("ERROR")) {
                callback.onError(response.optString("message"));
            } else {
                callback.onFollowSuccess(response);
            }
        } catch (Exception e) {
            callback.onError("Error parsing response");
        }
    }
}