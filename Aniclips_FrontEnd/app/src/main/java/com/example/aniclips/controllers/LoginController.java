package com.example.aniclips.controllers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.LoginCallback;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LoginController extends AsyncTask<Void, Void, JSONObject> {
    private final WeakReference<Context> contextRef;
    private final LoginCallback callback;
    private final String username;
    private final String password;

    public LoginController(Context context, String username, String password, LoginCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.callback = callback;
        this.username = username;
        this.password = password;
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
        if (response == null) {
            callback.onLoginError(null);
        } else if (response.has("detail")) {
            callback.onLoginError(response);
        } else {
            callback.onLoginSuccess(response);
        }
    }
}