package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import Models.CartModel;

public class CartActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Activity activity;
    private ArrayList<CartModel> cartModelArrayList;
    private Adaptors.CartAdapter orderAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_acitivity);
        activity = CartActivity.this;
        recyclerView = findViewById(R.id.CartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        BuildRecyclerView();
    }

    private void BuildRecyclerView() {
        cartModelArrayList = new ArrayList<>();
        cartModelArrayList.add(new CartModel(R.drawable.image, "Npk Urea","3000","3549","1Pc","2"));
        orderAdaptor = new Adaptors.CartAdapter(cartModelArrayList,activity);
        recyclerView.setAdapter(orderAdaptor);
    }
}