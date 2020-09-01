package com.jewelleryauction.jewelleryauctionhouse.model;

public class Signup_response {

    private String status;
    private String token;

    public Signup_response(String status, String token) {
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
