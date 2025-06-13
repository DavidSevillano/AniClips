package com.example.aniclips.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aniclips.R;
import com.example.aniclips.adapters.MyClipsAdapter;
import com.example.aniclips.controllers.PerfilController;
import com.example.aniclips.dto.ClipDtoMiniatura;
import com.example.aniclips.interfaces.PerfilCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyClipsFragment extends Fragment {
    private RecyclerView rvClips;
    private TextView tvNoClips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_clips, container, false);
        rvClips = view.findViewById(R.id.rvClips);
        tvNoClips = view.findViewById(R.id.tvNoClips);
        rvClips.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));        loadClips();

        return view;
    }

    private void loadClips() {
        new PerfilController(getActivity(), new PerfilCallback() {
            @Override
            public void onPerfilSuccess(JSONObject perfil) {
                try {
                    JSONArray clipsArray = perfil.getJSONArray("clips");
                    List<ClipDtoMiniatura> clips = new ArrayList<>();
                    for (int i = 0; i < clipsArray.length(); i++) {
                        JSONObject obj = clipsArray.getJSONObject(i);
                        ClipDtoMiniatura clip = new ClipDtoMiniatura();
                        clip.setId(obj.getLong("id"));
                        clip.setMiniatura(obj.getString("miniatura"));
                        clip.setNombreAnime(obj.getString("nombreAnime"));
                        clips.add(clip);
                    }
                    if (clips.isEmpty()) {
                        tvNoClips.setVisibility(View.VISIBLE);
                        rvClips.setVisibility(View.GONE);
                    } else {
                        tvNoClips.setVisibility(View.GONE);
                        rvClips.setVisibility(View.VISIBLE);
                        rvClips.setAdapter(new MyClipsAdapter(requireActivity(), clips));
                    }
                } catch (Exception e) {
                    tvNoClips.setVisibility(View.VISIBLE);
                    rvClips.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPerfilError(String errorMsg) {
                tvNoClips.setVisibility(View.VISIBLE);
                rvClips.setVisibility(View.GONE);
            }
        }).execute();
    }
}