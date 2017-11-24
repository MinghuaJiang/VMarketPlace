package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by VINCENTWEN on 11/24/17.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // username-password login
        Button confirmButton = findViewById(R.id.confirm_button);
        final TextView proceedAnyway = findViewById(R.id.register_later);

        proceedAnyway.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                proceed_anyway(view);
            }
        });
    }

    private void initLogin() {

    }

    public void proceed_anyway(View view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 100);
    }
}
