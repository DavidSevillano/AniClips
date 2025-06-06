package com.example.aniclips.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;

public class LoginFragment extends Fragment {

    private CardView cvUsername;
    private CardView cvPassword;
    private Button btnLogin;
    private TextView tvSignIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        cvUsername = view.findViewById(R.id.cvUsername);
        cvPassword = view.findViewById(R.id.cvPassword);
        btnLogin = view.findViewById(R.id.btnLogIn);
        tvSignIn = view.findViewById(R.id.tvSignIn);
    }

    private void initEvents() {
        tvSignIn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginSignin_container, new SignInFragment())
                    .commit();
        });
    }
}