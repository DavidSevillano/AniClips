package com.example.aniclips.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aniclips.R;
import com.example.aniclips.adapters.ThumbnailsSearchAdapter;
import com.example.aniclips.controllers.SearchFilterController;
import com.example.aniclips.controllers.SearchFragmentController;
import com.example.aniclips.interfaces.SearchThumbnailCallback;
import com.example.aniclips.models.Miniatura;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchThumbnailCallback {

    private RecyclerView recyclerViewSearch;
    private ThumbnailsSearchAdapter adapter;
    private GridLayoutManager layoutManager;
    private View searchMainLayout;
    private EditText etSearch;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 0;
    private final int pageSize = 18;
    private String currentSearch = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchMainLayout = view.findViewById(R.id.searchMainLayout);

        recyclerViewSearch = view.findViewById(R.id.rvSearch);
        recyclerViewSearch.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerViewSearch.setItemAnimator(null);

        layoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerViewSearch.setLayoutManager(layoutManager);

        adapter = new ThumbnailsSearchAdapter(new ArrayList<>());
        recyclerViewSearch.setAdapter(adapter);

        etSearch = view.findViewById(R.id.etSearch);

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                android.view.inputmethod.InputMethodManager imm =
                        (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nombreAnime = s.toString().trim();
                if (nombreAnime.isEmpty()) {
                    cargarTodosLosClips();
                } else {
                    buscarPorNombreAnime(nombreAnime);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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
                        if (currentSearch.isEmpty()) {
                            new SearchFragmentController(requireActivity(), SearchFragment.this, currentPage, pageSize).execute();
                        } else {
                            buscarPorNombreAnime(currentSearch);
                        }
                    }
                }
            }
        });

        cargarTodosLosClips();

        return view;
    }

    private void cargarTodosLosClips() {
        currentPage = 0;
        isLastPage = false;
        currentSearch = "";
        adapter.clearThumbnails();
        isLoading = true;
        new SearchFragmentController(requireActivity(), this, currentPage, pageSize).execute();
    }

    private void buscarPorNombreAnime(String nombreAnime) {
        if (!nombreAnime.equals(currentSearch)) {
            currentPage = 0;
            isLastPage = false;
            adapter.clearThumbnails();
        }
        currentSearch = nombreAnime;
        isLoading = true;
        new SearchFilterController(requireActivity(), this, nombreAnime, currentPage, pageSize).execute();
    }

    @Override
    public void onSearchThumbnailCallback(List<Miniatura> miniaturas) {
        if (miniaturas.size() < pageSize) {
            isLastPage = true;
        }
        adapter.addThumbnail(miniaturas);
        isLoading = false;

        if (searchMainLayout.getVisibility() != View.VISIBLE) {
            searchMainLayout.setVisibility(View.VISIBLE);
            searchMainLayout.setAlpha(0f);
            searchMainLayout.animate().alpha(1f).setDuration(250).start();
        }
    }

    @Override
    public void onError(String message) {
        isLoading = false;
    }
}