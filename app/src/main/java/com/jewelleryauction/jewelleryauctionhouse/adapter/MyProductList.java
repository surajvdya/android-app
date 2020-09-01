package com.jewelleryauction.jewelleryauctionhouse.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jewelleryauction.jewelleryauctionhouse.R;
import com.jewelleryauction.jewelleryauctionhouse.StrictModeClass.StrictModeClass;
import com.jewelleryauction.jewelleryauctionhouse.model.MyProductModel;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MyProductList extends RecyclerView.Adapter<MyProductList.MyProductsViewHolder> {
    Context context;
    List<MyProductModel> productsDataList;

    public static final String base_url = "http://10.0.2.2:3000/";
    String imagePath = base_url;

    public MyProductList(Context context, List<MyProductModel> productsDataList) {
        this.context = context;
        this.productsDataList = productsDataList;
    }

    @NonNull
    @Override
    public MyProductList.MyProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewmyproduct, parent, false);
        return new MyProductList.MyProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductList.MyProductsViewHolder holder, int position) {
final MyProductModel myProductModel=productsDataList.get(position);
        holder.myproductName.setText(myProductModel.getProduct_name());
    holder.mybase_price.setText(myProductModel.getBase_price());
        holder.myend_date.setText(myProductModel.getEnd_date());

        StrictModeClass.StrictMode();
        String imgPath = imagePath + myProductModel.getProduct_Image();
        try {
            URL url = new URL(imgPath);
            holder.myprodImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return productsDataList.size();
    }

    public class MyProductsViewHolder extends RecyclerView.ViewHolder {
        ImageView myprodImage;
        TextView myproductName, mybase_price, myend_date, myemail;

        public MyProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            myprodImage=itemView.findViewById(R.id.myproductimage);
            myproductName=itemView.findViewById(R.id.myprodname);
            mybase_price=itemView.findViewById(R.id.myproductbaseprice);
            myend_date=itemView.findViewById(R.id.myproductenddate);
        }
    }
}