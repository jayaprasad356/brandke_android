package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kapp.bharat.helper.Constant;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSendOtp;
    EditText etPhoneNumber;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        activity = SignUpActivity.this;

        btnSendOtp = findViewById(R.id.btnSendOtp);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etPhoneNumber.getText().toString().trim().equals("")){
                    etPhoneNumber.setError("Enter Mobile");
                    etPhoneNumber.requestFocus();
                }
                else if (etPhoneNumber.getText().length() != 10){
                    etPhoneNumber.setError("Invaild Mobile");
                    etPhoneNumber.requestFocus();
                }

                else {
                    Intent i = new Intent(activity,OtpActivity.class);
                    i.putExtra(Constant.MOBILE,etPhoneNumber.getText().toString());
                    startActivity(i);
                    registerUser();
                }

            }
        });
    }

    private void registerUser()  {

    }
}