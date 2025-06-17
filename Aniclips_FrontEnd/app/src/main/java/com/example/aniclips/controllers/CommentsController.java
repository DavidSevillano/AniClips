package com.example.aniclips.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.aniclips.dto.ComentarioDto;
import com.example.aniclips.dto.PerfilAvatarDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.OkHttpTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentsController extends AsyncTask<Void, Void, List<ComentarioDto>> {

    public interface CommentsCallback {
        void onCommentsSuccess(List<ComentarioDto> comentarios);
        void onError(String errorMsg);
    }

    private final WeakReference<Context> contextRef;
    private final long clipId;
    private final CommentsCallback callback;

    public CommentsController(Context context, long clipId, CommentsCallback callback) {
        this.contextRef = new WeakReference<>(context);
        this.clipId = clipId;
        this.callback = callback;
    }

    @Override
    protected List<ComentarioDto> doInBackground(Void... voids) {
        Context context = contextRef.get();
        if (context == null) return null;

        List<ComentarioDto> listaComentarios = new ArrayList<>();
        try {
            SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
            String token = prefs.getString(Constantes.PREF_TOKEN_JWT, null);
            if (token == null) return null;

            String url = "/comentario/?clipId=" + clipId;
            String response = OkHttpTools.getWithToken(url, token);

            JSONObject obj = new JSONObject(response);
            JSONArray content;
            if (obj.has("content")) {
                content = obj.getJSONArray("content");
            } else if (obj.has("status") && obj.getString("status").equals("ERROR")) {
                return null;
            } else {
                content = new JSONArray(response);
            }

            for (int i = 0; i < content.length(); i++) {
                JSONObject comentarioJson = content.getJSONObject(i);
                ComentarioDto comentario = parseComentarioDto(comentarioJson);
                listaComentarios.add(comentario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listaComentarios;
    }

    private ComentarioDto parseComentarioDto(JSONObject comentarioJson) {
        ComentarioDto comentario = new ComentarioDto();
        comentario.setId(comentarioJson.optLong("id", 0));
        comentario.setTexto(comentarioJson.optString("texto", ""));
        try {
            comentario.setFecha(LocalDate.parse(comentarioJson.optString("fecha", "")));
        } catch (Exception e) {
            comentario.setFecha(null);
        }

        JSONObject usuarioJson = comentarioJson.optJSONObject("getUsuarioClipDto");
        if (usuarioJson != null) {
            UsuarioClipDto usuario = new UsuarioClipDto();
            String idUserStr = usuarioJson.optString("idUser", null);
            if (idUserStr != null) {
                usuario.setIdUser(UUID.fromString(idUserStr));
            }
            usuario.setUsername(usuarioJson.optString("username", ""));
            JSONObject avatarJson = usuarioJson.optJSONObject("getPerfilAvatarDto");
            if (avatarJson != null) {
                PerfilAvatarDto avatar = new PerfilAvatarDto();
                avatar.setAvatar(avatarJson.optString("avatar", ""));
                usuario.setGetPerfilAvatarDto(avatar);
            }
            comentario.setGetUsuarioClipDto(usuario);
        }
        return comentario;
    }

    @Override
    protected void onPostExecute(List<ComentarioDto> comentarios) {
        if (comentarios == null) {
            callback.onError("No se pudieron obtener los comentarios");
        } else {
            callback.onCommentsSuccess(comentarios);
        }
    }
}