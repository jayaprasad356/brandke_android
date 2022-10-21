package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView categoryRecycleView;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        activity = CategoryActivity.this;

        categoryRecycleView = findViewById(R.id.categoryRecycleView);



    }
}