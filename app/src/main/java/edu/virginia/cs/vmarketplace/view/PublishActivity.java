package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.view.fragments.ProfileItemAdapter;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class PublishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);
        ListView listView = findViewById(R.id.profile_detail_list);
        PublishItemAdapter adapter = new PublishItemAdapter(this, getPublishList());
        listView.setAdapter(adapter);
    }

    //Get from backend service
    private List<PublishItem> getPublishList(){
        List<PublishItem> list = new ArrayList<PublishItem>();
        PublishItem item = new PublishItem();
        item.setId(1);
        item.setTitle("Cool Kid");
        item.setPrice(100.0);
        item.setReplyCount(5);
        item.setViewCount(200);
        list.add(item);
        item = new PublishItem();
        item.setId(2);
        item.setTitle("Bao");
        item.setPrice(200.0);
        item.setReplyCount(3);
        item.setViewCount(100);
        list.add(item);

        return list;
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
