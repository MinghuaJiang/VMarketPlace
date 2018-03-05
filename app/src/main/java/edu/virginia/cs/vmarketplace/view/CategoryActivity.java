package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.view.adapter.ListRecyclerViewAdapter;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView listRecycleView;
    private LinearLayoutManager listLayoutManager;
    private ListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        String category = intent.getStringExtra(AppConstant.CATEGORY);

        TextView view = findViewById(R.id.toolbar_title);
        view.setText(category);

        listRecycleView = findViewById(R.id.category_tab_list);

        listLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listRecycleView.setLayoutManager(listLayoutManager);

        adapter = new ListRecyclerViewAdapter(this, new ArrayList<ProductItemsDO>(), category, this);
        listRecycleView.setAdapter(adapter);

    }
}
