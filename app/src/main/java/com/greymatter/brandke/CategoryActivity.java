package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.greymatter.brandke.Adapter.CategoryListAdapter;
import com.greymatter.brandke.Adapter.ProductAdapter;
import com.greymatter.brandke.Models.Categorylist;
import com.greymatter.brandke.Models.Product;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity{

    RecyclerView recycleView;
    Activity activity;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        activity = CategoryActivity.this;

        recycleView = findViewById(R.id.categoryRecycleView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
        recycleView.setLayoutManager(gridLayoutManager);


        productlist();

    }

    private void productlist() {

        ArrayList<Product> products = new ArrayList<>();

        Product categorylist1 = new Product("","Fruits","");
        Product categorylist2 = new Product("","Fruits","");
        Product categorylist3 = new Product("","Fruits","");
        Product categorylist4 = new Product("","Fruits","");

        products.add(categorylist1);
        products.add(categorylist2);
        products.add(categorylist3);
        products.add(categorylist4);



        productAdapter = new ProductAdapter(activity,products );
        recycleView.setAdapter(productAdapter);
    }


}