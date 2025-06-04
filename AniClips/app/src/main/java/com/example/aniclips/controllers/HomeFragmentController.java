package com.example.aniclips.controllers;

import android.app.Activity;
import android.os.AsyncTask;


import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.dto.PerfilAvatarDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.interfaces.HomeClipsCallback;
import com.example.aniclips.models.Clip;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeFragmentController extends AsyncTask<Void, Void, List<ClipDto>> {
    private final Activity activity;
    private final HomeClipsCallback callback;
    private final int page;   // Página a cargar
    private final int size;   // Tamaño página (opcional si la API lo soporta)

    public HomeFragmentController(Activity activity, HomeClipsCallback callback, int page, int size) {
        this.activity = activity;
        this.callback = callback;
        this.page = page;
        this.size = size;
    }

    @Override
    protected List<ClipDto> doInBackground(Void... voids) {
        if (activity == null) return null;
        List<ClipDto> listaClips = new ArrayList<>();
        try {
            // Construir URL con page y size para paginación
            String url = "?page=" + page + "&size=" + size;
            String responseJSON = OkHttpTools.get(url, activity);
            JSONObject response = new JSONObject(responseJSON);

            JSONArray clipsArray = response.optJSONArray("content");

            if (clipsArray != null) {
                for (int i = 0; i < clipsArray.length(); i++) {
                    JSONObject clipJson = clipsArray.getJSONObject(i);

                    ClipDto clipDto = new ClipDto();
                    clipDto.setDescripcion(clipJson.optString("descripcion"));
                    clipDto.setUrlVideo(clipJson.optString("urlVideo"));
                    clipDto.setVisitas(clipJson.optInt("visitas"));
                    clipDto.setDuracion(clipJson.optInt("duracion"));
                    clipDto.setCantidadMeGusta(clipJson.optInt("cantidadMeGusta"));
                    clipDto.setCantidadComentarios(clipJson.optInt("cantidadComentarios"));
                    clipDto.setMediaValoraciones(clipJson.optDouble("mediaValoraciones"));

                    String fechaStr = clipJson.optString("fecha");
                    if (fechaStr != null && !fechaStr.isEmpty()) {
                        clipDto.setFecha(LocalDate.parse(fechaStr));
                    }

                    JSONObject usuarioJson = clipJson.optJSONObject("getUsuarioClipDto");
                    if (usuarioJson != null) {
                        UsuarioClipDto usuario = new UsuarioClipDto();
                        usuario.setUsername(usuarioJson.optString("Username"));

                        JSONObject avatarJson = usuarioJson.optJSONObject("getPerfilAvatarDto");
                        if (avatarJson != null) {
                            PerfilAvatarDto avatar = new PerfilAvatarDto();
                            avatar.setAvatar(avatarJson.optString("avatar"));
                            usuario.setGetPerfilAvatarDto(avatar);
                        }
                        clipDto.setGetUsuarioClipDto(usuario);
                    }

                    listaClips.add(clipDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClips;
    }

    @Override
    protected void onPostExecute(List<ClipDto> listaClips) {
        if (listaClips == null || listaClips.isEmpty()) {
            callback.onError("No se recibieron clips");
        } else {
            callback.onHomeClipsCallback(listaClips);
        }
    }
}
