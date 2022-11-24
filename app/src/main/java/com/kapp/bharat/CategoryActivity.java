package com.kapp.bharat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;
import com.kapp.bharat.Adapter.ProductAdapter;
import com.kapp.bharat.Models.Product;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;

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
    TextView tvCatName,tvFromRange,tvToRange;
    Session session;
    String getproductname;
    RangeSlider rangeSlider;
    String from = "0",to = "";
    String from_m = "0",to_m = "";
    ImageView backbtn;
    Button btnFilter;
    String Measurement = "",Brand = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        activity = CategoryActivity.this;
        session = new Session(activity);

        rangeSlider = findViewById(R.id.rangeSlider);
        tvFromRange = findViewById(R.id.tvFromRange);
        tvToRange = findViewById(R.id.tvToRange);
        btnFilter = findViewById(R.id.btnFilter);

        rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {


            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                //Log.d("PRICE_RANGE",slider.getValues().get(0) + "");
                from = Math.round(slider.getValues().get(0))+ "";
                to = Math.round(slider.getValues().get(1))+ "";


                tvFromRange.setText(from);
                tvToRange.setText(to);


            }
        });


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
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBoottomsheet();

            }
        });
        productlist();



    }

    private void showBoottomsheet()
    {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.filter_bottom_layout);
        Button btnFilter = bottomSheetDialog.findViewById(R.id.btnFilter);
        TextView tvFromRange = bottomSheetDialog.findViewById(R.id.tvFromRange);
        TextView tvToRange = bottomSheetDialog.findViewById(R.id.tvToRange);
        TextView tvFromMeasure = bottomSheetDialog.findViewById(R.id.tvFromMeasure);
        TextView tvToMeasure = bottomSheetDialog.findViewById(R.id.tvToMeasure);
        RangeSlider pricerangeSlider = bottomSheetDialog.findViewById(R.id.pricerangeSlider);
        RangeSlider rangeSliderMeasure = bottomSheetDialog.findViewById(R.id.rangeSliderMeasure);
        Spinner brandSpinner = bottomSheetDialog.findViewById(R.id.brandSpinner);
        Spinner MeasurmentSpinner = bottomSheetDialog.findViewById(R.id.MeasurmentSpinner);



        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (brandSpinner.getSelectedItemId() == 0){
                    Brand = "";
                }else {
                    Brand = brandSpinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pricerangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {


            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                //Log.d("PRICE_RANGE",slider.getValues().get(0) + "");
                from = Math.round(slider.getValues().get(0))+ "";
                to = Math.round(slider.getValues().get(1))+ "";


                tvFromRange.setText("₹ "+from);
                tvToRange.setText("₹ "+to);


            }
        });
        rangeSliderMeasure.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {



            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                //Log.d("PRICE_RANGE",slider.getValues().get(0) + "");
                from_m = Math.round(slider.getValues().get(0))+ "";
                to_m = Math.round(slider.getValues().get(1))+ "";
                tvFromMeasure.setText(from_m);
                tvToMeasure.setText(to_m);


            }
        });
        Measurement = MeasurmentSpinner.getSelectedItem().toString();

//        radio1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!radio1.isSelected()) {
//                    radio1.setChecked(true);
//                    radio1.setSelected(true);
//                    Measurement = radio1.getText().toString();
//                } else {
//                    radio2.setChecked(false);
//                    radio2.setSelected(false);
//                }
//            }
//        });
//        radio2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!radio2.isSelected()) {
//                    radio2.setChecked(true);
//                    radio2.setSelected(true);
//                    Measurement = radio2.getText().toString();
//                } else {
//                    radio1.setChecked(false);
//                    radio1.setSelected(false);
//                }
//            }
//        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productlist();
                bottomSheetDialog.dismiss();
            }
        });



        bottomSheetDialog.show();
    }

    private void productlist() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.CATEGORY_ID,CategoryId);
        params.put(Constant.FROM,from);
        params.put(Constant.TO,to);
        params.put(Constant.FROM_M,from_m);
        params.put(Constant.TO_M,to_m);
        params.put(Constant.BRAND,Brand);
        params.put(Constant.UNIT,Measurement);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("PRODUCTS_RES",response);
            Log.d("PRODUCTS_PAR",CategoryId  +" - " + from + " - " + to + " - " + from_m + " - " + to_m + " - " + Brand +" - " +  Measurement);

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
                        ArrayList<Product> products = new ArrayList<>();
                        productAdapter = new ProductAdapter(CategoryActivity.this, products);
                        recycleView.setAdapter(productAdapter);
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PRODUCT_LIST_URL, params, true);

    }


}