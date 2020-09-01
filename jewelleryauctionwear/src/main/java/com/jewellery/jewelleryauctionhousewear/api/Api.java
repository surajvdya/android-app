package com.jewellery.jewelleryauctionhousewear.api;

import com.jewellery.jewelleryauctionhousewear.model.ProductsData;
import com.jewellery.jewelleryauctionhousewear.model.Signup_Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("users/login")
    Call<Signup_Response> checkUser(@Field("email") String username, @Field("password")String password);


    @GET("product/addproduct")
    Call<List<ProductsData>> getproducts(@Header("Authorization")String token);

}
