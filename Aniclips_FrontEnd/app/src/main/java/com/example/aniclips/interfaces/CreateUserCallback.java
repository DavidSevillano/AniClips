package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface CreateUserCallback {
    void onUserCreatedCallback(JSONObject userJson);
    void onError(String message);
}
