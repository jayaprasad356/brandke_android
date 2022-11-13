package com.kapp.bharat.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kapp.bharat.CartActivity;
import com.kapp.bharat.Models.Product;
import com.kapp.bharat.ProductDetailsActivity;
import com.kapp.bharat.R;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Product> products;
    Session session;

    public ProductAdapter(Activity activity, ArrayList<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.protuct_list, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        session = new Session(activity);
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Product product = products.get(position);

        Glide.with(activity).load(product.getImage()).placeholder(R.drawable.product_img).into(holder.ImgProduct);
        holder.tvName.setText(product.getProduct_name());
        holder.tvPrice.setText("₹ "+product.getPrice());
        holder.tvMeasurement.setText(product.getMeasurement()+product.getUnit());
        holder.btnAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addcart(product.getId());

                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                intent.putExtra(Constant.ID,product.getId());
                intent.putExtra(Constant.PRODUCT_NAME,product.getProduct_name());
                intent.putExtra(Constant.PRICE,product.getPrice());
                intent.putExtra(Constant.PRODUCT_DESCRIPTION,product.getDescription());
                intent.putExtra(Constant.PRODUCT_IMAGE,product.getImage());
                intent.putExtra(Constant.PRODUCT_BRAND,product.getBrand());
                intent.putExtra(Constant.MEASUREMENT,product.getMeasurement()+product.getUnit());
                activity.startActivity(intent);
            }
        });





//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(activity, CartActivity.class);
////                intent.putExtra(Constant.ID,product.getId());
////                intent.putExtra(Constant.PRODUCT_NAME,product.getProduct_name());
////                intent.putExtra(Constant.PRICE,product.getPrice());
////                intent.putExtra(Constant.PRODUCT_DESCRIPTION,product.getDescription());
////                intent.putExtra(Constant.PRODUCT_IMAGE,product.getImage());
////                intent.putExtra(Constant.PRODUCT_BRAND,product.getBrand());
////                activity.startActivity(intent);
//
//
//
//            }
//        });

    }

    private void addcart(String id) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.PRODUCT_ID,id);
        params.put(Constant.QUANTITY,"1");
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, "Product Added To Cart", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,CartActivity.class);
                        activity.startActivity(intent);
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.ADD_TO_CART_URL, params,true);



    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView ImgProduct;
        final TextView tvName,tvPrice,tvMeasurement;
        final Button btnAddcart;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            ImgProduct = itemView.findViewById(R.id.ImgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvMeasurement = itemView.findViewById(R.id.tvMeasurement);
            btnAddcart = itemView.findViewById(R.id.btnAddcart);

        }
    }
}
