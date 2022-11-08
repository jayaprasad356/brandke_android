package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class contactusActivity extends AppCompatActivity {

    ImageView backbtn;
    TextView tvName,tvMobile,tvEmail,tvAddress;
    ImageView imgTwitter,imgFacebook,imgLinkedin,imgInstagram;
    Activity activity;
    String twitter,linkedin,facebook,instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        activity = contactusActivity.this;
        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);
        tvEmail = findViewById(R.id.tvEmail);
        imgTwitter = findViewById(R.id.imgTwitter);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgLinkedin = findViewById(R.id.imgLinkedin);
        imgInstagram = findViewById(R.id.imgInstagram);
        backbtn =findViewById(R.id.backbtn);


        contactList();



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void contactList()
    {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("LOGIN_RES",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        tvName.setText(jsonArray.getJSONObject(0).getString(Constant.NAME));
                        tvMobile.setText(jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                        tvEmail.setText(jsonArray.getJSONObject(0).getString(Constant.EMAIL));
                        twitter = jsonArray.getJSONObject(0).getString(Constant.TWITTER);
                        linkedin = jsonArray.getJSONObject(0).getString(Constant.LINKEDIN);
                        facebook = jsonArray.getJSONObject(0).getString(Constant.FACEBOOK);
                        instagram = jsonArray.getJSONObject(0).getString(Constant.INSTAGRAM);
                        if (!twitter.isEmpty()){
                            imgTwitter.setVisibility(View.VISIBLE);
                        }
                        if (!linkedin.isEmpty()){
                            imgLinkedin.setVisibility(View.VISIBLE);
                        }
                        if (!facebook.isEmpty()){
                            imgFacebook.setVisibility(View.VISIBLE);
                        }
                        if (!instagram.isEmpty()){
                            imgInstagram.setVisibility(View.VISIBLE);
                        }
                        imgTwitter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {

                                    open(twitter);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        imgFacebook.setOnClickListener(view -> {
                            try {

                                open(facebook);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        });
                        imgInstagram.setOnClickListener(view -> {
                            try {

                                open(instagram);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });
                        imgLinkedin.setOnClickListener(view -> {
                            try {

                                open(linkedin);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        });
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SETTINGS_LIST, params,true);

    }

    private void open(String url)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }
}