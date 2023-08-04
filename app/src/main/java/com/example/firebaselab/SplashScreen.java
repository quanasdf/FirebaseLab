package com.example.firebaselab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 3000; // 3 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Tạo một Handler để chuyển đến màn hình chính sau thời gian hiển thị Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để mở màn hình chính
                Intent intent = new Intent(SplashScreen.this, Home.class);
                startActivity(intent);
                finish(); // Đóng SplashActivity sau khi chuyển đến màn hình chính
            }
        }, SPLASH_DISPLAY_TIME);
    }
}