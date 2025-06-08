package com.example.AniClips.query;

import com.example.AniClips.model.Clip;
import com.example.AniClips.util.SearchCriteria;

import java.util.List;

public class ClipSpecificationBuilder extends GenericSpecificationBuilder<Clip> {
    public ClipSpecificationBuilder(List<SearchCriteria> params) {
        super(params);
    }
}