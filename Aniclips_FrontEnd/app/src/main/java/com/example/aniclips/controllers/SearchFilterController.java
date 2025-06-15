package com.example.aniclips.controllers;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.aniclips.interfaces.SearchThumbnailCallback;
import com.example.aniclips.models.Miniatura;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SearchFilterController extends AsyncTask<Void, Void, List<Miniatura>> {
    private final Activity activity;
    private final SearchThumbnailCallback callback;
    private final String nombreAnime;
    private final String genero;
    private final Float minValoracion;
    private final Float maxValoracion;
    private final int page;
    private final int size;

    private final WeakReference<ProgressBar> progressBarRef;

    public SearchFilterController(Activity activity, SearchThumbnailCallback callback, String nombreAnime, String genero, Float minValoracion, Float maxValoracion, int page, int size, ProgressBar progressBar) {
        this.activity = activity;
        this.callback = callback;
        this.nombreAnime = nombreAnime;
        this.genero = genero;
        this.minValoracion = minValoracion;
        this.maxValoracion = maxValoracion;
        this.page = page;
        this.size = size;
        this.progressBarRef = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar = progressBarRef.get();
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Miniatura> doInBackground(Void... voids) {
        if (activity == null) return null;
        List<Miniatura> listaMiniaturas = new ArrayList<>();
        try {
            List<String> searchParams = new ArrayList<>();
            if (nombreAnime != null && !nombreAnime.isEmpty()) {
                searchParams.add("nombreAnime:" + nombreAnime);
            }
            if (genero != null && !genero.isEmpty()) {
                searchParams.add("genero:" + genero);
            }
            if (minValoracion != null) {
                searchParams.add("valoracion>=" + minValoracion);
            }
            if (maxValoracion != null) {
                searchParams.add("valoracion<=" + maxValoracion);
            }
            String searchParam = String.join(",", searchParams);
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
        ProgressBar progressBar = progressBarRef.get();
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (listaMiniaturas == null || listaMiniaturas.isEmpty()) {
            callback.onError("No se encontraron resultados");
        } else {
            callback.onSearchThumbnailCallback(listaMiniaturas);
        }
    }
}