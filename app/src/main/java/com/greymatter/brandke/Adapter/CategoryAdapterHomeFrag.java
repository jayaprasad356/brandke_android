package com.greymatter.brandke.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.brandke.CategoryActivity;
import com.greymatter.brandke.Models.Category;
import com.greymatter.brandke.R;

import java.util.ArrayList;


public class CategoryAdapterHomeFrag extends RecyclerView.Adapter<CategoryAdapterHomeFrag.viewholder> {

    private ArrayList<Category> categories;
    private Context context;
    private final RecyclerOnClickListener recyclerOnClickListener;

    public CategoryAdapterHomeFrag(ArrayList<Category> categories, Context context,RecyclerOnClickListener recyclerOnClickListener) {
        this.categories = categories;
        this.context = context;
        this.recyclerOnClickListener = recyclerOnClickListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list,parent,false);
        return new viewholder(view,recyclerOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Category model = categories.get(position);
        holder.ProductName.setText(model.getProductName());
        holder.ProductImage.setImageResource(model.getProductImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CategoryActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    protected class viewholder extends RecyclerView.ViewHolder {

        private ImageView ProductImage;
        private TextView ProductName;

        public viewholder(@NonNull View itemView,RecyclerOnClickListener recyclerOnClickListener) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.ProductImage);
            ProductName = itemView.findViewById(R.id.ProductName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos= getAdapterPosition();
                    if(pos == RecyclerView.NO_POSITION) {
                        recyclerOnClickListener.onRecyclerItemClick(pos,getItemCount());
                    }
                }
            });
        }
    }
}
