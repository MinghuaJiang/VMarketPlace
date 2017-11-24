package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.model.SoldItem;

public class SoldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);
        ListView listView = findViewById(R.id.profile_detail_list);
        SoldItemAdapter adapter = new SoldItemAdapter(this, getSoldList());
        listView.setAdapter(adapter);
    }

    //Get from backend service
    private List<SoldItem> getSoldList(){
        List<SoldItem> list = new ArrayList<SoldItem>();
        SoldItem item = new SoldItem();
        item.setId(1);
        item.setImageResourceId(R.drawable.family_father);
        item.setTitle("Cool Kid");
        item.setPrice(100.0);
        list.add(item);
        item = new SoldItem();
        item.setId(2);
        item.setImageResourceId(R.drawable.family_mother);
        item.setTitle("Bao");
        item.setPrice(200.0);
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
