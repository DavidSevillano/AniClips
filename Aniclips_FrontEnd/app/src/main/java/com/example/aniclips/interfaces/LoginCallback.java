package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface LoginCallback {
    void onUserLogin(JSONObject userJson);
    void onError(String message);
}
