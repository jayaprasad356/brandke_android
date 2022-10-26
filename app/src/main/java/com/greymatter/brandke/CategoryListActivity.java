package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.Adapter.CategoryListAdapter;
import com.greymatter.brandke.Models.Categorylist;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryListActivity extends AppCompatActivity {

    RecyclerView categoryRecycleView;
    CategoryListAdapter categoryAdapter;
    ImageView backbtn;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);


        activity = CategoryListActivity.this;

        categoryRecycleView = findViewById(R.id.categoryRecycleView);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
        categoryRecycleView.setLayoutManager(gridLayoutManager);
        categorylist();



    }


    private void categorylist() {

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Categorylist> categories = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Categorylist group = g.fromJson(jsonObject1.toString(), Categorylist.class);
                                categories.add(group);
                            } else {
                                break;
                            }
                        }
                        categoryAdapter = new CategoryListAdapter(activity, categories);
                        categoryRecycleView.setAdapter(categoryAdapter);



                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CATEGORY_LIST_URL, params, true);


    }
}