package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface ActivateAccountCallback {
    void onAccountActivated(String username);
    void onError(String message);
}
