package com.jewelleryauction.jewelleryauctionhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jewelleryauction.jewelleryauctionhouse.ProductDetailActivity;
import com.jewelleryauction.jewelleryauctionhouse.R;
import com.jewelleryauction.jewelleryauctionhouse.StrictModeClass.StrictModeClass;
import com.jewelleryauction.jewelleryauctionhouse.model.ProductsData;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ProductListApt extends RecyclerView.Adapter<ProductListApt.ProductsViewHolder> {

    Context context;
    List<ProductsData> productsDataList;

    public static final String base_url = "http://10.0.2.2:3000/";
    String imagePath = base_url;

    public ProductListApt(Context context, List<ProductsData> productsDataList) {
        this.context = context;
        this.productsDataList = productsDataList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewproducts, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        final ProductsData productsData = productsDataList.get(position);
        holder.productName.setText(productsData.getProduct_name());
//        holder.productCategory.setText(productsData.getProduct_category());
//        holder.base_price.setText(productsData.getBase_price());
//        holder.start_date.setText(productsData.getStart_date());
        holder.end_date.setText(productsData.getEnd_date());
        holder.highest_bid.setText(productsData.getHighest_bid());
        holder.prodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imagepath = imagePath + productsData.getProduct_Image();
                Intent intent = new Intent(context, ProductDetailActivity.class);

                intent.putExtra("id", productsData.get_id());
                intent.putExtra("image", imagepath);
                intent.putExtra("imagename", productsData.getProduct_Image());
                intent.putExtra("name", productsData.getProduct_name());
                intent.putExtra("category", productsData.getProduct_category());
                intent.putExtra("base_price", productsData.getBase_price());
                intent.putExtra("start_date", productsData.getStart_date());
                intent.putExtra("end_date", productsData.getEnd_date());
                intent.putExtra("highest_bid", productsData.getHighest_bid());
                intent.putExtra("email", productsData.getEmail());
                context.startActivity(intent);

            }
        });

        StrictModeClass.StrictMode();
        String imgPath = imagePath + productsData.getProduct_Image();
        try {
            URL url = new URL(imgPath);
            holder.prodImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return productsDataList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView prodImage;
        TextView productName, productCategory, base_price, start_date, end_date, highest_bid, email;
        EditText amount;
        Button bit_button;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.prodImage);
            productName = itemView.findViewById(R.id.prodName);
//            productCategory = itemView.findViewById(R.id.prodType);
//            base_price = itemView.findViewById(R.id.prodBasePrice);
//            start_date = itemView.findViewById(R.id.prodStartDate);
            end_date = itemView.findViewById(R.id.prodEndDate);
            highest_bid = itemView.findViewById(R.id.ProdHighestBid);
            amount = itemView.findViewById(R.id.amount);
            bit_button = itemView.findViewById(R.id.bit_button);
            email = itemView.findViewById(R.id.email);

        }
    }
}
