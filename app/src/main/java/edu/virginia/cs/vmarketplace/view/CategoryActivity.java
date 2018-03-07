package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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
import edu.virginia.cs.vmarketplace.util.CategoryUtil;
import edu.virginia.cs.vmarketplace.util.ReflectionUtil;
import edu.virginia.cs.vmarketplace.view.adapter.ListRecyclerViewAdapter;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView listRecycleView;
    private LinearLayoutManager listLayoutManager;
    private ListRecyclerViewAdapter adapter;
    private static final int PAGE_SIZE = 3;
    private Object loadMoreToken;
    private EndlessRecyclerViewScrollListener listener;
    private String currentTabName;
    private int currentTabIndex;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        Bundle instanceState = AppContextManager.getContextManager().getAppContext().getInstanceState();
        Intent intent = getIntent();
        String category = intent.getStringExtra(AppConstant.CATEGORY);
        if(category == null){
            category = instanceState.getString(AppConstant.CATEGORY);
            currentTabIndex = instanceState.getInt(AppConstant.SWITCH_TAB);
        }else{
            currentTabIndex = 0;
            instanceState.putString(AppConstant.CATEGORY, category);
            instanceState.putInt(AppConstant.SWITCH_TAB, currentTabIndex);
        }

        TextView view = findViewById(R.id.toolbar_title);
        view.setText(category);

        currentTabName = !CategoryUtil.getSubCategory(category).isEmpty() ? CategoryUtil.getSubCategory(category).get(currentTabIndex) : null;
        int backgroundResourceId =  (int) ReflectionUtil.getConstant(R.color.class, category.replaceAll(" ",""));
        tabLayout = findViewById(R.id.tab);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, backgroundResourceId));
        for(String tabName : CategoryUtil.getSubCategory(category)){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabName);
            tabLayout.addTab(tab);
        }
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.tan_background), ContextCompat.getColor(this, R.color.barBackground));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                int tabLayoutWidth = tabLayout.getWidth();
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int deviceWidth = metrics.widthPixels;
                if(tabLayoutWidth < deviceWidth){
                    tabLayout.setMinimumWidth(deviceWidth);
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                }else {
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });

        listRecycleView = findViewById(R.id.category_tab_list);

        listLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listRecycleView.setLayoutManager(listLayoutManager);

        adapter = new ListRecyclerViewAdapter(this, new ArrayList<ProductItemsDO>());
        listRecycleView.setAdapter(adapter);

        if(currentTabIndex == 0) {
            getSupportLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<PageRequest, ProductItemsDO>(
                    this,
                    adapter,
                    ProductItemService.getInstance()::findPostBySubCategory, new PageRequest(PAGE_SIZE, 0, currentTabName)
            ).with((x) -> {
                listener.setHasMorePage(x.getToken() != null);
                loadMoreToken = x.getToken();
                adapter.initData(x.getResult());
            })).forceLoad();
        }

        listener = new EndlessRecyclerViewScrollListener(listLayoutManager, 1) {
            @Override
            public void onNoMoreResult(RecyclerView view) {
                adapter.getFootViewHolder().progressBar.setVisibility(View.GONE);
                Toast.makeText(CategoryActivity.this, "No More Item", Toast.LENGTH_SHORT).show();
                ProductItemService.getInstance().clearCache();
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                PageRequest request = new PageRequest(PAGE_SIZE, page, currentTabName);
                request.setToken(loadMoreToken);
                adapter.getFootViewHolder().progressBar.setVisibility(View.VISIBLE);
                    getSupportLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<PageRequest, ProductItemsDO>(
                            CategoryActivity.this,
                            adapter,
                            (x)-> {
                                try {
                                    Thread.currentThread().sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return ProductItemService.getInstance().findPostBySubCategory(x);
                            }, request
                    ).with((x) ->{
                        adapter.getFootViewHolder().progressBar.setVisibility(View.INVISIBLE);
                        listener.setHasMorePage(x.getToken() != null);
                        loadMoreToken = x.getToken();
                        adapter.insertData(x.getResult(), totalItemsCount);
                        Toast.makeText(CategoryActivity.this, "Loaded more items successfully", Toast.LENGTH_SHORT).show();
                    })).forceLoad();
            }
        };
        listRecycleView.addOnScrollListener(listener);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabName = tab.getText().toString();
                listener.resetState();
                loadMoreToken = null;
                currentTabName = tabName;
                currentTabIndex = tab.getPosition();
                AppContextManager.getContextManager().getAppContext().getInstanceState().putInt(AppConstant.SWITCH_TAB, currentTabIndex);
                getSupportLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<PageRequest, ProductItemsDO>(
                        CategoryActivity.this,
                        adapter,
                        ProductItemService.getInstance()::findPostBySubCategory, new PageRequest(PAGE_SIZE, 0, tabName)
                ).with((x)->{
                    listener.setHasMorePage(x.getToken() != null);
                    loadMoreToken = x.getToken();
                    adapter.initData(x.getResult());
                })).forceLoad();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setScrollX(tabLayout.getWidth() / CategoryUtil.getSubCategory(category).size() * currentTabIndex);
        tabLayout.setSmoothScrollingEnabled(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tabLayout.getTabAt(currentTabIndex).select();
            }
        }, 50);
    }
}
