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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jewelleryauction.jewelleryauctionhouse.StrictModeClass.StrictModeClass;
import com.jewelleryauction.jewelleryauctionhouse.api.ApiClass;
import com.jewelleryauction.jewelleryauctionhouse.model.ImageModel;
import com.jewelleryauction.jewelleryauctionhouse.model.Signup_response;
import com.jewelleryauction.jewelleryauctionhouse.model.Users;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    String imagepath = "";
    String  imageView="";
    ImageView userimage;
    private EditText first_name, last_name, email, password, conpassword;
    private RadioGroup rdogender;
    private RadioButton radiosex;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getSupportActionBar().setTitle("Register Form");
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signpassword);
        userimage=findViewById(R.id.imageview);
        conpassword = findViewById(R.id.signrepassword);
        rdogender = findViewById(R.id.rdogender);
        btnSignup = findViewById(R.id.btnSignup);
            userimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkPermission();
                    TakePicture();
                }
            });
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveImageOnly();
                    SignupMode();
                }
            });
    }


    private void TakePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please Select the Image", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            Uri uri = data.getData();
            userimage.setImageURI(uri);
            imagepath = getRealPathFromUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
    public void SignupMode() {
        if (!TextUtils.isEmpty(first_name.getText().toString())) {
            if (!TextUtils.isEmpty(last_name.getText().toString())) {
                if (!TextUtils.isEmpty(email.getText().toString())) {
                    if (!TextUtils.isEmpty(password.getText().toString())) {
                        if (!TextUtils.isEmpty(conpassword.getText().toString())) {
                            if (!imagepath.isEmpty()) {
                                int select = rdogender.getCheckedRadioButtonId();
                                radiosex = findViewById(select);
                                String firstname = first_name.getText().toString();
                                String lastname = last_name.getText().toString();
                                String emaill = email.getText().toString();
                                String passw = password.getText().toString();
                                String conpassw = conpassword.getText().toString();

                                if (passw.equals(conpassw)) {
                                    Users users = new Users(firstname, lastname, radiosex.getText().toString(),emaill,imageView, passw );
                                        register(users);
                                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                }

                            } else {
                                Toast.makeText(this, "Please Select the image for profile", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            conpassword.setError("Please Type the password again");
                        }
                    } else {
                        password.setError("Please Type Password");
                    }
                } else {
                    email.setError("Please Enter the Email");
                }

            } else {
                last_name.setError("Please Enter Last Name");
            }
        } else {
            first_name.setError("Please Enter First Name");
        }
    }

    void register(Users users) {
        ApiClass apiClass = new ApiClass();
        Call<Signup_response> regiscall = apiClass.calls().register(users);
        regiscall.enqueue(new Callback<Signup_response>() {
            @Override
            public void onResponse(Call<Signup_response> call, Response<Signup_response> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "error" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("error", "error" + response.code());
                    return;
                }
                Signup_response signup_respond = response.body();
                if (signup_respond.getStatus().equals("Success")) {
                    Toast.makeText(RegisterActivity.this, "Sucessfully Registered", Toast.LENGTH_LONG).show();
                    Toast.makeText(RegisterActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Mail already exists", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Signup_response> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", "error  " + t.getLocalizedMessage());
            }
        });
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
        File file = new File( imagepath );
        RequestBody requestBody = RequestBody.create( MediaType.parse( "multipart/form-data" ), file );
        MultipartBody.Part body = MultipartBody.Part.createFormData( "image",
                file.getName(), requestBody );

        ApiClass usersAPI = new ApiClass();

        Call<ImageModel> responseBodyCall = usersAPI.calls().uploadImage( body );

        StrictModeClass.StrictMode();
        //Synchronous methid
        try {
            Response<ImageModel> imageResponseResponse = responseBodyCall.execute();
            imageView = imageResponseResponse.body().getFilename();
            //Toast.makeText( this, ""+imageName, Toast.LENGTH_SHORT ).show();

        } catch (IOException e) {
            Toast.makeText( this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        }
    }
}
