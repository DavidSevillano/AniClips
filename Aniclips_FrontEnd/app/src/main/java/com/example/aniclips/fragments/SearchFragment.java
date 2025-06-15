package com.example.aniclips.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aniclips.R;
import com.example.aniclips.adapters.ThumbnailsSearchAdapter;
import com.example.aniclips.controllers.SearchFilterController;
import com.example.aniclips.controllers.SearchFragmentController;
import com.example.aniclips.interfaces.SearchThumbnailCallback;
import com.example.aniclips.models.Miniatura;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchThumbnailCallback {

    private RecyclerView recyclerViewSearch;
    private ThumbnailsSearchAdapter adapter;
    private GridLayoutManager layoutManager;
    private View searchMainLayout;
    private EditText etSearch;
    private ProgressBar progressBar;


    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean filtrosAplicados = false;
    private int currentPage = 0;
    private final int pageSize = 18;
    private String currentSearch = "";
    private String generoSeleccionado = null;

    private String filtroGenero = null;
    private Float filtroMinValoracion = 0f;
    private Float filtroMaxValoracion = 5f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchMainLayout = view.findViewById(R.id.searchMainLayout);

        progressBar = view.findViewById(R.id.progressBar);

        recyclerViewSearch = view.findViewById(R.id.rvSearch);
        recyclerViewSearch.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerViewSearch.setItemAnimator(null);

        layoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerViewSearch.setLayoutManager(layoutManager);

        adapter = new ThumbnailsSearchAdapter(new ArrayList<>());
        recyclerViewSearch.setAdapter(adapter);

        etSearch = view.findViewById(R.id.etSearch);

        searchMainLayout.setVisibility(View.VISIBLE);
        searchMainLayout.setAlpha(0f);
        searchMainLayout.animate().alpha(1f).setDuration(250).start();

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                InputMethodManager imm =
                        (InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
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
                            new SearchFragmentController(requireActivity(), SearchFragment.this, currentPage, pageSize, progressBar).execute();
                        } else {
                            buscarPorNombreAnime(currentSearch);
                        }
                    }
                }
            }
        });

        TextView tvFiltros = view.findViewById(R.id.tvFiltros);
        tvFiltros.setOnClickListener(v -> mostrarSheetFiltrosLateral());

        cargarTodosLosClips();

        return view;
    }

    private void cargarTodosLosClips() {
        currentPage = 0;
        isLastPage = false;
        currentSearch = "";
        adapter.clearThumbnails();
        isLoading = true;
        new SearchFragmentController(requireActivity(), this, currentPage, pageSize, progressBar).execute();
    }

    private void buscarPorNombreAnime(String nombreAnime) {
        if (!nombreAnime.equals(currentSearch)) {
            currentPage = 0;
            isLastPage = false;
            adapter.clearThumbnails();
        }
        currentSearch = nombreAnime;
        isLoading = true;
        new SearchFilterController(requireActivity(), this, nombreAnime, null, null, null, currentPage, pageSize, progressBar).execute();
    }

    @Override
    public void onSearchThumbnailCallback(List<Miniatura> miniaturas) {
        if (miniaturas.size() < pageSize) {
            isLastPage = true;
        }
        adapter.addThumbnail(miniaturas);
        isLoading = false;
    }

    private void mostrarSheetFiltrosLateral() {
        Dialog dialog = new Dialog(requireContext(), R.style.RightSheetDialog);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_filtros, null);
        dialog.setContentView(sheetView);

        ImageButton btnCerrar = sheetView.findViewById(R.id.btnCerrarSheet);
        btnCerrar.setOnClickListener(v -> dialog.dismiss());

        Button[] botonesGenero = {
                sheetView.findViewById(R.id.btnShonen),
                sheetView.findViewById(R.id.btnShojo),
                sheetView.findViewById(R.id.btnSeinen),
                sheetView.findViewById(R.id.btnJosei),
                sheetView.findViewById(R.id.btnMecha),
                sheetView.findViewById(R.id.btnIsekai),
                sheetView.findViewById(R.id.btnSlice),
                sheetView.findViewById(R.id.btnHorror),
                sheetView.findViewById(R.id.btnComedia),
                sheetView.findViewById(R.id.btnOtros)
        };

        if (filtroGenero != null) {
            for (Button btn : botonesGenero) {
                if (btn.getText().toString().equals(filtroGenero)) {
                    btn.setBackgroundTintList(getResources().getColorStateList(R.color.btn_focused));
                    generoSeleccionado = filtroGenero;
                } else {
                    btn.setBackgroundTintList(getResources().getColorStateList(R.color.tv_profile));
                }
            }
        } else {
            for (Button btn : botonesGenero) {
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.tv_profile));
            }
            generoSeleccionado = null;
        }

        for (Button btn : botonesGenero) {
            btn.setOnClickListener(v -> {
                for (Button b : botonesGenero) {
                    b.setBackgroundTintList(getResources().getColorStateList(R.color.tv_profile));
                }
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.btn_focused));
                generoSeleccionado = btn.getText().toString();
            });
        }

        Window window = dialog.getWindow();
        if (window != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
            window.setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.END);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setWindowAnimations(R.style.RightSheetAnimation);
        }

        RangeSlider rangeSlider = sheetView.findViewById(R.id.rangeSliderValoracion);
        rangeSlider.setValues(filtroMinValoracion, filtroMaxValoracion);
        rangeSlider.setThumbTintList(ContextCompat.getColorStateList(requireContext(), R.color.btn_focused));
        rangeSlider.setTrackActiveTintList(ContextCompat.getColorStateList(requireContext(), R.color.btn_focused));
        rangeSlider.setTrackInactiveTintList(ContextCompat.getColorStateList(requireContext(), R.color.tv_profile));

        Button btnReiniciar = sheetView.findViewById(R.id.btnReiniciarFiltros);
        btnReiniciar.setOnClickListener(v -> {
            boolean sinFiltrosAplicados =
                    (filtroGenero == null || filtroGenero.isEmpty()) &&
                            filtroMinValoracion == 0f &&
                            filtroMaxValoracion == 5f;

            if (!filtrosAplicados || sinFiltrosAplicados) {
                Toast.makeText(requireContext(), "No hay ningún filtro que reiniciar", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Button b : botonesGenero) {
                b.setBackgroundTintList(getResources().getColorStateList(R.color.tv_profile));
            }
            generoSeleccionado = null;
            rangeSlider.setValues(0f, 5f);

            filtrosAplicados = false;
            filtroMinValoracion = 0f;
            filtroMaxValoracion = 5f;
            filtroGenero = null;

            cargarTodosLosClips();

            dialog.dismiss();
        });

        Button btnAplicar = sheetView.findViewById(R.id.btnAplicarFiltros);
        btnAplicar.setOnClickListener(v -> {
            float minValoracion = rangeSlider.getValues().get(0);
            float maxValoracion = rangeSlider.getValues().get(1);

            filtrosAplicados = true;
            filtroMinValoracion = minValoracion;
            filtroMaxValoracion = maxValoracion;
            filtroGenero = generoSeleccionado;

            adapter.clearThumbnails();

            new SearchFilterController(
                    requireActivity(),
                    this,
                    null,
                    filtroGenero,
                    filtroMinValoracion,
                    filtroMaxValoracion,
                    0,
                    pageSize,
                    progressBar
            ).execute();

            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onError(String message) {
        isLoading = false;
    }
}