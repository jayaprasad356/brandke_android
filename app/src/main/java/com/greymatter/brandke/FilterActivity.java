package com.greymatter.brandke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;
import com.greymatter.brandke.helper.Constant;

public class FilterActivity extends AppCompatActivity {

    ImageView backimg;
    RangeSlider rangeSlider;
    String from,to;
    Button btnFilter;
    TextView tvFromRange,tvToRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        rangeSlider = findViewById(R.id.rangeSlider);


        backimg = findViewById(R.id.backimg);
        btnFilter = findViewById(R.id.btnFilter);
        tvToRange = findViewById(R.id.tvToRange);
        tvFromRange = findViewById(R.id.tvFromRange);


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
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
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FilterActivity.this,FilterProductActivity.class);
                intent.putExtra(Constant.FROM,from);
                intent.putExtra(Constant.TO,to);
                startActivity(intent);
            }
        });


    }
}