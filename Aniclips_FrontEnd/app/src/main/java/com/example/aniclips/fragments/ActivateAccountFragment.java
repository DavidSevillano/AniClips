package com.example.aniclips.fragments;

import android.content.Context;
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
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.controllers.ActivateAccountController;
import com.example.aniclips.controllers.CreateUserController;
import com.example.aniclips.dialogs.DialogAccountActivated;
import com.example.aniclips.interfaces.ActivateAccountCallback;
import com.example.aniclips.interfaces.CreateUserCallback;
import com.example.aniclips.utils.Constantes;

import org.json.JSONObject;

public class ActivateAccountFragment extends Fragment {

    private EditText etActivateAccount;
    private Button btnActivateAccount;

    private String code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activate_account, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        etActivateAccount = view.findViewById(R.id.etActivateAccount);
        btnActivateAccount = view.findViewById(R.id.btnActivateAccount);
    }

    private void initEvents() {

        btnActivateAccount.setOnClickListener(v -> {
            activateAccount();
        });
    }

    private void activateAccount() {

        code = etActivateAccount.getText().toString().trim();

        new ActivateAccountController(requireContext(), code, new ActivateAccountCallback() {
            @Override
            public void onAccountActivated(String username) {

                DialogAccountActivated
                        .newInstance(username)
                        .show(getParentFragmentManager(), "TurnoIniciado");

                SharedPreferences prefs = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                prefs.edit().putString(Constantes.PREF_ACTIVATION_CODE, username).apply();

            }

            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        }).execute();
        }

    }
