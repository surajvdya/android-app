package com.jewelleryauction.jewelleryauctionhouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jewelleryauction.jewelleryauctionhouse.StrictModeClass.StrictModeClass;
import com.jewelleryauction.jewelleryauctionhouse.api.ApiClass;
import com.jewelleryauction.jewelleryauctionhouse.bll.LoginBLl;
import com.jewelleryauction.jewelleryauctionhouse.model.ImageModel;
import com.jewelleryauction.jewelleryauctionhouse.model.Users;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateActivity extends AppCompatActivity {
    ImageView imageupdate;
    EditText firstnameup, lastnameup;
    Button btnUpdate;
    String oldName, id;
    private String imageName = "";
    public static String token = "";
    public static final String base_url = "http://10.0.2.2:3000/";
    String imagePaths = base_url;
    String imagePath="";
    String sendp;
    String rImage;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        imageupdate = findViewById(R.id.imageupdate);
        firstnameup = findViewById(R.id.etuptfirst);
        lastnameup = findViewById(R.id.etuptsecond);
        btnUpdate = findViewById(R.id.btnUpdate);

        token = "Bearer " + LoginBLl.Token;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String imgPath = imagePaths + bundle.getString("image");
            oldName = bundle.getString("image");
            id = bundle.getString("id");
            firstnameup.setText(bundle.getString("first_name"));
            lastnameup.setText(bundle.getString("last_name"));

            try {
                URL url = new URL(imgPath);
                imageupdate.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imageupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowseImage();
                count++;
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 1) {
                    saveImageOnly();
                    sendp = imageName;
                } else {
                    sendp = oldName;
                }
                Users users = new Users(firstnameup.getText().toString(), lastnameup.getText().toString(), sendp);
                UserUpdate(users, id);
                Intent intent=new Intent(UserUpdateActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
            }
        });


    }

    void BrowseImage() {
        checkPermission();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(UserUpdateActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserUpdateActivity.this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please Select Image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imageupdate.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",
                file.getName(), requestBody);

        ApiClass usersAPI = new ApiClass();

        Call<ImageModel> responseBodyCall = usersAPI.calls().uploadImage(body);
        StrictModeClass.StrictMode();

        try {
            Response<ImageModel> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, ""+imageName, Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            Toast.makeText(this, "Error!!" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void UserUpdate(Users user, String id) {
        ApiClass usersAPI = new ApiClass();
        final Call<Users> updateCall = usersAPI.calls().userUpdate(id, user);
        updateCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UserUpdateActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(UserUpdateActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }



}
