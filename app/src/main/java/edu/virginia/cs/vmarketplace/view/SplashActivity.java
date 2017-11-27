package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import edu.virginia.cs.vmarketplace.view.login.AWSLoginActivity;

/**
 * Created by cutehuazai on 11/22/17.
 */

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AWSLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
