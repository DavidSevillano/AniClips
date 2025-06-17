package com.example.aniclips.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.aniclips.interfaces.LoginCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LoginController extends AsyncTask<Void, Void, JSONObject> {
    private final WeakReference<Context> contextRef;
    private final LoginCallback callback;
    private final String username;
    private final String password;
    private final WeakReference<ProgressBar> progressBarRef;


    public LoginController(Context context, String username, String password, LoginCallback callback, ProgressBar progressBar) {
        this.contextRef = new WeakReference<>(context);
        this.callback = callback;
        this.username = username;
        this.password = password;
        this.progressBarRef = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar = progressBarRef.get();
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            JSONObject loginJson = new JSONObject();
            loginJson.put("username", username);
            loginJson.put("password", password);

            String responseJSON = OkHttpTools.post("/auth/login", loginJson.toString());

            return new JSONObject(responseJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        Context context = contextRef.get();

        ProgressBar progressBar = progressBarRef.get();
        if (progressBar != null) progressBar.setVisibility(View.GONE);


        if (response == null || context == null) {
            callback.onLoginError(null);
            return;
        }

        if (response.has("detail")) {
            callback.onLoginError(response);
        } else {
            try {
                String token = response.getString("token");
                String id = response.getString("id");
                String avatar = response.getString("avatar");

                String rol = response.getJSONArray("roles").getString(0);

                SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
                prefs.edit().putString(Constantes.PREF_TOKEN_JWT, token).apply();
                prefs.edit().putString(Constantes.PREF_MY_USER_ID, id).apply();
                prefs.edit().putString(Constantes.PREF_USER_AVATAR, avatar).apply();
                prefs.edit().putString(Constantes.PREF_MY_USER_ROLE, rol).apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            callback.onLoginSuccess(response);
        }
    }
}