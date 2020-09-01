package com.jewellery.jewelleryauctionhousewear.model;

public class Signup_Response {
    private String status;
    private String token;

    public Signup_Response(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }
}
