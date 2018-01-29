package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.service.ProfileItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.view.adapter.ProfileItemAdapter;

public class AboutActivity extends AppCompatActivity {
    private ListView listView;
    private ProfileItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        listView = findViewById(R.id.list);

        adapter = new ProfileItemAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null,
                new CommonLoaderCallback<Void, ProfileItem>(adapter,
                        ProfileItemService.getInstance()::getAboutItems)).forceLoad();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    handleEmail();
                }
            }
        });
    }

    private void handleEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String toList[] = {"ben.minghuajiang@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, toList);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[vMarketPlace Android] Email Me");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"\n\n\n\n\n\n\n\n\n\n\n" +
                "Client Version: 1.0.0" + "\n" +  
                "Device: " + Build.MANUFACTURER + " " + Build.BRAND + " (" + Build.MODEL+ ")" +"\n" +
                "OS Version: " + Build.VERSION.RELEASE + "\n" +
                "User Name: " +
                AppContextManager.getContextManager().getAppContext().getUser().getUserName());
        startActivity(Intent.createChooser(emailIntent,"Email Me"));
    }
}
