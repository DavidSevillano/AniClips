package com.example.aniclips.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.activities.LoginSiginActivity;
import com.example.aniclips.controllers.PerfilController;
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
    private ImageButton ibLogout;
    private ImageButton ibProfile;
    
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

    }

    private void initEvents() {
        ibLogout.setOnClickListener(v -> mostrarDialogoSalir());
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
        Toast.makeText(requireContext(), "onSalirConfirmado llamado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(requireContext(), LoginSiginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}