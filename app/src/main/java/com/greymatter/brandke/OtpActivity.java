package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.greymatter.brandke.helper.Constant;

import org.w3c.dom.Text;

public class OtpActivity extends AppCompatActivity {
    private Button btnVerifyOtp;
    private String PhoneNumber;
    private TextView Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnVerifyOtp = findViewById(R.id.btnVerify);
        Phone = findViewById(R.id.PhoneNumber);
        Phone.setText(getIntent().getStringExtra(Constant.MOBILE));
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