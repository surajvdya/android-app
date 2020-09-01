package com.jewellery.jewelleryauctionhousewear;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jewellery.jewelleryauctionhousewear.StrictMode.StrictModeClass;
import com.jewellery.jewelleryauctionhousewear.bll.LoginBLl;
import com.jewellery.jewelleryauctionhousewear.model.Users;

public class MainActivity extends WearableActivity {

    private EditText etEmail,etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etEmail.getText().toString())){
                    if(!TextUtils.isEmpty(etPassword.getText().toString())){
                        String email=etEmail.getText().toString();
                        String password=etPassword.getText().toString();

                        Users users=new Users(email,password);
                        login(users);
                    }else{
                        etPassword.setError("Please Enter Password");
                    }
                }else{
                    etEmail.setError("Please Enter Email");
                }
            }
        });
    }

    public boolean login(Users u){

        LoginBLl loginBLl=new LoginBLl();
        StrictModeClass.StrictMode();
        if(loginBLl.checkUser(u.getEmail(),u.getPassword())){
            
            Intent logIn= new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(logIn);
//            Toast.makeText(this, " ", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(this, "Either Username or Password is incorrect", Toast.LENGTH_SHORT).show();
        return false;
    }
}
