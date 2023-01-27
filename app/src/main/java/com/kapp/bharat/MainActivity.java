package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static FragmentManager fm = null;
    ChipNavigationBar chipNavigationBar;
    Session session;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        session = new Session(activity);

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
    private void userDetails()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("USER_DETAILS",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.BALANCE,jsonArray.getJSONObject(0).getString(Constant.BALANCE));
                        if(jsonArray.getJSONObject(0).getString(Constant.STATUS).equals("1")){
                            session.logoutUser(activity);
                        }
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.USER_DETAILS_URL, params,false);

    }
    @Override
    protected void onStart() {
        super.onStart();
        userDetails();
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}