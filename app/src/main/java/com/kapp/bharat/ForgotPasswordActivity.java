package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView backimg;
    EditText etNewpassword, etConfirmpassword;
    Button btnChange;
    Activity activity;
    String Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Mobile = getIntent().getStringExtra(Constant.MOBILE);
        activity = ForgotPasswordActivity.this;
        backimg = findViewById(R.id.backimg);
        etNewpassword = findViewById(R.id.etNewpassword);
        etConfirmpassword = findViewById(R.id.etConfirmpassword);
        btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassword();
            }
        });

        backimg.setOnClickListener(view -> onBackPressed());
    }

    private void checkPassword() {
        if (etNewpassword.getText().toString().trim().equals("")) {
            etNewpassword.setError("empty");
        } else if (etConfirmpassword.getText().toString().trim().equals("")) {
            etConfirmpassword.setError("empty");
        } else if (!etConfirmpassword.getText().toString().trim().equals(etNewpassword.getText().toString().trim())) {
            Toast.makeText(activity, "Password Does Not Match", Toast.LENGTH_SHORT).show();
        } else {

            changePassword();
        }
    }

    private void changePassword()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,Mobile);
        params.put(Constant.PASSWORD,etNewpassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, LoginActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.FORGOT_PASSWORD_URL, params,true);
    }
}