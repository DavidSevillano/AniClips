package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.PerfilCallback;
import com.example.aniclips.utils.Constantes;

import org.json.JSONObject;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadClipController extends AsyncTask<Void, Void, JSONObject> {
    private final Activity activity;
    private final PerfilCallback callback;
    private final String nombreAnime;
    private final String genero;
    private final String descripcion;
    private final Uri videoUri;
    private final Uri miniaturaUri;

    public UploadClipController(Activity activity, PerfilCallback callback, String nombreAnime, String genero, String descripcion, Uri videoUri, Uri miniaturaUri) {
        this.activity = activity;
        this.callback = callback;
        this.nombreAnime = nombreAnime;
        this.genero = genero;
        this.descripcion = descripcion;
        this.videoUri = videoUri;
        this.miniaturaUri = miniaturaUri;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            Context context = activity.getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);

            InputStream videoStream = context.getContentResolver().openInputStream(videoUri);
            InputStream miniaturaStream = context.getContentResolver().openInputStream(miniaturaUri);

            byte[] videoBytes = videoStream.readAllBytes();
            byte[] miniaturaBytes = miniaturaStream.readAllBytes();

            OkHttpClient client = new OkHttpClient();

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("nombreAnime", nombreAnime)
                    .addFormDataPart("genero", genero)
                    .addFormDataPart("descripcion", descripcion)
                    .addFormDataPart("video", "clip.mp4", RequestBody.create(MediaType.parse("video/*"), videoBytes))
                    .addFormDataPart("miniatura", "miniatura.jpg", RequestBody.create(MediaType.parse("image/*"), miniaturaBytes));

            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(Constantes.API_BASE_URL + "/clip/")
                    .addHeader("Authorization", "Bearer " + token)
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseJSON = response.body().string();
                return new JSONObject(responseJSON);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result != null) {
            callback.onPerfilSuccess(result);
        } else {
            callback.onPerfilError("Error al subir el clip");
        }
    }
}