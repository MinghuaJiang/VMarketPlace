package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;

public class BioSettingActivity extends AppCompatActivity {
    private AppContext appContext;
    private EditText text;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = AppContextManager.getContextManager().getAppContext();
        setContentView(R.layout.activity_bio_setting);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        text = findViewById(R.id.description);
        text.setText(appContext.getUserDO().getBio());

        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appContext.getUserDO().setBio(text.getText().toString());
                Intent intent = new Intent(BioSettingActivity.this,
                        UserProfileSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
