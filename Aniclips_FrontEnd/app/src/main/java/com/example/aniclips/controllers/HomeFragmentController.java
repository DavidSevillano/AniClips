package com.example.aniclips.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;


import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.dto.PerfilAvatarDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.interfaces.HomeClipsCallback;
import com.example.aniclips.models.Clip;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeFragmentController extends AsyncTask<Void, Void, List<ClipDto>> {
    private final Activity activity;
    private final HomeClipsCallback callback;
    private final int page;
    private final int size;
    private final Long clipId;

    public HomeFragmentController(Activity activity, HomeClipsCallback callback, int page, int size, Long clipId) {
        this.activity = activity;
        this.callback = callback;
        this.page = page;
        this.size = size;
        this.clipId = clipId;
    }

    @Override
    protected List<ClipDto> doInBackground(Void... voids) {
        if (activity == null) return null;
        List<ClipDto> listaClips = new ArrayList<>();
        try {
            Context context = activity.getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);
            String responseJSON;
            if (clipId != null) {
                responseJSON = OkHttpTools.getWithToken("/clip/" + clipId, token);
                JSONObject clipJson = new JSONObject(responseJSON);
                ClipDto clipDto = parseClipDto(clipJson);
                listaClips.add(clipDto);
            } else {
                String url = "/clip/?page=" + page + "&size=" + size;
                responseJSON = OkHttpTools.getWithToken(url, token);
                JSONObject response = new JSONObject(responseJSON);
                JSONArray clipsArray = response.optJSONArray("content");
                if (clipsArray != null) {
                    for (int i = 0; i < clipsArray.length(); i++) {
                        JSONObject clipJson = clipsArray.getJSONObject(i);
                        ClipDto clipDto = parseClipDto(clipJson);
                        listaClips.add(clipDto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClips;
    }

    private ClipDto parseClipDto(JSONObject clipJson) throws JSONException {
        ClipDto clipDto = new ClipDto();
        clipDto.setId(clipJson.optLong("id"));
        clipDto.setDescripcion(clipJson.optString("descripcion"));
        clipDto.setUrlVideo(clipJson.optString("urlVideo"));
        clipDto.setUrlMiniatura(clipJson.optString("urlMiniatura"));
        clipDto.setVisitas(clipJson.optInt("visitas"));
        clipDto.setDuracion(clipJson.optInt("duracion"));
        clipDto.setCantidadMeGusta(clipJson.optInt("cantidadMeGusta"));
        clipDto.setCantidadComentarios(clipJson.optInt("cantidadComentarios"));
        clipDto.setMediaValoraciones(clipJson.optDouble("mediaValoraciones"));
        clipDto.setLedioLike(clipJson.optBoolean("ledioLike"));
        clipDto.setLoRateo(clipJson.optBoolean("loRateo"));
        clipDto.setLoSigue(clipJson.optBoolean("loSigue"));
        clipJson.optString("fecha");

        JSONObject usuarioJson = clipJson.optJSONObject("getUsuarioClipDto");
        if (usuarioJson != null) {
            UsuarioClipDto usuario = new UsuarioClipDto();
            String idUserStr = usuarioJson.optString("idUser", null);
            usuario.setIdUser(UUID.fromString(idUserStr));
            usuario.setUsername(usuarioJson.optString("username"));
            JSONObject avatarJson = usuarioJson.optJSONObject("getPerfilAvatarDto");
            if (avatarJson != null) {
                PerfilAvatarDto avatar = new PerfilAvatarDto();
                avatar.setAvatar(avatarJson.optString("avatar"));
                usuario.setGetPerfilAvatarDto(avatar);
            }
            clipDto.setGetUsuarioClipDto(usuario);
        }
        return clipDto;
    }

    @Override
    protected void onPostExecute(List<ClipDto> listaClips) {
        if (listaClips == null || listaClips.isEmpty()) {
        } else {
            callback.onHomeClipsCallback(listaClips);
        }
    }
}
