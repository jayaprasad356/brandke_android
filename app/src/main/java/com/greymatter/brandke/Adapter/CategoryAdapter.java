package com.greymatter.brandke.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.brandke.Models.Category;
import com.greymatter.brandke.R;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {

    private ArrayList<Category> categories;
    private Context context;

    public CategoryAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Category model = categories.get(position);
        holder.ProductName.setText(model.getProductName());
        holder.ProductImage.setImageResource(model.getProductImage());
        holder.ProductOriginalPrice.setText(model.getProductOriginalPrice());
        holder.ProductOfferPrice.setText(model.getProductOfferPrice());
        holder.TvTotalCount.setText(model.getProductQuantity());
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
        private CardView CvBtnDecrement;
        private CardView CvBtnIncrement;
        private TextView TvTotalCount;
        private TextView TvDelete;
        private TextView TvSaveForLater;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.ProductImage);
            ProductName = itemView.findViewById(R.id.ProductName);
            ProductOfferPrice =  itemView.findViewById(R.id.ProductOfferPrice);
            ProductOriginalPrice = itemView.findViewById(R.id.ProductOriginalPrice);
            ProductQuantity = itemView.findViewById(R.id.ProductQuantity);
            CvBtnDecrement = itemView.findViewById(R.id.cvBtnDecrement);
            CvBtnIncrement = itemView.findViewById(R.id.cvBtnIncrement);
            TvTotalCount = itemView.findViewById(R.id.tvResultCount);
            TvDelete = itemView.findViewById(R.id.tvProductDelete);
            TvSaveForLater = itemView.findViewById(R.id.tvSaveForLater);
        }
    }
}
