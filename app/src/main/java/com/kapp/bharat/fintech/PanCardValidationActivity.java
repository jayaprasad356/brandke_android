package com.kapp.bharat.fintech;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PanCardValidationActivity extends AppCompatActivity {
    Activity activity;
    EditText edPan;
    Button btnValidate;
    LinearLayout liResult;
    TextView tvPan,tvLastname,tvFirstName,tvMiddleName,tvLastTile;

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

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPan.getText().toString().trim().equals("")){
                    Toast.makeText(activity, "Enter Pan Number", Toast.LENGTH_SHORT).show();

                }else if (edPan.getText().length() != 12){
                    Toast.makeText(activity, "Invalid pan Number", Toast.LENGTH_SHORT).show();

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
        params.put(Constant.PAN,edPan.getText().toString().trim());
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