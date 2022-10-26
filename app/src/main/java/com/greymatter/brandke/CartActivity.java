package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.Adapter.CartAdapter;
import com.greymatter.brandke.Models.Cart;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {


    ImageView backimg;
    RecyclerView cartRecycleView;
    CartAdapter cartAdapter;
    Activity activity;
    Session session;
    LinearLayout r1;
    ImageView imgEmpty;
    TextView tvQuantity,tvTotalPrice,tvcontinuebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_acitivity);

        activity = CartActivity.this;
        session = new Session(activity);
        tvcontinuebtn = findViewById(R.id.tvcontinuebtn);
        backimg = findViewById(R.id.backbtn);
        imgEmpty = findViewById(R.id.imgEmpty);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        r1 = findViewById(R.id.ll1);
        cartRecycleView = findViewById(R.id.cartRecycleView);


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        cartRecycleView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
        tvcontinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,CheckoutActivity.class);
                startActivity(intent);
            }
        });

        cartList();
    }

    public void cartList() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        r1.setVisibility(View.VISIBLE);
                        imgEmpty.setVisibility(View.GONE);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Cart> carts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Cart group = g.fromJson(jsonObject1.toString(), Cart.class);
                                carts.add(group);
                            } else {
                                break;
                            }
                        }
                        cartAdapter = new CartAdapter(activity, carts,"cart");
                        cartRecycleView.setAdapter(cartAdapter);

                    } else {
                        r1.setVisibility(View.GONE);
                        imgEmpty.setVisibility(View.VISIBLE);
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    r1.setVisibility(View.GONE);
                    imgEmpty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CART_LIST_URL, params, true);



    }

}