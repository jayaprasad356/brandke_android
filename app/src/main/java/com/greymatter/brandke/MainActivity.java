package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fm;
    private Fragment HomeFrag,FinTechFrag,TokenFrag,WalletFrag,ProfileFrag;
    private ChipNavigationBar chipNavigationBar;

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

        chipNavigationBar = findViewById(R.id.NavigationView);
        fm.beginTransaction().replace(R.id.FrameLyt,HomeFrag).addToBackStack("null").commit();
        chipNavigationBar.setItemSelected(R.id.Home,true,true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.Home:
                        fm.beginTransaction()
                                .replace(R.id.FrameLyt,HomeFrag)
                                .addToBackStack("null")
                                .commit();
                        break;
                    case R.id.Fintech:
                        fm.beginTransaction()
                                .replace(R.id.FrameLyt,FinTechFrag)
                                .addToBackStack("null")
                                .commit();
                        break;
                    case R.id.Token:
                        fm.beginTransaction()
                                .replace(R.id.FrameLyt,TokenFrag)
                                .addToBackStack("null")
                                .commit();
                        break;
                    case R.id.Wallet:
                        fm.beginTransaction()
                                .replace(R.id.FrameLyt,WalletFrag)
                                .addToBackStack("null")
                                .commit();
                        break;
                    case R.id.Profile:
                        fm.beginTransaction()
                                .replace(R.id.FrameLyt,ProfileFrag)
                                .addToBackStack("null")
                                .commit();
                        break;
                }
            }
        });
    }
}