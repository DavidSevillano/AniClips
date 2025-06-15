package com.example.aniclips.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aniclips.R;
import com.example.aniclips.adapters.ClipsHomeAdapter;
import com.example.aniclips.controllers.HomeFragmentController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.dialogs.RegisterDialog;
import com.example.aniclips.interfaces.HomeClipsCallback;
import com.example.aniclips.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeClipsCallback {

    private RecyclerView recyclerViewClips;
    private ClipsHomeAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

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
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new ClipsHomeAdapter(new ArrayList<>());

        adapter.setOnClipsEmptyListener(() -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new NoClipsFragment())
                    .commit();
        });

        recyclerViewClips.setAdapter(adapter);

        adapter.setOnUserClickListener(userId -> {
            ProfileFragment fragment = ProfileFragment.newInstance(userId.toString());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerViewClips.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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
        new HomeFragmentController(requireActivity(), this, page, pageSize, null, progressBar).execute();
    }

    @Override
    public void onClipsLoaded(List<ClipDto> clips) {
        if ((clips == null || clips.isEmpty()) && currentPage == 0) {
            onNoClips();
            return;
        }
        if (clips == null || clips.isEmpty()) {
            isLastPage = true;
            isLoading = false;
            return;
        }
        LinearLayout homeMainLayout = getView().findViewById(R.id.homeMainLayout);

        if (clips.size() < pageSize) {
            isLastPage = true;
        }
        adapter.addClips(clips);
        isLoading = false;

        if (homeMainLayout != null && homeMainLayout.getVisibility() != View.VISIBLE) {
            homeMainLayout.setVisibility(View.VISIBLE);
            homeMainLayout.setAlpha(0f);
            homeMainLayout.animate().alpha(1f).setDuration(250).start();
        }
    }

    @Override
    public void onNoClips() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, new NoClipsFragment())
                .commit();
    }
}