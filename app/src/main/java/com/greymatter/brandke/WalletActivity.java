package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import Models.WalletModel;

public class WalletActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Activity activity;
    private ArrayList<WalletModel> walletModelArrayList;
    private Adaptors.WalletAdapter walletAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        activity = WalletActivity.this;
        recyclerView = findViewById(R.id.WalletRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);
        BuildRecyclerView();
    }

    private void BuildRecyclerView() {
        walletModelArrayList = new ArrayList<>();
        walletModelArrayList.add(new WalletModel("-₹1500","22-10-2022","Debit"));
        walletModelArrayList.add(new WalletModel("-₹1200","22-10-2022","Credit"));
        walletAdapter = new Adaptors.WalletAdapter(walletModelArrayList,activity);
        recyclerView.setAdapter(walletAdapter);
    }
}