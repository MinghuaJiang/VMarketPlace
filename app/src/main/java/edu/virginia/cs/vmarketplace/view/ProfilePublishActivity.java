package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.virginia.cs.vmarketplace.R;

import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.IntentUtil;
import edu.virginia.cs.vmarketplace.view.adapter.ProfilePublishItemAdapter;


/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfilePublishActivity extends AppCompatActivity{
    private SwipeRefreshLayout refreshLayout;
    private ProfilePublishItemAdapter adapter;
    private CommonLoaderCallback<String, ProductItemsDO> callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(R.string.published_by_me);

        final ListView listView = findViewById(R.id.profile_detail_list);
        adapter = new ProfilePublishItemAdapter(this, new ArrayList<ProductItemsDO>());
        listView.setAdapter(adapter);

        refreshLayout = findViewById(R.id.refresh);

        callback = new CommonLoaderCallback<String, ProductItemsDO>(adapter, ProductItemService.getInstance()::findItemByUserId,
                AppContextManager.getContextManager().getAppContext().getUser().getUserId()).with(refreshLayout);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSupportLoaderManager().restartLoader(0, null, callback).forceLoad();
            }
        });

        refreshLayout.setRefreshing(true);
        getSupportLoaderManager().initLoader(0, null, callback).forceLoad();
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        return IntentUtil.jumpWithTabRecorded(AppConstant.TAB_PROFILE, this, MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        Intent fromIntent = getIntent();
        if(fromIntent.hasExtra(AppConstant.JUMP_FROM)){
            int jumpFrom = fromIntent.getIntExtra(AppConstant.JUMP_FROM, -1);
            if(jumpFrom == AppConstant.PUBLISH_SUCCESS){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }
}
