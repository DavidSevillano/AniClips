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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.activities.LoginSiginActivity;
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
    private TextView tvDescriptionEmpty;
    private TextView tvDescriptionFilled;
    private TextView tvChangeProfilePicture;
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

    }

    private void initEvents() {
        ibLogout.setOnClickListener(v -> mostrarDialogoSalir());

        ibProfile.setOnClickListener(v -> openImagePicker());

        tvChangeProfilePicture.setOnClickListener(v -> openImagePicker());
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
                            .into(ibProfile);
                } else {
                    ibProfile.setImageResource(R.drawable.ic_profile);
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
}