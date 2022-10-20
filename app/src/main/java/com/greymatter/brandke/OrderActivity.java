package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import Models.OrderModel;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Activity activity;
    private ArrayList<OrderModel> orderModelArrayList;
    private Adaptors.OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        activity = OrderActivity.this;
        recyclerView = findViewById(R.id.order);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        BuildRecyclerView();
    }

    private void BuildRecyclerView() {
        orderModelArrayList = new ArrayList<>();
        orderModelArrayList.add(new OrderModel("1","1","Paddy","20/10/2022","8000","Processing"));
        orderModelArrayList.add(new OrderModel("2","1","Wheat","19/10/2022","5000","Processing"));
        orderAdapter = new Adaptors.OrderAdapter(orderModelArrayList,activity);
        recyclerView.setAdapter(orderAdapter);
    }
}