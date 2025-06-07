package com.example.aniclips.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.aniclips.R;
import com.example.aniclips.fragments.ActivateAccountFragment;
import com.example.aniclips.fragments.LoginFragment;
import com.example.aniclips.interfaces.ActivateAccountCallback;
import com.example.aniclips.utils.Constantes;

public class DialogAccountActivated extends DialogFragment {

    private ActivateAccountCallback listener;

    private AlertDialog dialog;

    public static DialogAccountActivated newInstance(String username) {
        DialogAccountActivated fragment = new DialogAccountActivated();
        Bundle args = new Bundle();
        args.putString(Constantes.BUNDLE_USER_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ActivateAccountCallback) {
            listener = (ActivateAccountCallback) context;
        } else if (getParentFragment() instanceof ActivateAccountCallback) {
            listener = (ActivateAccountCallback) getParentFragment();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        String username = getArguments().getString(Constantes.BUNDLE_USER_USERNAME);

        View view = getLayoutInflater().inflate(R.layout.dialog_activate_account, null);

        TextView tvWelcome = view.findViewById(R.id.tvWelcome);
        Button btnAcceptActivateAccount = view.findViewById(R.id.btnAcceptActivateAccount);

        tvWelcome.setText("Bienvenido "+ username + "!");

        btnAcceptActivateAccount.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginSignin_container, new LoginFragment())
                    .commit();
            dialog.dismiss();
        });

        LottieAnimationView lottieView = view.findViewById(R.id.lottieAccountActivated);
        lottieView.playAnimation();

        dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .setCancelable(true)
                .create();

        return dialog;
    }
}

