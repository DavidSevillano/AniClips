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
import com.example.aniclips.activities.MainActivity;

public class SignInFragment extends Fragment {

    private CardView cvEmail;
    private CardView cvUsername;
    private CardView cvPassword;
    private CardView cvRepeatPassword;
    private Button btnSignIn;
    private TextView tvLogIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        cvEmail = view.findViewById(R.id.cvEmail);
        cvUsername = view.findViewById(R.id.cvUsername);
        cvPassword = view.findViewById(R.id.cvPassword);
        cvRepeatPassword = view.findViewById(R.id.cvRepeatPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        tvLogIn = view.findViewById(R.id.tvLogInBack);
    }

    private void initEvents() {
        tvLogIn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
    }
}