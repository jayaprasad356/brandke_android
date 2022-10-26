package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    EditText etMobile,etPassword;
    TextView tvsignupbtn;
    Activity activity;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;
        session =  new Session(activity);

        tvsignupbtn = findViewById(R.id.tvsignupbtn);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        tvsignupbtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etMobile.getText().toString().trim().equals("")){
                    etMobile.setError("Enter Mobile Number");
                    etMobile.requestFocus();
                }
                else if (etPassword.getText().toString().trim().equals("")){
                    etPassword.setError("Enter Password");
                    etPassword.requestFocus();
                }
                else if (etMobile.getText().length() != 10){
                    etMobile.setError("Invalid");
                    etMobile.requestFocus();
                }
                else if (etPassword.getText().length() < 5){
                    etPassword.setError("Weak Password");
                    etPassword.requestFocus();
                }
                else{
                    loginUser();
                }
            }
        });
    }

    private void loginUser() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,etMobile.getText().toString().trim());
        params.put(Constant.PASSWORD,etPassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("LOGIN_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(LoginActivity.this, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setBoolean("is_logged_in", true);
                        session.setData(Constant.ID,jsonArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.NAME,jsonArray.getJSONObject(0).getString(Constant.NAME));
                        session.setData(Constant.MOBILE,jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                        session.setData(Constant.PINCODE,jsonArray.getJSONObject(0).getString(Constant.PINCODE));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, LoginActivity.this, Constant.LOGIN_URL, params,true);

    }
}