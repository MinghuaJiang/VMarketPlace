package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ThumbupDO;
import edu.virginia.cs.vmarketplace.service.ThumbupService;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.view.adapter.ThumbupDOAdapter;

public class LikeHistoryActivity extends AppCompatActivity {
    private AppContext context;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private ThumbupDOAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_history);
        context = AppContextManager.getContextManager().getAppContext();
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        TextView titleView = findViewById(R.id.toolbar_title);
        List<ThumbupDO> list = context.getThumbupDO();
        if(list.size() == 1){
            titleView.setText(list.size() + " like");
        }else {
            titleView.setText(list.size() + " likes");
        }
        refreshLayout = findViewById(R.id.refresh);
        listView = findViewById(R.id.profile_detail_list);
        adapter = new ThumbupDOAdapter(this, context.getThumbupDO());
        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getSupportLoaderManager().restartLoader(0, null,
                        new CommonLoaderCallback<String, ThumbupDO>(adapter, ThumbupService.getInstance()::findThumbupByItemId,
                                context.getItemsDO().getItemId()).with(refreshLayout)).forceLoad();
            }
        });
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        return new Intent(this, PublishDetailActivity.class);
    }
}
