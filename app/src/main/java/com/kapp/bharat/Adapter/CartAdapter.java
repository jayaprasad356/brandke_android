package com.kapp.bharat.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kapp.bharat.CartActivity;
import com.kapp.bharat.Models.Cart;
import com.kapp.bharat.R;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Cart> carts;

    String type;
    public CartAdapter(Activity activity, ArrayList<Cart> carts, String type) {
        this.activity = activity;
        this.carts = carts;
        this.type = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cart_model_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, @SuppressLint("RecyclerView") int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Cart cart = carts.get(position);
        final int[] i = {1};

        Glide.with(activity).load(cart.getImage()).placeholder(R.drawable.image).into(holder.imgProduct);
        holder.tvName.setText(cart.getProduct_name());
        holder.tvPrice.setText("₹ "+cart.getPrice());
        holder.tvQuantity.setText("("+cart.getQuantity()+")");
        if (type.equals("checkout")){
            holder.tvDelete.setVisibility(View.INVISIBLE);

        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCart(cart.getId(),position);
            }
        });
        holder.cvBtnIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i[0]++ ;
                       holder.tvResultCount.setText("" + i[0]);

                    }
                });
        holder.cvBtnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i[0]-- ;
                holder.tvResultCount.setText("" + i[0]);


            }
        });


    }

    private void deleteCart(String id, int position)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.CART_ID,id);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        carts.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, carts.size());
                        ((CartActivity)activity).cartList();


                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.DELETE_CART_URL, params,true);


    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView imgProduct;
        final TextView tvName,tvPrice,tvQuantity,tvDelete,tvResultCount;
        final CardView cvBtnDecrement,cvBtnIncrement;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            cvBtnIncrement = itemView.findViewById(R.id.cvBtnIncrement);
            cvBtnDecrement = itemView.findViewById(R.id.cvBtnDecrement);
            tvResultCount = itemView.findViewById(R.id.tvResultCount);
        }
    }
}
