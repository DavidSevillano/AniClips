package com.example.aniclips.interfaces;

import com.example.aniclips.dto.ClipDto;

import org.json.JSONObject;

import java.util.List;

public interface MeGustaCallback {
    void onMegustaSuccess(JSONObject meGustaResponse);
    void onError(String message);
}
