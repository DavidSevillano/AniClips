package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.PerfilCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONObject;

import java.io.InputStream;

public class ProfilePictureController extends AsyncTask<Void, Void, JSONObject> {
    private final Activity activity;
    private final PerfilCallback callback;
    private final Uri imageUri;

    public ProfilePictureController(Activity activity, PerfilCallback callback, Uri imageUri) {
        this.activity = activity;
        this.callback = callback;
        this.imageUri = imageUri;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            Context context = activity.getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);

            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            String responseJSON = OkHttpTools.putImageWithToken("/perfil/foto/", inputStream, token);
            return new JSONObject(responseJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject perfil) {
        if (perfil != null) {
            String avatar = perfil.optString("avatar", null);
            if (avatar != null) {
                Context context = activity.getApplicationContext();
                SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constantes.PREF_USER_AVATAR, avatar);
                editor.apply();
            }
            callback.onPerfilSuccess(perfil);
        } else {
            callback.onPerfilError("Error al subir la foto");
        }
    }
}