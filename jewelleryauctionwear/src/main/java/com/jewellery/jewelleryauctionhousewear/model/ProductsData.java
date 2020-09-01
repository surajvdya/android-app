package com.jewellery.jewelleryauctionhousewear.model;

public class ProductsData {
    private String _id;
    private String product_Image;
    private String product_name;
    private String product_category;
    private String base_price;
    private String start_date;
    private String end_date;
    private String highest_bid;
    private String email;

    public ProductsData(String product_Image, String product_name, String product_category, String base_price, String start_date, String end_date, String highest_bid, String email) {
        this.product_Image = product_Image;
        this.product_name = product_name;
        this.product_category = product_category;
        this.base_price = base_price;
        this.start_date = start_date;
        this.end_date = end_date;
        this.highest_bid = highest_bid;
        this.email = email;
    }


    public ProductsData(String _id, String product_Image, String product_name, String product_category, String base_price, String start_date, String end_date, String highest_bid, String email) {
        this._id = _id;
        this.product_Image = product_Image;
        this.product_name = product_name;
        this.product_category = product_category;
        this.base_price = base_price;
        this.start_date = start_date;
        this.end_date = end_date;
        this.highest_bid = highest_bid;
        this.email = email;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public String get_id() {
        return _id;
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

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getHighest_bid() {
        return highest_bid;
    }

    public void setHighest_bid(String highest_bid) {
        this.highest_bid = highest_bid;
    }

    public String getEmail() {
        return email;
    }
}