package com.example.aniclips.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.aniclips.R;
import com.example.aniclips.activities.LoginSiginActivity;
import com.example.aniclips.fragments.SignInFragment;

public class RegisterDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Registro requerido")
                .setMessage("Para acceder a esa funcionalidad necesitas estar registrado. ¿Quieres crearte una cuenta?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    Intent intent = new android.content.Intent(requireContext(), LoginSiginActivity.class);
                    intent.putExtra("showSignInFragment", true);
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .create();
    }
}