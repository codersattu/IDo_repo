package com.ido.apps.ido;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                }
                else {


                    startActivity(new Intent(SplashActivity.this, Login.class));
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
            }

    }

