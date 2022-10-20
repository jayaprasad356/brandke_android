package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import Adaptors.NotificationAdaptor;
import Adaptors.OrderAdaptor;
import Models.NotificationModel;
import Models.OrderModel;

public class NotificationActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Activity activity;
    private ArrayList<NotificationModel> notificationModelArrayList;
    private NotificationAdaptor notificationAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        activity = NotificationActivity.this;
        recyclerView = findViewById(R.id.notification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        BuildRecyclerView();
    }

    private void BuildRecyclerView() {
        notificationModelArrayList = new ArrayList<>();
        notificationModelArrayList.add(new NotificationModel("Welcome","Thanks for creating the account"));
        notificationModelArrayList.add(new NotificationModel("Offers","Grab the latest offers"));
        notificationAdaptor = new NotificationAdaptor(notificationModelArrayList,activity);
        recyclerView.setAdapter(notificationAdaptor);
    }
}