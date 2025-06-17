package com.example.aniclips.activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.fragments.HomeFragment;
import com.example.aniclips.fragments.LoginFragment;
import com.example.aniclips.fragments.SearchFragment;
import com.example.aniclips.fragments.SignInFragment;
import com.example.aniclips.utils.HideNavigationBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginSiginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideNavigationBar.hideNavigationBar(getWindow().getDecorView());

        setContentView(R.layout.activity_login_signin);

        if (getIntent().getBooleanExtra("showSignInFragment", false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginSignin_container, new SignInFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginSignin_container, new LoginFragment())
                    .commit();
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Salir de la app")
                .setMessage("¿Estás seguro de que quieres salir de AniClips?")
                .setPositiveButton("Sí", (dialogInterface, which) -> finishAffinity())
                .setNegativeButton("No", null)
                .create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(android.R.color.white));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(android.R.color.white));
    }
}
