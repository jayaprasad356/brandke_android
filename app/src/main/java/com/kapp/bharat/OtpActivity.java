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
                    //verifyPhoneNumberWithCode(mVerificationId, otp_view.getOTP().toString());

                } else {
                    Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(activity, "Enter OTP", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(activity, "123", Toast.LENGTH_SHORT).show();


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
        //  startPhoneNumberVerification("+91" + MobileNumber);
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

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
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


}