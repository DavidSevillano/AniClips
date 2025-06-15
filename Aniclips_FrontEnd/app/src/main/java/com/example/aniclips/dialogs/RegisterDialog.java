package com.example.aniclips.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.aniclips.R;
import com.example.aniclips.activities.LoginSiginActivity;

public class RegisterDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Registro requerido")
                .setMessage("Para acceder a esa funcionalidad necesitas estar registrado. ¿Quieres crearte una cuenta?")
                .setPositiveButton("Sí", (dialogInterface, which) -> {
                    Intent intent = new Intent(requireContext(), LoginSiginActivity.class);
                    intent.putExtra("showSignInFragment", true);
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            int white = requireContext().getColor(android.R.color.white);
            if (positive != null) positive.setTextColor(white);
            if (negative != null) negative.setTextColor(white);
        });

        return dialog;
    }
}