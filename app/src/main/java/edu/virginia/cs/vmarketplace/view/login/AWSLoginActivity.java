package edu.virginia.cs.vmarketplace.view.login;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobile.auth.facebook.FacebookButton;
import com.amazonaws.mobile.auth.google.GoogleButton;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;
import edu.virginia.cs.vmarketplace.view.MainActivity;

public class AWSLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(final AWSStartupResult awsStartupResult) {
                AuthUIConfiguration config =
                        new AuthUIConfiguration.Builder()
                                .userPools(true)  // true? show the Email and Password UI
                                .signInButton(FacebookButton.class) // Show Facebook button
                                // Show Google button
                                .signInButton(GoogleButton.class)
                                .logoResId(R.mipmap.logo_48dp) // Change the logo
                                .backgroundColor(ContextCompat.getColor(AWSLoginActivity.this,R.color.tan_background)) // Change the backgroundColor
                                .isBackgroundColorFullScreen(true) // Full screen backgroundColor the backgroundColor full screenff
                                .fontFamily("sans-serif-light") // Apply sans-serif-light as the global font
                                .canCancel(false)
                                .build();
                MySignInUI signinUI = (MySignInUI) AWSClientFactory.getInstance().getSignInUI(AWSLoginActivity.this);
                signinUI.login(AWSLoginActivity.this, MainActivity.class).authUIConfiguration(config).execute();
            }
        }).execute();
    }

    private void initTabs(){
        TabLayout layout = findViewById(R.id.sliding_tabs);
        TabLayout.Tab tab = layout.newTab();
        tab.setText("home");
        tab.setIcon(R.drawable.home_24p);
        layout.addTab(tab);

        tab = layout.newTab();
        tab.setText("place");
        tab.setIcon(R.drawable.place_24p);
        layout.addTab(tab);

        tab = layout.newTab();
        tab.setText("publish");
        tab.setIcon(R.drawable.add_24p);
        layout.addTab(tab);

        tab = layout.newTab();
        tab.setText("message");
        tab.setIcon(R.drawable.ic_message_24dp);
        layout.addTab(tab);

        tab = layout.newTab();
        tab.setText("profile");
        tab.setIcon(R.drawable.user_24p);
        layout.addTab(tab);
    }
}
