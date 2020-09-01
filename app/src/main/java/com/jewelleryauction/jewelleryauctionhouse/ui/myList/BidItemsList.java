package com.jewelleryauction.jewelleryauctionhouse.ui.myList;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jewelleryauction.jewelleryauctionhouse.R;
import com.jewelleryauction.jewelleryauctionhouse.adapter.MyProductList;
import com.jewelleryauction.jewelleryauctionhouse.api.ApiClass;
import com.jewelleryauction.jewelleryauctionhouse.bll.LoginBLl;
import com.jewelleryauction.jewelleryauctionhouse.model.MyProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidItemsList extends Fragment {

    RecyclerView myrecycler;
    View view;
//    public BidItemsList() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bid_items_list, container, false);

        myrecycler = view.findViewById(R.id.MyRecycle);
        String token = "bearer " + new LoginBLl().Token;

        LoadMyProducts(token);
        return view;
    }

    public void LoadMyProducts(String token) {
        ApiClass apiClass = new ApiClass();
        final Call<List<MyProductModel>> myproductlist = apiClass.calls().myproducts(token);
        myproductlist.enqueue(new Callback<List<MyProductModel>>() {
            @Override
            public void onResponse(Call<List<MyProductModel>> call, Response<List<MyProductModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<MyProductModel> myProductModels = response.body();
                Log.d("total", "onResponse: " + myProductModels.size());
                MyProductList myproductslist = new MyProductList(getContext(), myProductModels);
                myrecycler.setAdapter(myproductslist);
                myrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<List<MyProductModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
