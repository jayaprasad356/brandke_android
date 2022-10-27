package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.Adapter.CategoryListAdapter;
import com.greymatter.brandke.Adapter.ProductAdapter;
import com.greymatter.brandke.Models.Categorylist;
import com.greymatter.brandke.Models.Product;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity{

    RecyclerView recycleView;
    Activity activity;
    ProductAdapter productAdapter;
    String CategoryId;
    TextView tvCatName;
    Session session;
    String getproductname;

    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        activity = CategoryActivity.this;
        session = new Session(activity);

        CategoryId = getIntent().getStringExtra(Constant.CATEGORY_ID);


        recycleView = findViewById(R.id.categoryRecycleView);
        backbtn = findViewById(R.id.backbtn);
        tvCatName = findViewById(R.id.tvCatName);

        getproductname = getIntent().getStringExtra(Constant.PRODUCT_NAME);

        tvCatName.setText(""+getproductname);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
        recycleView.setLayoutManager(gridLayoutManager);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        productlist();

    }

    private void productlist() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.CATEGORY_ID,CategoryId);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("PRODUCTS_RES",response);

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
                        productAdapter = new ProductAdapter(CategoryActivity.this, products);
                        recycleView.setAdapter(productAdapter);


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PRODUCT_LIST_URL, params, true);

    }


}