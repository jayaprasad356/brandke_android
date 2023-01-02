package com.kapp.bharat.fintech;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kapp.bharat.R;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PanCardValidationActivity extends AppCompatActivity {
    Activity activity;
    EditText edPan;
    Button btnValidate;
    LinearLayout liResult;
    TextView tvPan,tvLastname,tvFirstName,tvMiddleName,tvLastTile;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pancard_validation);
        activity = PanCardValidationActivity.this;
        edPan = findViewById(R.id.edPan);
        btnValidate = findViewById(R.id.btnValidate);
        liResult = findViewById(R.id.liResult);
        tvPan = findViewById(R.id.tvPanNumber);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvMiddleName = findViewById(R.id.tvMiddleName);
        tvLastname = findViewById(R.id.tvLastName);
        tvLastTile = findViewById(R.id.tvTitle);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edPan.getText().toString().trim(); // get your editext value here
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                Matcher matcher = pattern.matcher(s);
// Check if pattern matches


                if (edPan.getText().toString().trim().equals("")){
                    Toast.makeText(activity, "Enter Pan Number", Toast.LENGTH_SHORT).show();

                }else {
                    if (matcher.matches()) {
                        validatePan();
                    }else {
                        Toast.makeText(activity, "Invalid Pan Number", Toast.LENGTH_SHORT).show();


                    }

                }

            }
        });


    }

    private void validatePan() {
        Long timestamp = System.currentTimeMillis()/1000;
        Map<String, String> params = new HashMap<>();
        params.put(Constant.REFERENCEID,timestamp.toString());
        params.put(Constant.PANNUMBER,edPan.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("ADHAAR_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, "Pan Details Fetched Successfully", Toast.LENGTH_SHORT).show();
                        liResult.setVisibility(View.VISIBLE);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(Constant.DATA);
                        tvPan.setText(jsonObject1.getString("pan_number"));
                        tvFirstName.setText(jsonObject1.getString("first_name"));
                        tvLastname.setText(jsonObject1.getString("last_name"));
                        tvMiddleName.setText(jsonObject1.getString("middle_name"));
                        tvLastTile.setText(jsonObject1.getString("title"));

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PAN_VALIDATE_URL, params, true,1);

    }
}