package edu.virginia.cs.vmarketplace.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.service.ProfileItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonAyncTask;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.ShareUtil;
import edu.virginia.cs.vmarketplace.view.adapter.ProfileItemAdapter;
import edu.virginia.cs.vmarketplace.view.login.AWSLoginActivity;

public class ProfileSettingsActivity extends AppCompatActivity {

    private ProfileItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        adapter = new ProfileItemAdapter(this, new ArrayList<>());
        ListView listView = findViewById(R.id.profile_container);
        listView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null,
                new CommonLoaderCallback<Void, ProfileItem>(adapter,
                        ProfileItemService.getInstance()::getSettingItems)).forceLoad();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    handleProfile();
                } else if (position == 1) {
                    handleShare();
                } else if (position == 2) {
                    handleAbout();
                } else {
                    handleCacheCleanup();
                }
            }
        });

        Button button = findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContextManager.getContextManager().getAppContext().signOut();
                Intent intent = new Intent(ProfileSettingsActivity.this, AWSLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void handleProfile() {
        Intent intent = new Intent(this, UserProfileSettingActivity.class);
        startActivity(intent);
    }

    private void handleShare() {
        String message = "Check out latest second-hand marketplace app for UVA students at:" +
                "\n" + "http://www.cs.virginia.edu/~mj2eu/apps/vmarketplace/vmarketplace.apk";
        Uri file = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.app);
        Intent intent = ShareUtil.shareToSocialNetwork(getPackageManager(),message, file);
        startActivity(intent);
    }

    private void handleAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void handleCacheCleanup() {
        new CommonAyncTask<Void, Void, Void>(() -> {
            File[] files = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".png");
                }
            });
            for (File file : files) {
                file.delete();
            }

            return null;
        }).with((x) -> {
            Toast.makeText(this, "Cache cleaned up successfully!", Toast.LENGTH_SHORT).show();
        }).run();
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(AppConstant.SWITCH_TAB, AppConstant.TAB_PROFILE); // Both constants are defined in your code
        intent.putExtras(bundle);

        return intent;
    }
}
