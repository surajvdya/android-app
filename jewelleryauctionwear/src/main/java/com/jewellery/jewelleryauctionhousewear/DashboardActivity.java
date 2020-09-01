package com.jewellery.jewelleryauctionhousewear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jewellery.jewelleryauctionhousewear.api.ApiClass;
import com.jewellery.jewelleryauctionhousewear.bll.LoginBLl;
import com.jewellery.jewelleryauctionhousewear.model.ProductsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String token = new LoginBLl().Token;
        mTextView = findViewById(R.id.text);
        loadCurrentUser(token);

        // Enables Always-on
        setAmbientEnabled();
        loadCurrentUser(token);

    }
    private void loadCurrentUser(String token) {
        ApiClass usersAPI = new ApiClass();
        Call<List<ProductsData>> userCall=usersAPI.calls().getproducts(token);
        userCall.enqueue(new Callback<List<ProductsData>>() {
            @Override
            public void onResponse(Call<List<ProductsData>> call, Response<List<ProductsData>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DashboardActivity.this, "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<ProductsData>productsData=response.body();

               mTextView.setText(String.valueOf(productsData.size()));

            }

            @Override
            public void onFailure(Call<List<ProductsData>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
