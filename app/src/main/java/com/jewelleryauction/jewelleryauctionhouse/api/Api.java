package com.jewelleryauction.jewelleryauctionhouse.api;
import com.jewelleryauction.jewelleryauctionhouse.model.Bidm;
import com.jewelleryauction.jewelleryauctionhouse.model.ImageModel;
import com.jewelleryauction.jewelleryauctionhouse.model.MyProductModel;
import com.jewelleryauction.jewelleryauctionhouse.model.ProductsData;
import com.jewelleryauction.jewelleryauctionhouse.model.Signup_response;
import com.jewelleryauction.jewelleryauctionhouse.model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

        @FormUrlEncoded
        @POST("users/login")
        Call<Signup_response>checkUser(@Field("email") String username,@Field("password")String password);

        @Multipart
        @POST("upload")
        Call<ImageModel>uploadImage(@Part MultipartBody.Part image);

        @POST("users/signup")
        Call<Signup_response>register(@Body Users cud);

        @GET("product/addproduct")
        Call<List<ProductsData>> getproducts(@Header("Authorization")String token);

        @FormUrlEncoded
        @PATCH("product/bid/{id}")
        Call<Bidm> getBid( @Path("id") String bid, @Header("Authorization") String s ,@Field("highest_bid") String highest_bid);

        @GET("users/me")
        Call<Users>getUser(@Header("Authorization")String token);

        @PATCH("users/update/{id}")
        Call<Users>userUpdate(@Path("id")String id,@Body Users cud);

        @POST("myproduct/addmyproduct")
        Call<List<MyProductModel>> addmyproduct(@Header("Authorization") String token, @Body MyProductModel myProductModel);

        @GET("myproduct/myproducts")
        Call<List<MyProductModel>>myproducts(@Header("Authorization")String token);
    }


