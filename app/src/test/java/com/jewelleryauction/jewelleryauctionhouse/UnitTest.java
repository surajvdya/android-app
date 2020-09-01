package com.jewelleryauction.jewelleryauctionhouse;

import com.jewelleryauction.jewelleryauctionhouse.api.ApiClass;
import com.jewelleryauction.jewelleryauctionhouse.model.Bidm;
import com.jewelleryauction.jewelleryauctionhouse.model.MyProductModel;
import com.jewelleryauction.jewelleryauctionhouse.model.Signup_response;
import com.jewelleryauction.jewelleryauctionhouse.model.Users;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.TestCase.assertTrue;

public class UnitTest {
    @Test
    public void Test_registration() {
        Users users = new Users("", "rohit", "shrestha", "male", "rohit.shrestha@gmail.com", "asd.jpg", "rohit");
        ApiClass apiClass = new ApiClass();
        Call<Signup_response> regiscall = apiClass.calls().register(users);

        try {
            Response<Signup_response> registerResponse = regiscall.execute();
            Signup_response response = registerResponse.body();

            assertTrue(registerResponse.isSuccessful() && (response.getStatus().equals("Success")));

        } catch (IOException e) {
            System.out.println(e);
        }


    }

    @Test
    public void Test_Login() {

        Users users = new Users("helina@gmail.com", "helina");
        ApiClass apiClass = new ApiClass();
        Call<Signup_response> regiscall = apiClass.calls().checkUser(users.getEmail(), users.getPassword());
        try {
            Response<Signup_response> loginResponse = regiscall.execute();
            Signup_response loginresponse = loginResponse.body();

            assertTrue(loginResponse.isSuccessful() && (!loginresponse.getToken().isEmpty()));


        } catch (IOException e) {
            System.out.println(e);
        }


    }

    @Test
    public void Test_Bid(){
        ApiClass apiClass = new ApiClass();
        Call<Bidm> bidmodelCall = apiClass.calls().getBid("5e2fb169c58bb73660da7a75", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImhlbGluYUBnbWFpbC5jb20iLCJfaWQiOiI1ZTNhZmUwY2QyMGUwNTI4NDA0YjM3MjMiLCJpYXQiOjE1ODEzOTYxMjF9.aYF07NdrI4q_itLOxUJCufY7zhhyCmpiAV8YA72aYu8", "5740310");
        try{
            Response<Bidm>bidmResponse=bidmodelCall.execute();
            Bidm bidm=bidmResponse.body();
            assertTrue(bidm.getMeaasge().equals("ok"));

        }catch (IOException e){
            System.out.println(e);
        }
    }
    @Test
    public void UserUpdate(){
        Users users=new Users("sajik","Shrestha","Male");
        ApiClass usersAPI = new ApiClass();
        Call<Users> updateCall = usersAPI.calls().userUpdate("5e2fee6fdbbb222e303d8747",users);
        try{
            Response<Users>usersudpateresponse = updateCall.execute();
            assertTrue(usersudpateresponse.isSuccessful());
        }catch (IOException e){
            System.out.println(e);
        }

    }
    @Test
    public void Cart(){
        MyProductModel myProductModel=new MyProductModel("asd.jpg","Computer","1200","2020","sajik.shrestha@gmail.com");
        ApiClass apiClass = new ApiClass();
        Call<List<MyProductModel>> myproductlist = apiClass.calls().addmyproduct("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImhlbGluYUBnbWFpbC5jb20iLCJfaWQiOiI1ZTNhZmUwY2QyMGUwNTI4NDA0YjM3MjMiLCJpYXQiOjE1ODE0MDI0Nzd9.groVm3k9nkfRQeTFmuOlK-_DYqpZcBYQ6-9Cij-3d-4", myProductModel);
        try {
            Response<List<MyProductModel>> myProductModelResponse=myproductlist.execute();
            assertTrue(myProductModelResponse.isSuccessful());


        }catch (IOException e){
            System.out.println(e);
        }


    }



}

