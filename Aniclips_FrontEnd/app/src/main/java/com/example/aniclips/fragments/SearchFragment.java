package com.example.aniclips.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aniclips.R;
import com.example.aniclips.adapters.ThumbnailsSearchAdapter;
import com.example.aniclips.controllers.SearchFragmentController;
import com.example.aniclips.interfaces.SearchThumbnailCallback;
import com.example.aniclips.models.Miniatura;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchThumbnailCallback {

    private RecyclerView recyclerViewSearch;
    private ThumbnailsSearchAdapter adapter;
    private GridLayoutManager layoutManager;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 0;
    private final int pageSize = 9;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerViewSearch = view.findViewById(R.id.rvSearch);
        recyclerViewSearch.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerViewSearch.setItemAnimator(null);

        layoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerViewSearch.setLayoutManager(layoutManager);

        adapter = new ThumbnailsSearchAdapter(new ArrayList<>());
        recyclerViewSearch.setAdapter(adapter);

        recyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        cargarMiniaturas(currentPage);
                    }
                }
            }
        });


        cargarMiniaturas(currentPage);

        return view;
    }

    private void cargarMiniaturas(int page) {
        new SearchFragmentController(requireActivity(), this, page, pageSize).execute();
    }

    @Override
    public void onSearchThumbnailCallback(List<Miniatura> miniaturas) {
        if (miniaturas.size() < pageSize) {
            isLastPage = true;
        }
        adapter.addThumbnail(miniaturas);
        isLoading = false;
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        isLoading = false;
    }
}