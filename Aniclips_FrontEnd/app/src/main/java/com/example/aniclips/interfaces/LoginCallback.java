package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface LoginCallback {
    void onLoginSuccess(JSONObject userJson);
    void onLoginError(JSONObject message);
}
