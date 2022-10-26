package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class WalletActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Activity activity;

    private CardView cvRecharge,CvCancel,CvRechargeInDialog;
    private EditText etAmount,etMessage;

    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        activity = WalletActivity.this;
        recyclerView = findViewById(R.id.WalletRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);
        //BuildRecyclerView();

        backbtn=findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        cvRecharge = findViewById(R.id.cvRechargeWallet);
        CvCancel = dialog.findViewById(R.id.cvCancel);
        CvRechargeInDialog = dialog.findViewById(R.id.cvRecharge);
        CvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        CvRechargeInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Recharge done", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },2000);

            }
        });
        cvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

//    private void BuildRecyclerView() {
//        walletModelArrayList = new ArrayList<>();
//        walletModelArrayList.add(new WalletModel("-₹1500","22-10-2022","Debit"));
//        walletModelArrayList.add(new WalletModel("-₹1200","22-10-2022","Credit"));
//        walletAdapter = new Adaptors.WalletAdapter(walletModelArrayList,activity);
//        recyclerView.setAdapter(walletAdapter);
//    }
}