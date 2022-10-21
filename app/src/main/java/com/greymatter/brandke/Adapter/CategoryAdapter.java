package com.greymatter.brandke.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.greymatter.brandke.Models.Category;
import com.greymatter.brandke.R;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {

    private ArrayList<Category> categories;
    private Context context;
    private final RecyclerOnClickListener recyclerOnClickListener;

    public CategoryAdapter(ArrayList<Category> categories, Context context,RecyclerOnClickListener recyclerOnClickListener) {
        this.categories = categories;
        this.context = context;
        this.recyclerOnClickListener = recyclerOnClickListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list_main,parent,false);
        return new viewholder(view,recyclerOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Category model = categories.get(position);
        holder.ProductName.setText(model.getProductName());
        holder.ProductImage.setImageResource(model.getProductImage());
        holder.ProductOriginalPrice.setText(model.getProductOriginalPrice());
        holder.ProductOfferPrice.setText(model.getProductOfferPrice());
        holder.ProductQuantity.setText(model.getProductQuantity());
        holder.LikeBtn.setAnimation(R.raw.love);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    protected class viewholder extends RecyclerView.ViewHolder {

        private ImageView ProductImage;
        private TextView ProductName;
        private TextView ProductOfferPrice;
        private TextView ProductOriginalPrice;
        private TextView ProductQuantity;
        private LottieAnimationView LikeBtn;
        private RelativeLayout btnAdd;

        public viewholder(@NonNull View itemView,RecyclerOnClickListener recyclerOnClickListener) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.categoryImage);
            ProductName = itemView.findViewById(R.id.CategoryName);
            ProductOfferPrice =  itemView.findViewById(R.id.categoryOfferPrice);
            ProductOriginalPrice = itemView.findViewById(R.id.CategoryOriginalPrice);
            ProductQuantity = itemView.findViewById(R.id.CategoryQuantity);
            LikeBtn = itemView.findViewById(R.id.ProductLikeBtn);
            btnAdd = itemView.findViewById(R.id.ProductAddBtn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position == RecyclerView.NO_POSITION) {
                        recyclerOnClickListener.onRecyclerItemClick(position);
                    }
                }
            });
        }
    }
}
