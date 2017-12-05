package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.util.IntentUtil;

public class PublishDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(R.string.publish_detail);
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        Intent fromIntent = getIntent();
        int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
        Intent intent = null;
        if(jump_from == AppConstant.PUBLISH_BY_ME){
            intent = new Intent(this, ProfilePublishActivity.class);
        }else if(jump_from == AppConstant.HOME_PAGE){

        }
        return intent;
    }
}
