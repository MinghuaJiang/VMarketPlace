package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.virginia.cs.vmarketplace.R;

public class PublishActivity extends AppCompatActivity {
    private static String secondHandIdentifier = "SECOND_HAND";
    private static String subleaseIdentifier = "SUBLEASE";
    private static String ridesIdentifier = "RIDES";
    private static String activitiesIdentifier = "ACTIVITIES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        // second hand logic begins here
        ImageView secondHand = findViewById(R.id.second_hand_image);
        secondHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, UseCameraActivity.class);
                String tag = secondHandIdentifier;
                useCameraIntent.putExtra(Intent.EXTRA_TEXT, tag);
                startActivity(useCameraIntent);
            }
        });

        // sublease logic begins here
        ImageView sublease = findViewById(R.id.sublease_image);
        sublease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, UseCameraActivity.class);
                String tag = subleaseIdentifier;
                useCameraIntent.putExtra(Intent.EXTRA_TEXT, tag);
                startActivity(useCameraIntent);
            }
        });

        // rides logic begins here
        ImageView rides = findViewById(R.id.rides_image);
        rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, UseCameraActivity.class);
                String tag = ridesIdentifier;
                useCameraIntent.putExtra(Intent.EXTRA_TEXT, tag);
                startActivity(useCameraIntent);
            }
        });

        // activities logic begins here
        ImageView activities = findViewById(R.id.activity_image);
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, UseCameraActivity.class);
                String tag = activitiesIdentifier;
                useCameraIntent.putExtra(Intent.EXTRA_TEXT, tag);
                startActivity(useCameraIntent);
            }
        });

        // close button logic begins here
        ImageView closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainPageIntent = new Intent(PublishActivity.this, MainActivity.class);
                startActivity(backToMainPageIntent);
            }
        });
    }
}
