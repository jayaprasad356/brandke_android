package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kapp.bharat.Adapter.ImageSliderAdapter;
import com.kapp.bharat.Adapter.SliderAdapterExample;
import com.kapp.bharat.Models.Slide;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    Button addbtn;
    ImageView backimg;
    ImageView imgproduct;
    TextView tv_productname, tvdescription, brand, tvQuantity, tvMeasurement, tvMrpPrice, tvDiscount;
    String getproductname, getdescription, getbrand, getImage, getMrp, getDiscount;
    Activity activity;
    Button btnAddToCart;
    ImageButton imgAdd, imgMinus;
    boolean addedcart = false;
    String ProductId;
    String Price;
    Session session;
    TextView tvPrice;
    SliderView sliderView;
    ViewPager viewPager;
    ImageSliderAdapter iadapter;

    ImageSliderAdapter slideShowAdapter;
    private SliderAdapterExample adapter;
    String Measurement;
    List<Integer> images;
    private List<String> imageUrls = new ArrayList<>();


    private final Handler handler = new Handler();
    private int delay = 3000; // 3 seconds

    private Runnable runnable;


    public ProductDetailsActivity() {
        runnable = null;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        activity = ProductDetailsActivity.this;
        adapter = new SliderAdapterExample(activity);
        session = new Session(activity);

        sliderView = findViewById(R.id.image_slider);

        images = Arrays.asList(R.drawable.applogo, R.drawable.applogo, R.drawable.applogo);
        iadapter = new ImageSliderAdapter(this, imageUrls);


        viewPager = findViewById(R.id.viewpager_id);
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem == imageUrls.size() - 1) {
                    currentItem = 0;
                } else {
                    currentItem++;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(this, delay);
            }
        };
        slideslist();
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        addbtn = findViewById(R.id.addbtn);
        tv_productname = findViewById(R.id.tv_productname);
        tvdescription = findViewById(R.id.tvdescription);
        tvMeasurement = findViewById(R.id.tvMeasurement);
        tvDiscount = findViewById(R.id.tvDiscount);
        imgproduct = findViewById(R.id.imgproduct);
        brand = findViewById(R.id.brand);
        backimg = findViewById(R.id.backbtn);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        imgAdd = findViewById(R.id.btnAddQuantity);
        imgMinus = findViewById(R.id.btnMinusQuantity);
        tvPrice = findViewById(R.id.tvPrice);
        tvMrpPrice = findViewById(R.id.tvMrpPrice);

        getproductname = getIntent().getStringExtra(Constant.PRODUCT_NAME);
        getdescription = getIntent().getStringExtra(Constant.PRODUCT_DESCRIPTION);
        getImage = getIntent().getStringExtra(Constant.PRODUCT_IMAGE);
        getbrand = getIntent().getStringExtra(Constant.PRODUCT_BRAND);
        getDiscount = getIntent().getStringExtra(Constant.PRODUCT_DISCOUNT);
        Measurement = getIntent().getStringExtra(Constant.MEASUREMENT);
        ProductId = getIntent().getStringExtra(Constant.ID);
        Price = getIntent().getStringExtra(Constant.PRICE);
        getMrp = getIntent().getStringExtra(Constant.PRODUCT_MRP);
        tvMeasurement.setText(Measurement);
        tvDiscount.setText(getDiscount + "% off");

        imgMinus.setOnClickListener(v -> addQuantity(false));
        imgAdd.setOnClickListener(v -> addQuantity(true));
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedcart = true;
                addQuantity(true);
            }
        });
        tvQuantity.setText("0");
        Glide.with(activity).load(getImage).placeholder(R.drawable.cartempty).into(imgproduct);
        tv_productname.setText("" + getproductname);
        tvdescription.setText("" + getdescription);
        tvMrpPrice.setText("₹" + getMrp);
        tvMrpPrice.setPaintFlags(tvMrpPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        brand.setText("" + getbrand);
        tvPrice.setText("₹" + Price);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addedcart) {
                    addtoCart();

                } else {
                    Toast.makeText(activity, "Add Quantity", Toast.LENGTH_SHORT).show();
                }

            }
        });
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void slideslist() {
        ArrayList<Slide> slides = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            imageUrls.add(String.valueOf(jsonObject1.get("image")));
                            if (jsonObject1 != null) {
                                com.kapp.bharat.Models.Slide group = g.fromJson(jsonObject1.toString(), Slide.class);
                                slides.add(group);
                            } else {
                                break;
                            }
                        }
                        viewPager.setAdapter(iadapter);
                        adapter.renewItems(slides);
                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SLIDES_LIST, params, true);
    }

    private void addtoCart() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.PRODUCT_ID, ProductId);
        params.put(Constant.QUANTITY, tvQuantity.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("res", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, "Product Added To Cart", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, CartActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.ADD_TO_CART_URL, params, true);


    }

    public void addQuantity(boolean isAdd) {
        int count = Integer.parseInt(tvQuantity.getText().toString());
        if (isAdd) {
            count++;
            tvQuantity.setText("" + count);
        } else {
            count--;
            tvQuantity.setText("" + count);
        }
        if (count == 0) {
            btnAddToCart.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

}