package com.example.aniclips.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.activities.MainActivity;
import com.example.aniclips.controllers.CreateUserController;
import com.example.aniclips.interfaces.CreateUserCallback;
import com.example.aniclips.utils.Constantes;

import org.json.JSONObject;

public class SignInFragment extends Fragment {

    private EditText etEmail, etUsername, etPassword, etRepeatPassword;
    private Button btnSignIn;
    private TextView tvLogIn;

    private String email, username, password, repeatPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        etEmail = view.findViewById(R.id.etEmail);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etRepeatPassword = view.findViewById(R.id.etRepeatPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        tvLogIn = view.findViewById(R.id.tvLogInBack);
    }

    private void initEvents() {
        tvLogIn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        btnSignIn.setOnClickListener(v -> {
            createUser();
        });
    }

    private void createUser() {

        email = etEmail.getText().toString().trim();
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        repeatPassword = etRepeatPassword.getText().toString().trim();

        Log.i("sigin", email);
        Log.i("sigin", username);
        Log.i("sigin", password);
        Log.i("sigin", repeatPassword);
        new CreateUserController(requireContext(), email, username, password, repeatPassword, new CreateUserCallback() {
            @Override
            public void onUserCreatedCallback(JSONObject userJson) {
                if (userJson != null && userJson.has("username")) {
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.loginSignin_container, new ActivateAccountFragment())
                            .commit();

                    SharedPreferences prefs = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    prefs.edit().putString(Constantes.PREF_USER_EMAIL, email).apply();
                    prefs.edit().putString(Constantes.PREF_USER_USERNAME, username).apply();
                    prefs.edit().putString(Constantes.PREF_USER_PASSWORD, password).apply();
                } else {
                    // Maneja el error aquí si lo deseas
                    Toast.makeText(requireContext(), "Error al crear usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

            }
        }).execute();
        }

    }
