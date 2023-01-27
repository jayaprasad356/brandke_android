package com.kapp.bharat.Models;

public class Product {
    String id;
    String product_name;
    String category_name;
    String image;
    String description;
    String brand;
    String price;
    String measurement;
    String unit;
    String mrp;
    String discount_percentage;
    String image1;
    String image2;

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }



    public Product(String id, String product_name, String category_name, String image, String description, String brand, String price, String measurement, String unit, String mrp, String discount_percentage) {
        this.id = id;
        this.product_name = product_name;
        this.category_name = category_name;
        this.image = image;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.measurement = measurement;
        this.unit = unit;
        this.mrp = mrp;
        this.discount_percentage = discount_percentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(String discount_percentage) {
        this.discount_percentage = discount_percentage;
    }
}
