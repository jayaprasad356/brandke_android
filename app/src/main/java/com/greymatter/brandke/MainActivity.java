package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends AppCompatActivity {

    public static FragmentManager fm = null;
    ChipNavigationBar chipNavigationBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();



        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setItemSelected(R.id.Home,
                true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FrameLyt,
                        new HomeFragment()).commit();

        bottomMenu();

    }

    private void bottomMenu() {



        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Fragment fragment = null;
                        switch (i){
                            case R.id.Home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.Fintech:
                                fragment = new FinTechFragmnet();
                                break;

                            case R.id.Token:
                                fragment = new TokenFargment();
                                break;

                            case R.id.Wallet:
                                fragment = new WalletFragment();
                                break;

                            case R.id.Profile:
                                fragment = new ProfileFragment();
                                break;

                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.FrameLyt,
                                        fragment).commit();
                    }
                });
    }
}