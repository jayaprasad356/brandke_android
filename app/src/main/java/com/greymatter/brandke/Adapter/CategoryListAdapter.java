package com.greymatter.brandke.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greymatter.brandke.CategoryActivity;
import com.greymatter.brandke.Models.Categorylist;
import com.greymatter.brandke.R;


import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Categorylist> categories;

    public CategoryListAdapter(Activity activity, ArrayList<Categorylist> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.category_list, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Categorylist category = categories.get(position);

        Glide.with(activity).load(category.getImage()).placeholder(R.drawable.product_img).into(holder.ImgProduct);
        holder.tvName.setText(category.getName());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CategoryActivity.class);
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView ImgProduct;
        final TextView tvName;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            ImgProduct = itemView.findViewById(R.id.ImgProduct);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
}
