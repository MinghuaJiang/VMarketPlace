package edu.virginia.cs.vmarketplace.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;
import edu.virginia.cs.vmarketplace.view.fragments.HomeFragment;
import edu.virginia.cs.vmarketplace.view.fragments.MessageFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PlaceFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ProfileFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PublishFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ViewPagerAdapter;

/**
 * Created by cutehuazai on 11/21/17.
 */

public class MainActivity extends AppCompatActivity{
    private AbstractFragment[] fragments;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments();
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < fragments.length;i++){
            tabLayout.getTabAt(i).setIcon(fragments[i].getIconResourceId());
        }
    }

    private void initFragments(){
        fragments = new AbstractFragment[5];
        fragments[0] = new HomeFragment();
        fragments[1] = new PlaceFragment();
        fragments[2] = new PublishFragment();
        fragments[3] = new MessageFragment();
        fragments[4] = new ProfileFragment();

    }
}
