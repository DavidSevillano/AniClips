package com.example.aniclips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.aniclips.R;

public class LoginActivity extends AppCompatActivity {

    private CardView cvUsername;
    private CardView cvPassword;
    private Button btnLogin;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();

    }

    private void initViews() {
        cvUsername = findViewById(R.id.cvUsername);
        cvPassword = findViewById(R.id.cvPassword);
        btnLogin = findViewById(R.id.btnLogIn);
        tvSignIn = findViewById(R.id.tvSignIn);
    }
    private void initEvents() {
        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }



}