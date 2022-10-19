package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    public static FragmentManager fm;
    private Fragment HomeFrag,FinTechFrag,TokenFrag,WalletFrag,ProfileFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        HomeFrag = new HomeFargment();
        FinTechFrag = new FinTechFragmnet();
        TokenFrag = new TokenFargment();
        WalletFrag = new WalletFragment();
        ProfileFrag = new ProfileFragment();


        fm.beginTransaction().replace(R.id.FrameLyt,HomeFrag).addToBackStack("null").commit();

    }
}