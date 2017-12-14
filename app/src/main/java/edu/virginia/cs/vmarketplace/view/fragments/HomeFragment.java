package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class HomeFragment extends AbstractFragment {
    private AbstractFragment[] fragments;
    private SliderLayout mSlider;
    private int[] sliderIds;
    private String[] sliderNames;

    public HomeFragment() {
        super("home", R.drawable.home_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initFragments();
        View rootView = inflater.inflate(R.layout.home, container, false);
        Toolbar toolbar =
                rootView.findViewById(R.id.home_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        ab.setDisplayShowTitleEnabled(false);

        getActivity().getWindow().setStatusBarColor(
                getTabBackground());

        sliderIds = new int[]{R.drawable.uva_rotunda_spring, R.drawable.uva_rotunda_summer, R.drawable.uva_rotunda, R.drawable.uva_rotunda_winter};
        sliderNames = new String[]{"Spring", "Summer", "Fall", "Winter"};
        mSlider = rootView.findViewById(R.id.slider);

        for(int i = 0;i < sliderIds.length;i++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(sliderNames[i])
                    .image(sliderIds[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);

        TabLayout tabLayout = rootView.findViewById(R.id.tab);

        TabLayout.Tab tabNew = tabLayout.newTab();

        tabNew.setText(fragments[0].getTabName());

        TabLayout.Tab tabNearBy = tabLayout.newTab();

        tabNearBy.setText(fragments[1].getTabName());

        tabLayout.addTab(tabNew);

        tabLayout.addTab(tabNearBy);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.tab_container, fragments[0]).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.tab_container, fragments[tab.getPosition()]).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    public int getTabBackground(){
        return ContextCompat.getColor(getContext(), R.color.barBackground);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void initFragments() {
        fragments = new AbstractFragment[2];
        fragments[0] = new HomeTabNewFragment();
        fragments[1] = new HomeTabNearByFragment();
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onResume(){
        super.onResume();
        mSlider.startAutoCycle();
    }

}
