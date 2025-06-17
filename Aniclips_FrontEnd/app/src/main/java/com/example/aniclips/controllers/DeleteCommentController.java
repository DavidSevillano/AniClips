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

public class DeleteCommentController extends AsyncTask<Void, Void, JSONObject> {
    private final WeakReference<Context> contextRef;
    private final long commentId;
    private final DeleteCallback callback;
    private final boolean isAdmin;

    public DeleteCommentController(Context context, long commentId, DeleteCallback callback, boolean isAdmin) {
        this.contextRef = new WeakReference<>(context);
        this.commentId = commentId;
        this.callback = callback;
        this.isAdmin = isAdmin;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        Context context = contextRef.get();
        if (context == null) return null;

        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);

        if (token == null) return null;

        String url;
        if (isAdmin) {
            url = "/comentario/admin/delete/" + commentId;
        } else {
            url = "/comentario/delete/" + commentId;
        }
        String response = OkHttpTools.deleteWithToken(url, commentId, token);

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