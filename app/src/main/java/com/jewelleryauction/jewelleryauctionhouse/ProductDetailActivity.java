package com.jewelleryauction.jewelleryauctionhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jewelleryauction.jewelleryauctionhouse.StrictModeClass.StrictModeClass;
import com.jewelleryauction.jewelleryauctionhouse.api.ApiClass;
import com.jewelleryauction.jewelleryauctionhouse.bll.LoginBLl;
import com.jewelleryauction.jewelleryauctionhouse.channel.CreateChannel;
import com.jewelleryauction.jewelleryauctionhouse.model.Bidm;
import com.jewelleryauction.jewelleryauctionhouse.model.MyProductModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private SensorManager mSensorManager;
    private Sensor mProximity;
    SensorEventListener sel;
    private static final int SENSOR_SENSITIVITY = 4;
    ImageView product_Image;
    TextView product_name, product_category, base_price, start_date, end_date, highest_bid, email;
    Button bit_button;
    EditText amount;
    String Iname;
    AlertDialog.Builder builder;
    String id;
    String img_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product_Image = findViewById(R.id.product_Image);
        product_name = findViewById(R.id.product_name);
        product_category = findViewById(R.id.product_category);
        base_price = findViewById(R.id.base_price);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        highest_bid = findViewById(R.id.highest_bid);
        bit_button = findViewById(R.id.bit_button);
        email = findViewById(R.id.email);


        amount = findViewById(R.id.amount);

        mSensorManager=(SensorManager)getSystemService(this.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sel=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (sensorEvent.values[0] >= -SENSOR_SENSITIVITY && sensorEvent.values[0] <= SENSOR_SENSITIVITY) {
                        bit_button.setEnabled(false);
                    } else {
                        bit_button.setEnabled(true);
                    }
                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        if (mProximity != null) {
            mSensorManager.registerListener(sel, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "NO SENSOR DETECTED", Toast.LENGTH_SHORT).show();
        }



        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String imagepath = bundle.getString("image");
            Iname = bundle.getString("image");
            img_name=bundle.getString("imagename");
            try {
                URL url = new URL(imagepath);
                product_Image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            id = bundle.getString("id");
//            Toast.makeText(this, "id" + id, Toast.LENGTH_SHORT).show();
            product_name.setText(bundle.getString("name"));
            product_category.setText(bundle.getString("category"));
            base_price.setText(bundle.getString("base_price"));
            start_date.setText(bundle.getString("start_date"));
            end_date.setText(bundle.getString("end_date"));
            highest_bid.setText(bundle.getString("highest_bid"));
            email.setText(bundle.getString("email"));
            notificationManagerCompat = NotificationManagerCompat.from(this);
            CreateChannel channel = new CreateChannel(this);
            builder = new AlertDialog.Builder(this);
            channel.createChannel();


        }

        bit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             try{
                 if(!TextUtils.isEmpty(amount.getText().toString())){
                    int Hnum = 0;
                    Hnum = Integer.parseInt(highest_bid.getText().toString());
                    int getnum = Integer.parseInt(amount.getText().toString());
                    if (Hnum < getnum) {
                        String token = "Bearer " + new LoginBLl().Token;
                        setNew(token, amount.getText().toString());
                            PlaceMyProduct();


                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Bid must be more", Toast.LENGTH_SHORT).show();
                    }

                    }else {
                    amount.setError("Please Enter the amount to bid");
                }}
             catch (Exception e){
                 e.printStackTrace();
             }

            }
        });

    }
public void PlaceMyProduct(){
    builder.setMessage("DO you want to add?").setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String prodname = product_name.getText().toString();
                    String baseprice = base_price.getText().toString();
                    String enddate = end_date.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                    String usermail = sharedPreferences.getString("username","");

                    MyProductModel myProductModel = new MyProductModel(img_name, prodname, baseprice, enddate, usermail);
                    String token = "Bearer " + LoginBLl.Token;
//                    Toast.makeText(ProductDetailActivity.this, "" + token, Toast.LENGTH_SHORT).show();
                    AddMyProduct(token, myProductModel);
                    Intent intent = new Intent(ProductDetailActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    DisplayNotification();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
            Toast.makeText(ProductDetailActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    });
    AlertDialog alert = builder.create();
    alert.setTitle("Do you want to Save this Product?");
    alert.show();
}
    public void AddMyProduct(String token, MyProductModel myProductModel) {
        ApiClass apiClass = new ApiClass();
        Call<List<MyProductModel>> myproductlist = apiClass.calls().addmyproduct(token, myProductModel);
        myproductlist.enqueue(new Callback<List<MyProductModel>>() {
            @Override
            public void onResponse(Call<List<MyProductModel>> call, Response<List<MyProductModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ProductDetailActivity.this, "add product", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<MyProductModel>> call, Throwable t) {

            }
        });
    }


    public void setNew(String token, String bid) {
        ApiClass apiClass = new ApiClass();
        Call<Bidm> bidmodelCall = apiClass.calls().getBid(id, token, bid);
        StrictModeClass.StrictMode();
        bidmodelCall.enqueue(new Callback<Bidm>() {
            @Override
            public void onResponse(Call<Bidm> call, Response<Bidm> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "error" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("error", "error" + response.code());
                    return;
                }

                Toast.makeText(ProductDetailActivity.this, "Bid Posted. Thank You", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Bidm> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", "error  " + t.getLocalizedMessage());
            }
        });
    }

    private void DisplayNotification() {
        Notification notification = new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("Product Added ")
                .setContentText("Bid has been Successfully placed and product has been added.")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1, notification);
    }


}
