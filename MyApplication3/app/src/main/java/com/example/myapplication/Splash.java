package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    // Время отображения SplashScreen в миллисекундах
    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        // Установка макета для SplashActivity
        setContentView(R.layout.activity_splash);

        // Запуск основной активности после задержки
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Создание интента для запуска основной активности
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);

                // Закрытие SplashActivity после перехода на основную активность
                finish();
            }
        }, SPLASH_DURATION);
    }
}