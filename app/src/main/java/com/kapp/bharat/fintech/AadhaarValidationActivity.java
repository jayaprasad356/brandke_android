package com.kapp.bharat.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kapp.bharat.MainActivity;
import com.kapp.bharat.R;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AadhaarValidationActivity extends AppCompatActivity {
    Activity activity;
    EditText edAadhaar;
    Button btnValidate;
    LinearLayout liResult;
    TextView tvAadhaar,tvAgerange,tvState,tvGender,tvMobileLinked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar_validation);
        activity = AadhaarValidationActivity.this;
        edAadhaar = findViewById(R.id.edAadhaar);
        btnValidate = findViewById(R.id.btnValidate);
        liResult = findViewById(R.id.liResult);
        tvAadhaar = findViewById(R.id.tvAadhaar);
        tvAgerange = findViewById(R.id.tvAgerange);
        tvState = findViewById(R.id.tvState);
        tvGender = findViewById(R.id.tvGender);
        tvMobileLinked = findViewById(R.id.tvMobileLinked);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edAadhaar.getText().toString().trim().equals("")){
                    Toast.makeText(activity, "Enter Aadhaar Number", Toast.LENGTH_SHORT).show();

                }else if (edAadhaar.getText().length() != 12){
                    Toast.makeText(activity, "Invalid Aadhaar Number", Toast.LENGTH_SHORT).show();

                }else {
                    validateAadhaar();
                }

            }
        });


    }

    private void validateAadhaar() {
        Long timestamp = System.currentTimeMillis()/1000;
        Map<String, String> params = new HashMap<>();
        params.put(Constant.REFID,timestamp.toString());
        params.put(Constant.AADHAR_NUMBER,edAadhaar.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("ADHAAR_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, "Aadhaar Details Fetched Successfully", Toast.LENGTH_SHORT).show();
                        liResult.setVisibility(View.VISIBLE);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(Constant.DATA);
                        tvAadhaar.setText(jsonObject1.getString("aadhaar_number"));
                        tvAgerange.setText(jsonObject1.getString("age_range"));
                        tvState.setText(jsonObject1.getString("state"));
                        tvGender.setText(jsonObject1.getString("gender"));
                        if (jsonObject1.getBoolean("is_mobile")){
                            tvMobileLinked.setText("Yes");

                        }else {
                            tvMobileLinked.setText("No");
                        }


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.AADHAAR_VALIDATE_URL, params, true,1);

    }
}