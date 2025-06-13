package com.example.aniclips.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aniclips.R;
import com.example.aniclips.adapters.ClipsHomeAdapter;
import com.example.aniclips.controllers.HomeFragmentController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.interfaces.HomeClipsCallback;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeClipsCallback {

    private RecyclerView recyclerViewClips;
    private ClipsHomeAdapter adapter;
    private LinearLayoutManager layoutManager;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 0;
    private final int pageSize = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewClips = view.findViewById(R.id.rvHome);
        recyclerViewClips.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerViewClips.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewClips.setLayoutManager(layoutManager);

        adapter = new ClipsHomeAdapter(new ArrayList<>());
        recyclerViewClips.setAdapter(adapter);

        recyclerViewClips.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView,int dx,int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true;
                        currentPage++;
                        cargarClipsHome(currentPage);
                    }
                }
            }
        });

        cargarClipsHome(currentPage);

        return view;
    }

    private void cargarClipsHome(int page) {
        new HomeFragmentController(requireActivity(), this, page, pageSize, null).execute();
    }

    @Override
    public void onHomeClipsCallback(List<ClipDto> clips) {
        if (clips.size() < pageSize) {
            isLastPage = true;
        }
        adapter.addClips(clips);
        isLoading = false;
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        isLoading = false;
    }
}

