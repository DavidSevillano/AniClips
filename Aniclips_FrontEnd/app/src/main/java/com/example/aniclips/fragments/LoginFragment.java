package com.example.aniclips.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.activities.MainActivity;
import com.example.aniclips.controllers.LoginController;
import com.example.aniclips.interfaces.LoginCallback;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignIn;
    private TextView tvNoAccount;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogIn);
        tvSignIn = view.findViewById(R.id.tvSignIn);
        tvNoAccount = view.findViewById(R.id.tvNoAccount);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void initEvents() {
        tvSignIn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginSignin_container, new SignInFragment())
                    .commit();
        });
        btnLogin.setOnClickListener(v -> {
            login();
        });
        tvNoAccount.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean validarCampos() {
        EditText[] campos = {etUsername, etPassword};
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void login(){
        if (!validarCampos()) return;

        new LoginController(
                requireContext(),
                etUsername.getText().toString(),
                etPassword.getText().toString(),
                new LoginCallback() {
                    @Override
                    public void onLoginSuccess(JSONObject response) {
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onLoginError(JSONObject error) {
                        if (error != null && error.has("detail") && "Bad credentials".equals(error.optString("detail"))) {
                            Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, progressBar
        ).execute();
    }
}