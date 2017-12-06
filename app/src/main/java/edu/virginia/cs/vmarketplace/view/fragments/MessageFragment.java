package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.model.MessageItem;
import edu.virginia.cs.vmarketplace.view.MessageDetailActivity;
import edu.virginia.cs.vmarketplace.service.loader.MessageItemLoader;


/**
 * Created by cutehuazai on 11/23/17.
 */

public class MessageFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<List<MessageItem>> {
    private List<MessageItem> list;
    private SwipeRefreshLayout refreshLayout;
    private MessageItemAdapter adapter;
    public MessageFragment(){
        super("message", R.drawable.message_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message, container, false);
        Toolbar toolbar =
                rootView.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        ab.setDisplayShowTitleEnabled(false);


        final ListView listView = rootView.findViewById(R.id.message_detail_list);
        adapter = new MessageItemAdapter(getActivity(), new ArrayList<MessageItem>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageItem item = list.get(position);
                Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                intent.putExtra(AppConstant.JUMP_FROM, AppConstant.MAIN_ACTIVITY);
                intent.putExtra(AppConstant.BUYER_NAME, item.getSellerName());
                intent.putExtra(AppConstant.MESSAGE_ID, item.getId());
                startActivity(intent);
            }
        });

        refreshLayout = rootView.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(0, null, MessageFragment.this).forceLoad();
            }
        });

        refreshLayout.setRefreshing(true);
        getLoaderManager().initLoader(0, null, MessageFragment.this).forceLoad();
        return rootView;
    }

    @Override
    public Loader<List<MessageItem>> onCreateLoader(int id, Bundle args) {
        return new MessageItemLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<MessageItem>> loader, List<MessageItem> data) {
        list = data;
        adapter.clear();
        adapter.addAll(data);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<MessageItem>> loader) {
        adapter.clear();
    }

}
