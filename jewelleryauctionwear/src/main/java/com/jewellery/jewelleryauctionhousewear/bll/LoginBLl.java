package com.jewellery.jewelleryauctionhousewear.bll;

import android.util.Log;

import com.jewellery.jewelleryauctionhousewear.api.Api;
import com.jewellery.jewelleryauctionhousewear.api.ApiClass;
import com.jewellery.jewelleryauctionhousewear.model.Signup_Response;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLl {
    private  boolean isSucess= false;
    public static String Token;

    public boolean checkUser(String email, String password){
        Log.d("username",""+email);
        Log.d("password",""+password);
        ApiClass usersAPI=new ApiClass();
        Call<Signup_Response> userCall=usersAPI.calls().checkUser(email,password);
        try{
            Response<Signup_Response> loginResponse=userCall.execute();
//            if(loginResponse.isSuccessful()&&
//            loginResponse.body().getStatus().equals("Login Success!")){
            Signup_Response signupresponse = loginResponse.body();
            if(signupresponse.getStatus().equals("Login success!")){
                Token = signupresponse.getToken();
                isSucess = true;
                Log.d("token", "" + Token);
                //
            } else if(signupresponse.getStatus().equals("Failed")){
                isSucess=false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }return isSucess;
    }
}
