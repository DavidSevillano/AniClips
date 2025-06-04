package com.example.aniclips.dto;

import java.util.List;

public class ClipResponse {
    private List<ClipDto> content;

    public List<ClipDto> getContent() {
        return content;
    }

    public void setContent(List<ClipDto> content) {
        this.content = content;
    }
}
