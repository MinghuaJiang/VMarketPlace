package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.AppContext;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.util.IntentUtil;

public class PublishActivity extends AppCompatActivity {
    private static String secondHandIdentifier = "Second Hand";
    private static String subleaseIdentifier = "Sublease";
    private static String ridesIdentifier = "Ride";
    private static String activitiesIdentifier = "Activity";
    private static final int BACK_FROM_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        final AppContext context = AppContextManager.getContextManager().getAppContext();
        ConstraintLayout layout = findViewById(R.id.container);
        // second hand logic begins here
        ImageView secondHand = findViewById(R.id.second_hand_image);
        secondHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, PhotoActivity.class);
                context.setCurrentCategory(secondHandIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // sublease logic begins here
        ImageView sublease = findViewById(R.id.sublease_image);
        sublease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, PhotoActivity.class);
                context.setCurrentCategory(subleaseIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // rides logic begins here
        ImageView rides = findViewById(R.id.rides_image);
        rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, PublishFormActivity.class);
                context.setCurrentCategory(ridesIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // activities logic begins here
        ImageView activities = findViewById(R.id.activity_image);
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(PublishActivity.this, PhotoActivity.class);
                context.setCurrentCategory(activitiesIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // close button logic begins here
        ImageView closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PublishActivity.this.getIntent();
                startActivity(IntentUtil.jumpWithTabRecorded(intent.getIntExtra(AppConstant.PREVIOUS_TAB, 0), PublishActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == BACK_FROM_CAMERA && resultCode == RESULT_CANCELED) {
            Intent backIntent = new Intent(this, MainActivity.class);
            startActivity(backIntent);
        }
    }
}
