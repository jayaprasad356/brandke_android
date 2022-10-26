package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.Adapter.OrderAdapter;
import com.greymatter.brandke.Models.Order;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    RecyclerView orderRecycleView;
    OrderAdapter orderAdapter;
    Activity activity;
    Session session;
    ImageView backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        activity = OrderActivity.this;
        session = new Session(activity);
        orderRecycleView = findViewById(R.id.order);
        backimg = findViewById(R.id.backimg);

        orderRecycleView.setLayoutManager(new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL, false));

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        orderList();

    }

    private void orderList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Order> orders = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Order group = g.fromJson(jsonObject1.toString(), Order.class);
                                orders.add(group);
                            } else {
                                break;
                            }
                        }
                        orderAdapter = new OrderAdapter(activity, orders);
                        orderRecycleView.setAdapter(orderAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.ORDER_LIST_URL, params, true);





    }

}