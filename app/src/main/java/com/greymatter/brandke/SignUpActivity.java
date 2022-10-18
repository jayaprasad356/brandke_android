package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,OtpActivity.class));
            }
        });
    }
}