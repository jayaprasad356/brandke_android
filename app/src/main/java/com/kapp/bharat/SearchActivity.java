package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class SearchActivity extends AppCompatActivity {

    EditText etSearch;
    Activity activity;
    ImageView backimg;
    RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    ImageButton ibFilterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = SearchActivity.this;
        etSearch = findViewById(R.id.etSearch);
        backimg = findViewById(R.id.backimg);
        ibFilterBtn = findViewById(R.id.ibFilterBtn);

        productRecycleView = findViewById(R.id.productRecycleView);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        });

        ibFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FilterActivity.class);
                startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
                    productRecycleView.setLayoutManager(gridLayoutManager);
                    productList();

                }

            }
        });

    }

    private void productList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.SEARCH, etSearch.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("SEARCH_RES", response);

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
        }, activity, Constant.SEARCH_LIST, params, true);


    }

}