package com.example.aniclips.controllers;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.aniclips.interfaces.SearchThumbnailCallback;
import com.example.aniclips.models.Miniatura;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterController extends AsyncTask<Void, Void, List<Miniatura>> {
    private final Activity activity;
    private final SearchThumbnailCallback callback;
    private final String nombreAnime;
    private final int page;
    private final int size;

    public SearchFilterController(Activity activity, SearchThumbnailCallback callback, String nombreAnime, int page, int size) {
        this.activity = activity;
        this.callback = callback;
        this.nombreAnime = nombreAnime;
        this.page = page;
        this.size = size;
    }

    @Override
    protected List<Miniatura> doInBackground(Void... voids) {
        if (activity == null) return null;
        List<Miniatura> listaMiniaturas = new ArrayList<>();
        try {
            String searchParam = "nombreAnime:" + nombreAnime;
            String url = "/clip/buscar/?search=" + searchParam + "&page=" + page + "&size=" + size;
            String responseJSON = OkHttpTools.get(url, activity);
            JSONObject response = new JSONObject(responseJSON);

            JSONArray clipsArray = response.optJSONArray("content");

            if (clipsArray != null) {
                for (int i = 0; i < clipsArray.length(); i++) {
                    JSONObject miniaturaJson = clipsArray.getJSONObject(i);

                    Miniatura miniatura = new Miniatura();
                    miniatura.setId(miniaturaJson.optLong("id"));
                    miniatura.setNombreAnime(miniaturaJson.optString("nombreAnime"));
                    miniatura.setUrlMiniatura(miniaturaJson.optString("miniatura"));

                    listaMiniaturas.add(miniatura);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMiniaturas;
    }

    @Override
    protected void onPostExecute(List<Miniatura> listaMiniaturas) {
        if (listaMiniaturas == null || listaMiniaturas.isEmpty()) {
            callback.onError("No se encontraron resultados");
        } else {
            callback.onSearchThumbnailCallback(listaMiniaturas);
        }
    }
}