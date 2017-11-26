package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.util.IntentUtil;

public class MessageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        return IntentUtil.jumpWithTabRecorded(AppConstant.TAB_MESSAGE, this, MainActivity.class);
    }
}
