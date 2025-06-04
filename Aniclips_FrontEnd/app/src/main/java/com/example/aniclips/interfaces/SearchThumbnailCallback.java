package com.example.aniclips.interfaces;

import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.models.Miniatura;

import java.util.List;

public interface SearchThumbnailCallback {
    void onSearchThumbnailCallback(List<Miniatura> miniaturas);
    void onError(String message);
}
