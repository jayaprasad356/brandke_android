package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.greymatter.brandke.Adapter.CategoryAdapter;
import com.greymatter.brandke.Adapter.RecyclerOnClickListener;
import com.greymatter.brandke.Models.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements RecyclerOnClickListener{

    RecyclerView categoryRecycleView;
    private CategoryAdapter categoryAdapter;
    protected ArrayList<Category> categoryArrayList;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        activity = CategoryActivity.this;

        categoryRecycleView = findViewById(R.id.categoryRecycleView);
        categoryRecycleView.setLayoutManager(new GridLayoutManager(this,2));
        categoryRecycleView.setHasFixedSize(true);
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryArrayList.add(new Category(R.drawable.product_img,"Wheat","₹300","₹500","2kg","40%off",R.raw.love));
        categoryAdapter = new CategoryAdapter(categoryArrayList,this,this);
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    @Override
    public void onRecyclerItemClick(int position) {

    }

    @Override
    public void onRecyclerItemClick(int position, int Size) {

    }
}