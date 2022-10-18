package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSignUp = findViewById(R.id.btnRegister);
        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        });
    }
}