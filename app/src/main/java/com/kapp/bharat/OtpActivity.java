package com.kapp.bharat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class OtpActivity extends AppCompatActivity {
    private Button btnVerifyOtp;
    private TextView Phone;
    private int otp;

    MaterialButton verifybtn;
    String MobileNumber;
    TextView tvMobileno, resend;
    Session session;
    OtpTextView otp_view;
    private String mVerificationId = "";
    Activity activity;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String TAG = "OTPACT", titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        activity = OtpActivity.this;
        btnVerifyOtp = findViewById(R.id.btnVerify);
        mAuth = FirebaseAuth.getInstance();
        otp_view = findViewById(R.id.otpView);
        titleText = getIntent().getStringExtra(Constant.TITLE);
        Phone = findViewById(R.id.PhoneNumber);
        Phone.setText(getIntent().getStringExtra(Constant.MOBILE));
        MobileNumber = getIntent().getStringExtra(Constant.MOBILE);
        session = new Session(activity);

        btnVerifyOtp.setOnClickListener(v -> {
            if (otp_view.getOTP().length() == 6) {
                if (Integer.parseInt(otp_view.getOTP()) == otp) {
                    verifyPhoneNumberOtp();

                } else {
                    Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(activity, "Enter OTP", Toast.LENGTH_SHORT).show();
            }

        });
        generateOTP();

    }

    private void verifyPhoneNumberOtp() {
        if (titleText.equals(Constant.FORGOT_PASSWORD)) {
            Intent intent = new Intent(OtpActivity.this, ForgotPasswordActivity.class);
            intent.putExtra(Constant.MOBILE, MobileNumber);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(OtpActivity.this, RegisterActivity.class);
            intent.putExtra(Constant.MOBILE, MobileNumber);
            startActivity(intent);
            finish();
        }
    }

    private void generateOTP() {
        Random random = new Random();
        otp = random.nextInt(900000 + 10000);
        String url = "https://sms6.rmlconnect.net/bulksms/bulksms?username=Mxcel&password=Xcel2020&type=0&dlr=1&destination=" + MobileNumber + "&source=XLSRVY&message=Dear%20User,%0A%0AYour%20OTP%20for%20Token%20App%20is%20" + otp + ".%20This%20is%20valid%20for%2010%20min,%20please%20do%20not%20share%20it%20with%20anyone.%0A%0ATeam%20Market-Xcel&entityid=1601100000000017697&tempid=1607100000000233745";

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                Toast.makeText(OtpActivity.this, OtpActivity.this.getString(R.string.otp_send_success), Toast.LENGTH_SHORT).show();
            }
        }, OtpActivity.this, url, null, true);
    }

}