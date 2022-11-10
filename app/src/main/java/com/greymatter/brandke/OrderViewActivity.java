package com.greymatter.brandke;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greymatter.brandke.helper.Constant;

public class OrderViewActivity extends AppCompatActivity {

    ImageView backimg;
    TextView tvOderId,tvProductname,tvDate,tvQuantity,tvStatus,tvAmount;
    ImageView imageProduct;
    String Orderid,ProductName,Date,Quantity,Status,Amount,Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);


        Orderid = getIntent().getStringExtra(Constant.ID);
        ProductName = getIntent().getStringExtra(Constant.PRODUCT_NAME);
        Date = getIntent().getStringExtra(Constant.DATE);
        Quantity = getIntent().getStringExtra(Constant.QUANTITY);
        Status = getIntent().getStringExtra(Constant.STATUS);
        Amount = getIntent().getStringExtra(Constant.TOTAL_PRICE);
        Image = getIntent().getStringExtra(Constant.PRODUCT_IMAGE);




        backimg = findViewById(R.id.backimg);
        tvOderId = findViewById(R.id.tvOderId);
        tvProductname = findViewById(R.id.tvProductname);
        tvDate = findViewById(R.id.tvDate);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvStatus = findViewById(R.id.tvStatus);
        tvAmount = findViewById(R.id.tvAmount);
        imageProduct = findViewById(R.id.imageProduct);

        Glide.with(OrderViewActivity.this).load(Image).into(imageProduct);


        tvOderId.setText("Order No. "+Orderid);
        tvQuantity.setText("items "+Quantity);
        tvProductname.setText(ProductName);
        tvAmount.setText("For Amount : "+Amount);
        tvDate.setText("Oder Placed On : " + Date);
        tvStatus.setText("Status : "+Status);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}