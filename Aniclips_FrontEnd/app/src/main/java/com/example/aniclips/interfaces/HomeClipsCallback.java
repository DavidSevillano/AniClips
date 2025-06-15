package com.example.aniclips.interfaces;

import com.example.aniclips.dto.ClipDto;

import java.util.List;

public interface HomeClipsCallback {
    void onClipsLoaded(List<ClipDto> clips);
    void onNoClips();
}
