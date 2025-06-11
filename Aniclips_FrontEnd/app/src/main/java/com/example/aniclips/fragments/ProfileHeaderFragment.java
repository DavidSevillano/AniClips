package com.example.aniclips.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.activities.LoginSiginActivity;
import com.example.aniclips.controllers.DescriptionController;
import com.example.aniclips.controllers.PerfilController;
import com.example.aniclips.controllers.ProfilePictureController;
import com.example.aniclips.dialogs.DialogCerrarSesion;
import com.example.aniclips.interfaces.PerfilCallback;
import com.example.aniclips.interfaces.SalirDialogListener;

import org.json.JSONObject;

public class ProfileHeaderFragment extends Fragment implements SalirDialogListener {

    private TextView tvUsername;
    private TextView tvClipsNumber;
    private TextView tvFollowersNumber;
    private TextView tvFollowingNumber;
    private EditText tvDescriptionEmpty;
    private TextView tvDescriptionFilled;
    private TextView tvChangeProfilePicture;
    private TextView tvGuardarDescription;
    private ImageButton ibLogout;
    private ImageButton ibProfile;

    private static final int PICK_IMAGE_REQUEST = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_header, container, false);
        initViews(view);
        initEvents();
        cargarPerfil();
        return view;
    }

    private void initViews(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        ibLogout = view.findViewById(R.id.ibLogout);
        ibProfile = view.findViewById(R.id.ibProfile);
        tvClipsNumber = view.findViewById(R.id.tvClipsNumber);
        tvFollowersNumber = view.findViewById(R.id.tvFollowersNumber);
        tvFollowingNumber = view.findViewById(R.id.tvFollowingNumber);
        tvDescriptionEmpty = view.findViewById(R.id.tvDescriptionEmpty);
        tvDescriptionFilled = view.findViewById(R.id.tvDescriptionFilled);
        tvChangeProfilePicture = view.findViewById(R.id.tvChangeProfilePicture);
        tvGuardarDescription = view.findViewById(R.id.tvGuardarDescription);
    }

    private void initEvents() {
        ibLogout.setOnClickListener(v -> mostrarDialogoSalir());

        ibProfile.setOnClickListener(v -> openImagePicker());

        tvChangeProfilePicture.setOnClickListener(v -> openImagePicker());

        setupDescriptionEdit();

        tvDescriptionEmpty.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                tvDescriptionEmpty.setHint("");
            } else {
                if (tvDescriptionEmpty.getText().toString().isEmpty()) {
                    tvDescriptionEmpty.setHint("Añade tu descripción aquí");
                }
            }
        });
    }

    private void cargarPerfil() {
        new PerfilController(requireActivity(), new PerfilCallback() {
            @Override
            public void onPerfilSuccess(JSONObject perfil) {
                String username = perfil.optString("username", "");
                String description = perfil.optString("descripcion", "");
                int clipsNumber = perfil.optInt("numeroClips", 0);
                int followersNumber = perfil.optInt("numeroSeguidores", 0);
                int followingNumber = perfil.optInt("numeroSeguidos", 0);

                tvUsername.setText(username);
                tvClipsNumber.setText(String.valueOf(clipsNumber));
                tvFollowersNumber.setText(String.valueOf(followersNumber));
                tvFollowingNumber.setText(String.valueOf(followingNumber));

                if (description == null || description.isEmpty()) {
                    tvDescriptionEmpty.setVisibility(VISIBLE);
                    tvDescriptionFilled.setVisibility(GONE);
                } else {
                    tvDescriptionEmpty.setVisibility(GONE);
                    tvDescriptionFilled.setVisibility(VISIBLE);
                    tvDescriptionFilled.setText(description);
                }

                String avatarUrl = perfil.optString("foto", null);
                if (avatarUrl != null && !avatarUrl.isEmpty()) {
                    Glide.with(requireContext())
                            .load(avatarUrl)
                            .placeholder(R.drawable.ic_profile)
                            .circleCrop()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE)
                            .into(ibProfile);
                } else {
                    ibProfile.setImageResource(R.drawable.ic_profile);
                }

                if (description == null || description.isEmpty()) {
                    tvDescriptionEmpty.setVisibility(VISIBLE);
                    tvDescriptionFilled.setVisibility(GONE);
                } else {
                    tvDescriptionEmpty.setVisibility(GONE);
                    tvDescriptionFilled.setVisibility(VISIBLE);
                    tvDescriptionFilled.setText(description);

                    tvDescriptionFilled.setOnClickListener(v -> {
                        tvDescriptionFilled.setVisibility(GONE);
                        tvDescriptionEmpty.setVisibility(VISIBLE);
                        tvDescriptionEmpty.setText(description);
                        tvDescriptionEmpty.requestFocus();
                        tvDescriptionEmpty.setSelection(description.length());

                        tvDescriptionEmpty.post(() -> {
                            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)
                                    requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.showSoftInput(tvDescriptionEmpty, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    });
                }

            }




            @Override
            public void onPerfilError(String error) {
                tvUsername.setText("Error");
            }
        }).execute();
    }

    private void mostrarDialogoSalir() {
        new DialogCerrarSesion().show(getChildFragmentManager(), "salirDialog");
    }

    @Override
    public void onSalirConfirmado() {
        new PerfilController(requireActivity(), new PerfilCallback() {
            @Override
            public void onPerfilSuccess(JSONObject perfil) {
                Intent intent = new Intent(requireContext(), LoginSiginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }

            @Override
            public void onPerfilError(String error) {
                Toast.makeText(requireContext(), "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
            }
        }, true).execute();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            new ProfilePictureController(requireActivity(), new PerfilCallback() {
                @Override
                public void onPerfilSuccess(JSONObject perfil) {
                    cargarPerfil();
                }

                @Override
                public void onPerfilError(String error) {
                    Toast.makeText(requireContext(), "Error al subir la foto", Toast.LENGTH_SHORT).show();
                }
            }, imageUri).execute();
        }
    }

    private void setupDescriptionEdit() {
        tvDescriptionEmpty.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tvGuardarDescription.setVisibility(GONE);
                } else {
                    tvGuardarDescription.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        tvGuardarDescription.setOnClickListener(v -> guardarDescripcion(tvDescriptionEmpty.getText().toString().trim()));
    }
    private void guardarDescripcion(String descripcion) {
        if (descripcion.isEmpty()) return;

        android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)
                requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null && getView() != null) {
            imm.hideSoftInputFromWindow(tvDescriptionEmpty.getWindowToken(), 0);
        }

        new DescriptionController(
                requireActivity(),
                new PerfilCallback() {
                    @Override
                    public void onPerfilSuccess(org.json.JSONObject perfil) {
                        Toast.makeText(requireContext(), "Descripción actualizada", Toast.LENGTH_SHORT).show();
                        cargarPerfil();
                    }
                    @Override
                    public void onPerfilError(String error) {
                        Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                },
                descripcion
        ).execute();
    }
}