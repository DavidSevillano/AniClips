package com.example.aniclips.interfaces;

import org.json.JSONObject;

public interface DeleteCallback {
    void onDeleteSuccess(JSONObject response);
    void onDeleteError(JSONObject error);
}