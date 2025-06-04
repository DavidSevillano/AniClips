package com.example.aniclips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.aniclips.R;

public class SignInActivity extends AppCompatActivity {

    private CardView cvEmail;
    private CardView cvUsername;
    private CardView cvPassword;
    private CardView cvRepeatPassword;
    private Button btnSignIn;
    private TextView tvLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        
        initViews();
        initEvents();
    }

    private void initViews() {
        cvEmail = findViewById(R.id.cvEmail);
        cvUsername = findViewById(R.id.cvUsername);
        cvPassword = findViewById(R.id.cvPassword);
        cvRepeatPassword = findViewById(R.id.cvRepeatPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvLogIn = findViewById(R.id.tvLogInBack);
    }
    private void initEvents() {
        tvLogIn.setOnClickListener(v -> {
            finish();
        });
        btnSignIn.setOnClickListener(v ->{
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}