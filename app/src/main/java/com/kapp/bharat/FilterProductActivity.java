package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.kapp.bharat.Adapter.ProductAdapter;
import com.kapp.bharat.Models.Product;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterProductActivity extends AppCompatActivity {
    ImageView backimg;
    RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    Activity activity;
    String From,To;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_product);
        activity = FilterProductActivity.this;
        backimg = findViewById(R.id.backimg);
        productRecycleView = findViewById(R.id.productRecycleView);
        From = getIntent().getStringExtra(Constant.FROM);
        To = getIntent().getStringExtra(Constant.TO);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
        productRecycleView.setLayoutManager(gridLayoutManager);
        productList();


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void productList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.FROM, From);
        params.put(Constant.TO, To);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("FILTER_PRO",response);

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Product> products = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Product group = g.fromJson(jsonObject1.toString(), Product.class);
                                products.add(group);
                            } else {
                                break;
                            }
                        }
                        productAdapter = new ProductAdapter(activity, products);
                        productRecycleView.setAdapter(productAdapter);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.FILTER_LIST, params, true);


    }
}