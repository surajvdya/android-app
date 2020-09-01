package com.jewelleryauction.jewelleryauctionhouse.model;

public class Users {
    String _id;
    String first_name;
    String last_name;
    String gender;
    String email;
    String user_image;
    String password;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String first_name, String last_name, String gender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public Users(String _id, String first_name, String last_name, String gender, String email, String user_image, String password) {
        this._id = _id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.email = email;
        this.user_image = user_image;
        this.password = password;
    }

    public Users(String first_name, String last_name, String gender, String email, String user_image, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.email = email;
        this.user_image = user_image;
        this.password = password;
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getPassword() {
        return password;
    }

    public String get_id() {
        return _id;
    }
}
