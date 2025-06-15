package com.example.aniclips.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;
import com.example.aniclips.interfaces.FollowCallback;
import org.json.JSONObject;

import java.util.UUID;

public class FollowController extends AsyncTask<Void, Void, JSONObject> {
    private final Context context;
    private final UUID seguidoId;
    private final FollowCallback callback;
    private final String method;

    public FollowController(Context context, UUID seguidoId, String method, FollowCallback callback) {
        this.context = context;
        this.seguidoId = seguidoId;
        this.method = method;
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        String responseJSON;
        try {
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);
            if (token == null) return null;

            String url = "";

            switch (method) {
                case "POST":
                    JSONObject body = new JSONObject();
                    body.put("seguidoId", seguidoId);
                    url = "/seguir/";
                    responseJSON = OkHttpTools.postWithToken(url, body.toString(), token);
                    break;
                case "DELETE":
                    url = "/dejar-de-seguir/" + seguidoId;
                    long seguidoIdLong = seguidoId.getMostSignificantBits();
                    responseJSON = OkHttpTools.deleteWithToken(url, seguidoIdLong, token);
                    break;
                default:
                    return null;
            }
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
        } else if (response.optString("status").equalsIgnoreCase("ERROR")) {
            callback.onError(response.optString("message"));
        } else {
            callback.onFollowSuccess(response);
        }
    }
}