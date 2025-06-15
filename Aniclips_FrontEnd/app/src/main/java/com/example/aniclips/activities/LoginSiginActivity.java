package com.example.aniclips.activities;

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
}
