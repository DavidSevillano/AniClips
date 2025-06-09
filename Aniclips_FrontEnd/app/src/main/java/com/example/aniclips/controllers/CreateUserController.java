package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.dto.PerfilAvatarDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.interfaces.CreateUserCallback;
import com.example.aniclips.interfaces.HomeClipsCallback;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateUserController extends AsyncTask<Void, Void,JSONObject> {
    private final WeakReference<Context> contextRef;
    private final CreateUserCallback callback;
    private final String email;
    private final String username;
    private final String password;
    private final String verifyPassword;

    public CreateUserController(Context context, String email, String username, String password, String verifyPassword, CreateUserCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.callback = callback;
        this.email = email;
        this.username = username;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        if (contextRef == null) return null;

        try {
            JSONObject loginJson = new JSONObject();
            loginJson.put("username", username);
            loginJson.put("email", email);
            loginJson.put("password", password);
            loginJson.put("verifyPassword", verifyPassword);

            String responseJSON = OkHttpTools.post("/auth/register", loginJson.toString());
            JSONObject response = new JSONObject(responseJSON);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject userJson) {
        if (userJson == null) {
            callback.onError("No se recibió el usuario");
        } else {
            callback.onUserCreatedCallback(userJson);
        }
    }
}
