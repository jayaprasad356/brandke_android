package com.greymatter.brandke;

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
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class OtpActivity extends AppCompatActivity {
    private Button btnVerifyOtp;
    private String PhoneNumber;
    private TextView Phone;

    MaterialButton verifybtn;
    String MobileNumber;
    TextView tvMobileno, resend;
    Session session;
    OtpTextView otp_view;
    private String mVerificationId = "";
    Activity activity;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String TAG = "OTPACT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnVerifyOtp = findViewById(R.id.btnVerify);

        Phone = findViewById(R.id.PhoneNumber);
        Phone.setText(getIntent().getStringExtra(Constant.MOBILE));
        PhoneNumber = getIntent().getStringExtra(Constant.MOBILE);
        session = new Session(activity);


        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otp_view.getOTP().length() == 6) {
                    login();
//                    if (!mVerificationId.equals("")) {
//                        verifyPhoneNumberWithCode(mVerificationId, otp_view.getOTP().toString());
//
//                    } else {
//                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
//                    }


                } else {
                    Toast.makeText(activity, "Enter OTP", Toast.LENGTH_SHORT).show();
                }


            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;


            }
        };

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String code = credential.getSmsCode();
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (code != null)
                                otp_view.setOTP(code);

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            login();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void login() {


        Map<String, String> params = new HashMap<>();
        //request

        params.put(Constant.MOBILE, MobileNumber);


        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("OTP_RES", response);


                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        if (jsonObject.getBoolean(Constant.NEW_USER)) {
                            Intent intent = new Intent(OtpActivity.this, SignUpActivity.class);
                            intent.putExtra(Constant.MOBILE, MobileNumber);
                            startActivity(intent);
                            finish();

                        } else {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                            session.setBoolean("is_logged_in", true);
                            session.setData(Constant.ID, jsonArray.getJSONObject(0).getString(Constant.ID));
                            session.setData(Constant.NAME, jsonArray.getJSONObject(0).getString(Constant.NAME));
                            session.setData(Constant.MOBILE, jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                            session.setData(Constant.ADDRESS, jsonArray.getJSONObject(0).getString(Constant.ADDRESS));
                            session.setData(Constant.GENDER, jsonArray.getJSONObject(0).getString(Constant.GENDER));
                            session.setData(Constant.EMAIL, jsonArray.getJSONObject(0).getString(Constant.EMAIL));
                            startActivity(new Intent(OtpActivity.this, MainActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, String.valueOf(response) + String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
            //pass url
        }, OtpActivity.this, Constant.LOGIN_URL, params, true);


    }

}