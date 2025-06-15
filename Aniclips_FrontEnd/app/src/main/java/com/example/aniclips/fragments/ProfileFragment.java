package com.example.aniclips.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.activities.LoginSiginActivity;
import com.example.aniclips.utils.Constantes;

public class ProfileFragment extends Fragment {
    private static final String ARG_USER_ID = "user_id";
    private String userId;
    private View profileMainLayout;
    private View overlay;

    public static ProfileFragment newInstance(String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        overlay = view.findViewById(R.id.overlayProfileLoginRequired);
        profileMainLayout = view.findViewById(R.id.profileMainLayout);

        SharedPreferences prefs = requireContext().getSharedPreferences("My_prefs", android.content.Context.MODE_PRIVATE);
        boolean loggedIn = prefs.contains(Constantes.PREF_TOKEN_JWT);
        if (!loggedIn) {
            overlay.setVisibility(View.VISIBLE);
            profileMainLayout.setVisibility(View.VISIBLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                profileMainLayout.setRenderEffect(
                        android.graphics.RenderEffect.createBlurEffect(20f, 20f, android.graphics.Shader.TileMode.CLAMP)
                );
            }

            Button btnLogout = overlay.findViewById(R.id.btnOverlayLogout);
            btnLogout.setOnClickListener(v -> {
                prefs.edit().clear().apply();
                Intent intent = new Intent(requireContext(), LoginSiginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            });
        } else {
            overlay.setVisibility(View.GONE);
            profileMainLayout.setVisibility(View.GONE);
        }

        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }

        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userId);

        ProfileHeaderFragment headerFragment = new ProfileHeaderFragment();
        headerFragment.setArguments(bundle);

        MyClipsFragment myClipsFragment = new MyClipsFragment();
        myClipsFragment.setArguments(bundle);

        headerFragment.setPerfilLoadedListener(() -> {
            if (profileMainLayout != null && loggedIn) {
                profileMainLayout.setVisibility(View.VISIBLE);
                profileMainLayout.setAlpha(0f);
                profileMainLayout.animate().alpha(1f).setDuration(250).start();
            }
        });

        getChildFragmentManager().beginTransaction()
                .replace(R.id.profileHeaderContainer, headerFragment)
                .commit();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.myClipsContainer, myClipsFragment)
                .commit();

        return view;
    }
}