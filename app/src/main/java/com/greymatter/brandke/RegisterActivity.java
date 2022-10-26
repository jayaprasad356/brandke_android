package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText etName,etEmail,etPassword,etConfirmPassword,etOccupation,etGender,etAddress,etVillage,etPinCode,etDistrict;
    private Activity activity;
    private Spinner SpinGender,SpinVillage,SpinDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSignUp = findViewById(R.id.btnRegister);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etpassword);
        etEmail = findViewById(R.id.etEmail);
        etOccupation = findViewById(R.id.etOccupation);
        etAddress = findViewById(R.id.etAddress);
        etPinCode = findViewById(R.id.etPinCode);
        SpinGender = findViewById(R.id.spinGender);
        SpinVillage = findViewById(R.id.spinVillageName);
        SpinDistrict = findViewById(R.id.spinDistrict);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        activity = RegisterActivity.this;
        defaultspinnerList();
        etPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 6) {
                    setSpinnerList();
                }else{
                    defaultspinnerList();
                }
            }
        });

        btnSignUp.setOnClickListener(v -> {
            if(etName.getText().toString().isEmpty()) {
                etName.setError("Name");
            }else if(etPassword.getText().toString().isEmpty()) {
                etPassword.setError("Password");
            }else if(etEmail.getText().toString().isEmpty()) {
                etEmail.setError("Enter Email");
            }else if(etConfirmPassword.getText().toString().isEmpty()) {
                etConfirmPassword.setError("Confirm password");
            }else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                etConfirmPassword.setError("Check password");
            }else if(etOccupation.getText().toString().isEmpty()){
                etOccupation.setError("Occupation");
            }else if(SpinGender.getSelectedItemId() == 0){
                Toast.makeText(activity, "Enter Gender", Toast.LENGTH_SHORT).show();
            }else if(etAddress.getText().toString().isEmpty()){
                etOccupation.setError("Address");
            }else if(SpinVillage.getSelectedItemId() == 0){
                Toast.makeText(activity, "Enter Village", Toast.LENGTH_SHORT).show();
            }else if(etPinCode.getText().toString().isEmpty()){
                etPinCode.setError("PinCode");
            }else if(SpinDistrict.getSelectedItemId() == 0){
                Toast.makeText(activity, "Select District", Toast.LENGTH_SHORT).show();
            }else{
                RegisterUser();
            }
        });

    }

    private void defaultspinnerList() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Select Village Name");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinVillage.setAdapter(adapter);
        SpinVillage.setSelection(0);

        ArrayList<String> arr2 = new ArrayList<String>();
        arr2.add("Select District");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinDistrict.setAdapter(adapter2);
        SpinDistrict.setSelection(0);
    }

    private void setSpinnerList()
    {
        if (etPinCode.getText().length() == 6){
            String pincodeurl = "http://www.postalpincode.in/api/pincode/"+etPinCode.getText().toString().trim();
            Map<String, String> params = new HashMap<>();
            ApiConfig.GetVolleyRequest((result, response) -> {
                if (result) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("Status").equals("Success")) {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("PostOffice");
                            Gson g = new Gson();
                            ArrayList<String> villagename = new ArrayList<String>();
                            ArrayList<String> district = new ArrayList<String>();
                            villagename.add("Select Village Name");
                            district.add("Select District");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String name_ = jsonArray.getJSONObject(i).getString("Name");
                                String district_ = jsonArray.getJSONObject(i).getString("District");



                                if (!district.contains(district_)) {

                                    district.add(district_);

                                }
                                if (!villagename.contains(name_)) {

                                    villagename.add(name_);

                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, villagename);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SpinVillage.setAdapter(adapter);
                            SpinVillage.setSelection(0);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SpinDistrict.setAdapter(adapter2);
                            SpinDistrict.setSelection(0);
                        }
                        else {
                            Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, activity, pincodeurl, params,true);
        }
    }

    private void RegisterUser() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.NAME,etName.getText().toString().trim());
        params.put(Constant.EMAIL,etEmail.getText().toString().trim());
        params.put(Constant.PASSWORD,etPassword.getText().toString().trim());
        params.put(Constant.MOBILE,getIntent().getStringExtra(Constant.MOBILE));
        params.put(Constant.OCCUPATION,etOccupation.getText().toString().trim());
        params.put(Constant.GENDER,etGender.getText().toString().trim());
        params.put(Constant.ADDRESS,etAddress.getText().toString().trim());
        params.put(Constant.VILLAGE,etVillage.getText().toString().trim());
        params.put(Constant.PINCODE,etPinCode.getText().toString().trim());
        params.put(Constant.DISTRICT,etDistrict.getText().toString().trim());
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
        }, activity, Constant.SIGNUP_URL, params,true);
    }
}