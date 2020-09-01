package com.jewelleryauction.jewelleryauctionhouse.model;

public class MyProductModel {
    private String product_Image;
    private String product_name;
    private String base_price;
    private String end_date;
    private String email;

    public MyProductModel(String product_Image, String product_name, String base_price, String end_date, String email) {
        this.product_Image = product_Image;
        this.product_name = product_name;
        this.base_price = base_price;
        this.end_date = end_date;
        this.email = email;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
