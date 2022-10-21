package com.greymatter.brandke.Models;

import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class Category {

    private int ProductImage;
    private String ProductName;
    private String ProductOfferPrice;
    private String ProductOriginalPrice;
    private String ProductQuantity;
    private int LikeBtn;
    private String ProductOffPercent;

    public Category(int productImage, String productName, String productOfferPrice, String productOriginalPrice, String productQuantity, String productOffPercent,int likeBtn) {
        ProductImage = productImage;
        ProductName = productName;
        ProductOfferPrice = productOfferPrice;
        ProductOriginalPrice = productOriginalPrice;
        ProductQuantity = productQuantity;
        ProductOffPercent = productOffPercent;
        LikeBtn = likeBtn;
    }

    public Category(int productImage, String productName) {
        ProductImage = productImage;
        ProductName = productName;
    }

    public int getProductImage() {
        return ProductImage;
    }

    public void setProductImage(int productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductOfferPrice() {
        return ProductOfferPrice;
    }

    public void setProductOfferPrice(String productOfferPrice) {
        ProductOfferPrice = productOfferPrice;
    }

    public String getProductOriginalPrice() {
        return ProductOriginalPrice;
    }

    public void setProductOriginalPrice(String productOriginalPrice) {
        ProductOriginalPrice = productOriginalPrice;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public int getLikeBtn() {
        return LikeBtn;
    }

    public void setLikeBtn(int likeBtn) {
        LikeBtn = likeBtn;
    }

    public String getProductOffPercent() {
        return ProductOffPercent;
    }

    public void setProductOffPercent(String productOffPercent) {
        ProductOffPercent = productOffPercent;
    }
}
