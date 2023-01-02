package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kapp.bharat.Adapter.WalletRecyclerAdapter;
import com.kapp.bharat.Models.WalletRecyclerModel;
import java.util.ArrayList;

public class WalletX2Activity extends AppCompatActivity {


    private WalletRecyclerAdapter walletRecyclerAdapter;
    private ArrayList<WalletRecyclerModel> walletRecyclerModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_x2);
        //TODO : need to initialize recyclerview Adapter and Model is Created
        walletRecyclerModelArrayList = new ArrayList<>();
        walletRecyclerModelArrayList.add(new WalletRecyclerModel("1234567890",
                "12345690",
                "567890",
                "2345679",
                "67890",
                "gsjadjgd",
                "5678989"));
        walletRecyclerModelArrayList.add(new WalletRecyclerModel("1234567890",
                "12345690",
                "567890",
                "2345679",
                "67890",
                "gsjadjgd",
                "5678989"));
        walletRecyclerModelArrayList.add(new WalletRecyclerModel("1234567890",
                "12345690",
                "567890",
                "2345679",
                "67890",
                "gsjadjgd",
                "5678989"));
        walletRecyclerModelArrayList.add(new WalletRecyclerModel("1234567890",
                "12345690",
                "567890",
                "2345679",
                "67890",
                "gsjadjgd",
                "5678989"));
        walletRecyclerModelArrayList.add(new WalletRecyclerModel("1234567890",
                "12345690",
                "567890",
                "2345679",
                "67890",
                "gsjadjgd",
                "5678989"));
        walletRecyclerAdapter = new WalletRecyclerAdapter(this,walletRecyclerModelArrayList);
    }
}