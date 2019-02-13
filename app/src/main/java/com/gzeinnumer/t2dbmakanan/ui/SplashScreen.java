package com.gzeinnumer.t2dbmakanan.ui;

import android.os.Bundle;
import android.os.Handler;

import com.gzeinnumer.t2dbmakanan.R;
import com.gzeinnumer.t2dbmakanan.helper.SessionManager;


public class SplashScreen extends SessionManager {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setUpDelay();
    }

    private void setUpDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sessionManager.checkLogin();
                finish();
            }
        }, 1000);
    }
}