package com.jewelleryauction.jewelleryauctionhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.jewelleryauction.jewelleryauctionhouse.channel.BroadcastReceiverExample;
import com.jewelleryauction.jewelleryauctionhouse.R;

public class SplashScreenActivity extends AppCompatActivity {
BroadcastReceiverExample broadcastReceiverExample=new BroadcastReceiverExample();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
                String username=sharedPreferences.getString("username","");
                String password=sharedPreferences.getString("password","");
                if (username.equals("admin")&& password.equals("admin")){
                    Intent intent=new Intent(SplashScreenActivity.this,AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Intent intent1=new Intent(SplashScreenActivity.this,LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }

            }
        },2000);
    }
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiverExample,intentFilter);
    }
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiverExample);
    }

}
