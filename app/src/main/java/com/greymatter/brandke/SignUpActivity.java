package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                else {

                    registerUser();
                }

            }
        });
    }

    private void registerUser()  {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,etPhoneNumber.getText().toString().trim());

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, OtpActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SIGNUP_URL, params,true);


    }
}