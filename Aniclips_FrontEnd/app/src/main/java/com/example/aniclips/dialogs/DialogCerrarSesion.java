package com.example.aniclips.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.aniclips.R;
import com.example.aniclips.interfaces.SalirDialogListener;


public class DialogCerrarSesion extends DialogFragment {

    private SalirDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SalirDialogListener) {
            listener = (SalirDialogListener) context;
        } else if (getParentFragment() instanceof SalirDialogListener) {
            listener = (SalirDialogListener) getParentFragment();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setMessage(R.string.messageDialog)
                .setCancelable(false)
                .setPositiveButton(R.string.yesDialog, (dialogInterface, which) -> {
                    SalirDialogListener salirListener = null;
                    if (getParentFragment() instanceof SalirDialogListener) {
                        salirListener = (SalirDialogListener) getParentFragment();
                    } else if (getActivity() instanceof SalirDialogListener) {
                        salirListener = (SalirDialogListener) getActivity();
                    }
                    if (salirListener != null) salirListener.onSalirConfirmado();
                })
                .setNegativeButton(R.string.noDialog, null)
                .create();

        dialog.setOnShowListener(d -> {
            int color = isDarkTheme(requireContext())
                    ? ContextCompat.getColor(requireContext(), android.R.color.white)
                    : ContextCompat.getColor(requireContext(), android.R.color.black);

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(color);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(color);
        });

        return dialog;
    }

    private boolean isDarkTheme(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}

