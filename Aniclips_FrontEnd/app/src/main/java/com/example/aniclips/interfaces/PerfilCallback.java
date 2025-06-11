package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface PerfilCallback {
    void onPerfilSuccess(JSONObject perfil);
    void onPerfilError(String error);
}