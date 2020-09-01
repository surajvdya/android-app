package com.jewellery.jewelleryauctionhousewear.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jewellery.jewelleryauctionhousewear.R;
import com.jewellery.jewelleryauctionhousewear.model.ProductsData;


import java.util.List;

public class ProductListApt extends RecyclerView.Adapter<ProductListApt.ProductsViewHolder> {

    Context context;
    List<ProductsData> productsDataList;

    public static final String base_url = "http://10.0.2.2:3000/";

    public ProductListApt(Context context, List<ProductsData> productsDataList) {
        this.context = context;
        this.productsDataList = productsDataList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dashboard, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productsDataList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
