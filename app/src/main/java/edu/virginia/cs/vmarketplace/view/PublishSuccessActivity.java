package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import edu.virginia.cs.vmarketplace.R;

public class PublishSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_success);
        ImageView imageView = findViewById(R.id.close_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishSuccessActivity.this, ProfilePublishActivity.class);
                startActivity(intent);
            }
        });
    }
}
