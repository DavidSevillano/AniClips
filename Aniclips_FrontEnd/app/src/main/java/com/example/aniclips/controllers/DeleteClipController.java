package com.example.aniclips.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.DeleteCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class DeleteClipController extends AsyncTask<Void, Void, JSONObject> {
    private final WeakReference<Context> contextRef;
    private final long clipId;
    private final DeleteCallback callback;

    public DeleteClipController(Context context, long clipId, DeleteCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.clipId = clipId;
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        Context context = contextRef.get();
        if (context == null) return null;

        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);

        if (token == null) return null;

        String url = "/clip/admin/delete/" + clipId;
        String response = OkHttpTools.deleteWithToken(url, clipId, token);

        if (response == null || response.trim().isEmpty()) {
            JSONObject json = new JSONObject();
            try {
                json.put("status", "OK");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        try {
            return new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        if (response != null && response.optString("status", "OK").equals("OK")) {
            callback.onDeleteSuccess(response);
        } else {
            callback.onDeleteError(response);
        }
    }
}