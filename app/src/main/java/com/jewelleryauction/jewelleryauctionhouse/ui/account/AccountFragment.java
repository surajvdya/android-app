package com.jewelleryauction.jewelleryauctionhouse.ui.account;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jewelleryauction.jewelleryauctionhouse.R;
import com.jewelleryauction.jewelleryauctionhouse.UserUpdateActivity;
import com.jewelleryauction.jewelleryauctionhouse.api.ApiClass;
import com.jewelleryauction.jewelleryauctionhouse.bll.LoginBLl;
import com.jewelleryauction.jewelleryauctionhouse.model.Users;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

ImageView profileimage;
TextView tvusername;
Button btnupdate;
String first_name,last_name,id,pass,image;
Users dataset;
public static final String base_url="http://10.0.2.2:3000/";
String imagePath=base_url ;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);

        profileimage=root.findViewById(R.id.imageviewaccount);
        tvusername=root.findViewById(R.id.txtusername);
        btnupdate=root.findViewById(R.id.btnupdateaccount);

        String token="Bearer " + LoginBLl.Token;
        LoadUser(token);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), UserUpdateActivity.class);
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name",last_name);
                intent.putExtra("id", id);
                intent.putExtra("image",image);

                    startActivity(intent);
            }
        });
        return root;
    }

    public void LoadUser(String token){
        ApiClass apiClass=new ApiClass();
        Call<Users>usersCall=apiClass.calls().getUser(token);
        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Code"+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Users users=response.body();
                tvusername.setText(users.getFirst_name());
                String imgPath=imagePath + users.getUser_image();

                first_name=users.getFirst_name();
                last_name=users.getLast_name();

                image=users.getUser_image();

                id=users.get_id();

                try {
                    URL url=new URL(imgPath);
                    profileimage.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getContext(), "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}