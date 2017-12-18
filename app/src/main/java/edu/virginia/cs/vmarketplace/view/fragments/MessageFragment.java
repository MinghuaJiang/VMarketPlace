package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.MessageDO;
import edu.virginia.cs.vmarketplace.service.MessageService;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.view.MessageDetailActivity;
import edu.virginia.cs.vmarketplace.view.adapter.MessageDOAdapter;


/**
 * Created by cutehuazai on 11/23/17.
 */

public class MessageFragment extends AbstractFragment{
    private SwipeRefreshLayout refreshLayout;
    private MessageDOAdapter adapter;
    private CommonLoaderCallback<String, MessageDO> callback;
    public MessageFragment(){
        super("message", R.drawable.message_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message, container, false);

        getActivity().getWindow().setStatusBarColor(
               getTabBackground());

        Toolbar toolbar =
                rootView.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        ab.setDisplayShowTitleEnabled(false);


        final ListView listView = rootView.findViewById(R.id.message_detail_list);
        adapter = new MessageDOAdapter(getActivity(), new ArrayList<MessageDO>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageDO item = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                startActivity(intent);
            }
        });

        refreshLayout = rootView.findViewById(R.id.refresh);
        callback = new CommonLoaderCallback<String, MessageDO>(adapter,
                MessageService.getInstance()::findMessagesOrderByLastUpdatedTime, AppContextManager.getContextManager().getAppContext().getUser().getUserId()).with(refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(0, null, callback).forceLoad();
            }
        });

        refreshLayout.setRefreshing(true);

        getLoaderManager().initLoader(0, null, callback).forceLoad();
        return rootView;
    }

    public int getTabBackground(){
        return ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
    }
}
