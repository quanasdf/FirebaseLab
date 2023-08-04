package com.example.firebaselab;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.firebaselab.ass.API;
import com.example.firebaselab.ass.ImageTask;
import com.example.firebaselab.ass.NasaResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    private static final String API_KEY = "IP5pVaHaWYJXW1YFdrA03mXo4IayAmPLytGphJqi";
    private static final String BASE_URL = "https://api.nasa.gov/";

    private ImageView imageView;
    private Button fetchButton, button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        button = findViewById(R.id.outLogin);
        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(Home.this, SignIn.class));
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });


        imageView = findViewById(R.id.imageView);
        fetchButton = findViewById(R.id.fetchButton);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để lấy và hiển thị ảnh từ API
                fetchImageFromApi();
            }
        });
    }

    private void fetchImageFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API apiInterface = retrofit.create(API.class);
        Call<NasaResponse> call = apiInterface.getNasa(API_KEY, "2023-07-21");
        //call.enqueue() để gửi yêu cầu API bất đồng bộ, điều này cho phép ứng dụng tiếp tục thực hiện các công việc khác trong khi yêu cầu đang chờ đợi phản hồi từ API.
        call.enqueue(new Callback<NasaResponse>() {
            @Override
            public void onResponse(Call<NasaResponse> call, Response<NasaResponse> response) {
                if (response.isSuccessful()) {
                    NasaResponse apodResponse = response.body();
                    if (apodResponse != null) {
                        String imageUrl = apodResponse.getImageUrl();
                        // Hiển thị ảnh từ URL lên ImageView
                        Picasso.get().load(imageUrl).into(imageView);

                        // Chuyển ảnh về dạng Base64 (nếu cần)
                        ImageTask fetchImageTask = new ImageTask();
                        fetchImageTask.execute(imageUrl);
                        Log.d("Image", "convertImageToBase64" + fetchImageTask);
                        // Sử dụng base64Image theo ý muốn
                    }
                }
            }

            @Override
            public void onFailure(Call<NasaResponse> call, Throwable t) {
                // Xử lý lỗi khi không lấy được dữ liệu từ API
            }
        });
    }


}