package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greymatter.brandke.helper.Constant;

public class OtpActivity extends AppCompatActivity {
    private Button btnVerifyOtp;
    private String PhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnVerifyOtp = findViewById(R.id.btnVerify);
        PhoneNumber = getIntent().getStringExtra(Constant.MOBILE);
        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OtpActivity.this, RegisterActivity.class);
                i.putExtra(Constant.MOBILE,PhoneNumber);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }
        });
    }
}