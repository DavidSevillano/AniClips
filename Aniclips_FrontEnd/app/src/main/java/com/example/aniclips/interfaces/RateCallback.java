package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface RateCallback {
    void onRateSuccess(JSONObject rateResponse);
    void onError(String message);
}
