package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.greymatter.brandke.helper.Session;


public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        handler = new Handler();
        Session session = new Session(SplashScreenActivity.this);
        GotoActivity();



    }


    private void GotoActivity()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this,WalletActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}

