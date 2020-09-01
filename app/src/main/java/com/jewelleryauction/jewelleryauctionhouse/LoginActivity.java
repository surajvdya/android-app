package com.jewelleryauction.jewelleryauctionhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jewelleryauction.jewelleryauctionhouse.StrictModeClass.StrictModeClass;
import com.jewelleryauction.jewelleryauctionhouse.bll.LoginBLl;
import com.jewelleryauction.jewelleryauctionhouse.channel.CreateChannel;
import com.jewelleryauction.jewelleryauctionhouse.model.Users;

public class LoginActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText etLogEmail, etLogPassword;
    private Button btnLogin, btnLogRegister;
    public String Token="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  getSupportActionBar().setTitle("Login Form");
        etLogEmail = findViewById(R.id.etLogEmail);
        etLogPassword = findViewById(R.id.etLogPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogRegister = findViewById(R.id.btnLogRegister);
        notificationManagerCompat=NotificationManagerCompat.from(this);
        CreateChannel channel=new CreateChannel(this);
        channel.createChannel();



        btnLogRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etLogEmail.getText().toString())){
                    if(!TextUtils.isEmpty(etLogPassword.getText().toString())){
                        String email=etLogEmail.getText().toString();
                        String password=etLogPassword.getText().toString();

                        Users users=new Users(email,password);
                        login(users);
                    }else{
                        etLogPassword.setError("Please Enter Password");
                    }
                }else{
                    etLogEmail.setError("Please Enter Email");
                }
            }
        });
    }
    void Store(Users u){
        SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",u.getEmail());
        editor.putString("password",u.getPassword());
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        editor.commit();
    }
    public boolean login(Users u){

        LoginBLl loginBLl=new LoginBLl();
        StrictModeClass.StrictMode();
        if(loginBLl.checkUser(u.getEmail(),u.getPassword())){
            Store(u);
            Token=loginBLl.Token;
            Intent logIn= new Intent(LoginActivity.this,AdminDashboardActivity.class);
            logIn.putExtra("token",Token);
            startActivity(logIn);
            DisplayNotification();
          //  Toast.makeText(this, "Welcome "+ Token, Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(this, "Either Username or Passoword is incorrect", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void DisplayNotification(){
        Notification notification=new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("Login Success ")
                .setContentText("You have Successfully Logged in")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1,notification);
    }
}
