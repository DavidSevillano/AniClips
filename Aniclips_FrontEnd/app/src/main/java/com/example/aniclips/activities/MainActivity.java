package com.example.aniclips.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.controllers.UploadClipController;
import com.example.aniclips.fragments.HomeFragment;
import com.example.aniclips.fragments.ProfileFragment;
import com.example.aniclips.fragments.SearchFragment;
import com.example.aniclips.interfaces.PerfilCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.HideNavigationBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Uri videoUri = null;
    private Uri miniaturaUri = null;

    private AppCompatButton btnClipRef;
    private AppCompatButton btnMiniaturaRef;

    private final ActivityResultLauncher<Intent> videoPickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            videoUri = result.getData().getData();
                            if (btnClipRef != null) {
                                btnClipRef.setText("Clip introducido");
                                btnClipRef.setTextColor(android.graphics.Color.parseColor("#4CAF50"));
                            }
                        }
                    }
            );

    private final ActivityResultLauncher<Intent> miniaturaPickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            miniaturaUri = result.getData().getData();
                            if (btnMiniaturaRef != null) {
                                btnMiniaturaRef.setText("Miniatura introducida");
                                btnMiniaturaRef.setTextColor(android.graphics.Color.parseColor("#4CAF50"));
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideNavigationBar.hideNavigationBar(getWindow().getDecorView());
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            bottomNavigationView.setOnApplyWindowInsetsListener((v, insets) -> {
                v.setPadding(0, 0, 0, 0);
                return insets;
            });

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new HomeFragment())
                    .commit();

            bottomNavigationView.setOnItemSelectedListener(item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainContainer, selectedFragment)
                            .commit();
                }
                return true;
            });

            FloatingActionButton fab = findViewById(R.id.fabSubirClip);
            fab.setOnClickListener(v -> {
                View popupView = getLayoutInflater().inflate(R.layout.popup_subir_clip, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        (int) (200 * getResources().getDisplayMetrics().density),
                        (int) (60 * getResources().getDisplayMetrics().density),
                        true
                );

                popupWindow.setAnimationStyle(R.style.PopupAnimation);

                popupView.setOnClickListener(view -> {
                    popupWindow.dismiss();
                    boolean loggedIn = getSharedPreferences("My_prefs", MODE_PRIVATE).contains(Constantes.PREF_TOKEN_JWT);
                    android.util.Log.d("LOGIN_CHECK", "¿Logueado?: " + loggedIn);
                    if (!loggedIn) {
                        android.util.Log.d("LOGIN_CHECK", "Mostrando RegisterDialog");
                        new com.example.aniclips.dialogs.RegisterDialog()
                                .show(getSupportFragmentManager(), "RegisterDialog");
                    } else {
                        mostrarBottomSheet();
                    }
                });

                int[] location = new int[2];
                v.getLocationOnScreen(location);

                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
            });
        }
    }

    private void mostrarBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_subir_clip, null);
        bottomSheetDialog.setContentView(view);
        HideNavigationBar.hideNavigationBar(view);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        EditText etNombre = view.findViewById(R.id.etNombreAnime);
        AutoCompleteTextView etGenero = view.findViewById(R.id.etGenero);
        EditText etDescripcion = view.findViewById(R.id.etDescripcion);
        Button btnSubir = view.findViewById(R.id.btnSubir);
        btnClipRef = view.findViewById(R.id.btnSubirClip);
        btnMiniaturaRef = view.findViewById(R.id.btnSubirMiniatura);

        btnClipRef.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
            videoPickerLauncher.launch(intent);
        });

        btnMiniaturaRef.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            miniaturaPickerLauncher.launch(intent);
        });

        String[] generos = {"Shonen", "Shojo", "Seinen", "Josei", "Mecha", "Isekai", "Slice of Life", "Horror", "Comedia", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, generos);
        etGenero.setAdapter(adapter);
        etGenero.setThreshold(1);

        int itemHeight = (int) (48 * getResources().getDisplayMetrics().density);
        etGenero.setDropDownHeight(itemHeight * 4);
        etGenero.setDropDownVerticalOffset(0);

        etGenero.setOnClickListener(v -> etGenero.showDropDown());
        btnSubir.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String genero = etGenero.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (videoUri == null || miniaturaUri == null || nombre.isEmpty() || genero.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                new UploadClipController(
                        this,
                        new PerfilCallback() {
                            @Override
                            public void onPerfilSuccess(JSONObject response) {
                                Toast.makeText(MainActivity.this, "Clip subido exitosamente", Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                            }
                            @Override
                            public void onPerfilError(String error) {
                                Toast.makeText(MainActivity.this, "Error al subir el clip", Toast.LENGTH_SHORT).show();
                            }
                        },
                        nombre, genero, descripcion, videoUri, miniaturaUri
                ).execute();
            }
        });

        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Salir de la app")
                .setMessage("¿Estás seguro de que quieres salir de AniClips?")
                .setPositiveButton("Sí", (dialogInterface, which) -> finishAffinity())
                .setNegativeButton("No", null)
                .create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(android.R.color.white));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(android.R.color.white));
    }
}