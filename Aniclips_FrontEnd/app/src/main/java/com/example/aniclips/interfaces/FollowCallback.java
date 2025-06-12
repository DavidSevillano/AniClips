package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface FollowCallback {
    void onFollowSuccess(JSONObject response);
    void onError(String errorMsg);
}