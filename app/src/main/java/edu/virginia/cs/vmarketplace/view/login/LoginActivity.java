package edu.virginia.cs.vmarketplace.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.view.MainActivity;

/**
 * Created by VINCENTWEN on 11/24/17.
 */

public class LoginActivity extends AppCompatActivity {
    // username + password login Screen fields
    private EditText inUsername;
    private EditText inPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        LoginHelper.init(getApplicationContext());

        // username-password login
        initApp();
        Button confirmButton = findViewById(R.id.confirm_button);
        final TextView proceedAnyway = findViewById(R.id.register_later);

        proceedAnyway.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                proceed_anyway(view);
            }
        });
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

    // initialize app
    private void initApp() {
        inUsername = findViewById(R.id.input_username);
//        inUsername.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if(s.length() == 0) {
//                    TextView label = findViewById(R.id.textViewUserIdLabel);
//                    label.setText(R.string.Username);
//                    inUsername.setBackground(getDrawable(R.drawable.text_border_selector));
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                TextView label = (TextView) findViewById(R.id.textViewUserIdMessage);
//                label.setText("");
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.length() == 0) {
//                    TextView label = (TextView) findViewById(R.id.textViewUserIdLabel);
//                    label.setText("");
//                }
//            }
//        });

        inPassword = (EditText) findViewById(R.id.input_password);
//        inPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if(s.length() == 0) {
//                    TextView label = (TextView) findViewById(R.id.textViewUserPasswordLabel);
//                    label.setText(R.string.Password);
//                    inPassword.setBackground(getDrawable(R.drawable.text_border_selector));
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                TextView label = (TextView) findViewById(R.id.textViewUserPasswordMessage);
//                label.setText("");
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.length() == 0) {
//                    TextView label = (TextView) findViewById(R.id.textViewUserPasswordLabel);
//                    label.setText("");
//                }
//            }
//        });
    }

}
