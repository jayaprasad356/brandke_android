package com.kapp.bharat.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapp.bharat.Models.Order;
import com.kapp.bharat.OrderViewActivity;
import com.kapp.bharat.R;
import com.kapp.bharat.helper.Constant;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Order> orders;

    public OrderAdapter(Activity activity, ArrayList<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.order_model_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Order order = orders.get(position);

        holder.tvId.setText("Order No. "+order.getId());
        holder.tvQuantity.setText(order.getQuantity()+" items");
        holder.tvProductname.setText(order.getProduct_name());
        holder.tvTotal.setText(order.getTotal());
        holder.tvOrderdate.setText(order.getOrder_date());
        holder.tvStatus.setText(order.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, OrderViewActivity.class);
                intent.putExtra(Constant.ID,order.getId());
                intent.putExtra(Constant.PRODUCT_NAME,order.getProduct_name());
                intent.putExtra(Constant.TOTAL_PRICE,order.getTotal());
                intent.putExtra(Constant.DATE,order.getOrder_date());
                intent.putExtra(Constant.STATUS,order.getStatus());
                intent.putExtra(Constant.QUANTITY,order.getQuantity());
                intent.putExtra(Constant.PRODUCT_IMAGE,order.getImage());
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final TextView tvId,tvQuantity,tvProductname,tvOrderdate;
        final TextView tvTotal,tvStatus;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductname = itemView.findViewById(R.id.tvProductname);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvOrderdate = itemView.findViewById(R.id.tvOrderdate);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }
}

