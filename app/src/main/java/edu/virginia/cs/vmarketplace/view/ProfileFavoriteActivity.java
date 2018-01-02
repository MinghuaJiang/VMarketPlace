package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.PageRequest;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonRecycleViewLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.view.adapter.HomePageListAdapter;
import edu.virginia.cs.vmarketplace.view.adapter.ProfileFavoriteItemAdapter;

public class ProfileFavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProfileFavoriteItemAdapter adapter;
    private AppContext context;

    private static final int PAGE_SIZE = 3;
    private Object loadMoreToken;
    private EndlessRecyclerViewScrollListener listener;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail_recycler);

        context = AppContextManager.getContextManager().getAppContext();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(R.string.added_to_favorite);

        refresh = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.favorite_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProfileFavoriteItemAdapter(this,
                new ArrayList<>());
        recyclerView.setAdapter(adapter);
        layoutManager.setAutoMeasureEnabled(true);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                getSupportLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<PageRequest, ProductItemsDO>(
                        ProfileFavoriteActivity.this,
                        adapter,
                        (x)-> {
                            return ProductItemService.getInstance().getFavoritesByItemIds(
                                    new ArrayList<>(context.getUserDO().getFavoriteItems()), x);
                        }, new PageRequest(PAGE_SIZE, 0)
                ).with((x)->{
                    listener.setHasMorePage(x.getToken() != null);
                    loadMoreToken = x.getToken();
                    adapter.setData(x.getResult(), 0, adapter.getItemCount() - 1);
                    refresh.setRefreshing(false);
                })).forceLoad();
            }
        });

        listener = new EndlessRecyclerViewScrollListener(layoutManager, 2) {
            @Override
            public void onNoMoreResult(RecyclerView view) {
                adapter.getFootViewHolder().progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileFavoriteActivity.this, "No More Item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                PageRequest request = new PageRequest(PAGE_SIZE, page);
                request.setToken(loadMoreToken);
                adapter.getFootViewHolder().progressBar.setVisibility(View.VISIBLE);
                getSupportLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<PageRequest, ProductItemsDO>(
                        ProfileFavoriteActivity.this,
                        adapter,
                        (x)-> {
                            return ProductItemService.getInstance().getFavoritesByItemIds(
                                    new ArrayList<>(context.getUserDO().getFavoriteItems()), x);
                        }, request
                ).with((x) ->{
                    adapter.getFootViewHolder().progressBar.setVisibility(View.INVISIBLE);
                    listener.setHasMorePage(x.getToken() != null);
                    loadMoreToken = x.getToken();
                    adapter.insertData(x.getResult(), totalItemsCount);
                    Toast.makeText(ProfileFavoriteActivity.this, "Loaded more items successfully", Toast.LENGTH_SHORT).show();
                })).forceLoad();
            }
        };

        recyclerView.addOnScrollListener(listener);

        getSupportLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<PageRequest, ProductItemsDO>(
                this,
                adapter,
                (x)-> {
                    return ProductItemService.getInstance().getFavoritesByItemIds(
                            new ArrayList<>(context.getUserDO().getFavoriteItems()), x);
                }, new PageRequest(PAGE_SIZE, 0)
        ).with((x)->{
            listener.setHasMorePage(x.getToken() != null);
            loadMoreToken = x.getToken();
            adapter.initData(x.getResult());
        })).forceLoad();
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
