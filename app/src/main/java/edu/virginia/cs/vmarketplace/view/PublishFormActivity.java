package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ItemStatus;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.UserProfileService;
import edu.virginia.cs.vmarketplace.service.loader.CommonAyncTask;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.CategoryUtil;
import edu.virginia.cs.vmarketplace.util.LocationHolder;
import edu.virginia.cs.vmarketplace.util.TimeUtil;
import edu.virginia.cs.vmarketplace.view.adapter.ImageViewAdapter;

public class PublishFormActivity extends AppCompatActivity {
    private static final String TITLE_CONTENT = "title";
    private static final String DESCRIPTION_CONTENT = "description";
    private static final String PRICE_CONTENT = "price";
    private static final String CATEGORY_CONTENT = "category";

    private AppContext appContext;
    private GridView gridView;
    private ImageViewAdapter adapter;
    private List<String> mFiles;
    private SingleLineEditText titleView;
    private EditText descriptionView;
    private ImageView locationLogo;
    private TextView locationView;
    private SingleLineEditText priceView;
    private Spinner spinner;

    private SwipeRefreshLayout refreshLayout;
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_form);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.separator));
        appContext = AppContextManager.getContextManager().getAppContext();
        Toolbar toolbar =
                findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Button closeBtn = findViewById(R.id.close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appContext.destroyInstanceState();
                Intent fromIntent = getIntent();
                int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
                if(jump_from == AppConstant.PUBLISH_BY_ME){
                    Intent intent = new Intent(PublishFormActivity.this, ProfilePublishActivity.class);
                    startActivity(intent);
                }else if(jump_from == AppConstant.PHOTO_ACTIVITY){
                    Intent intent = new Intent(PublishFormActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setEnabled(false);
        titleView = findViewById(R.id.title);
        descriptionView = findViewById(R.id.description);
        priceView = findViewById(R.id.price);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowTitleEnabled(false);

        gridView = findViewById(R.id.container);

        locationLogo = findViewById(R.id.location_logo);

        locationView = findViewById(R.id.location);

        if(LocationHolder.getInstance().getLocation() != null){
            locationView.setText(LocationHolder.getInstance().getLocation());
        }else{
            locationLogo.setBackground(getDrawable(R.drawable.location_blue));
            locationView.setText("Choose one location");
        }

        spinner = findViewById(R.id.category);

        boolean isPublish = appContext.isPublish();

        if(isPublish) {
            mFiles = getIntent().getStringArrayListExtra("image").stream().map(
                    (x)->new File(x).getName()).collect(Collectors.toList());

            category = appContext.getCurrentCategory();
            ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.category_item, CategoryUtil.getSubCategory(category));
            spinner.setAdapter(spinnerAdapter);
        }else{
            ProductItemsDO itemsDO = appContext.getItemsDO();
            mFiles = itemsDO.getOriginalFiles();
            titleView.setText(itemsDO.getTitle());
            descriptionView.setText(itemsDO.getDescription());
            priceView.setText(String.valueOf(itemsDO.getPrice()));

            category = itemsDO.getCategory();
            appContext.setCurrentCategory(category);
            ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.category_item, CategoryUtil.getSubCategory(category));
            spinner.setAdapter(spinnerAdapter);
            spinner.setSelection(itemsDO.getSubcategoryPosition().intValue());
        }

        if (mFiles != null) {
            List<String> temp = new ArrayList<>(mFiles);
            temp.add("dummy");
            adapter = new ImageViewAdapter(this, temp);
            gridView.setAdapter(adapter);
            adapter.setmFiles(mFiles);
        } else {
            gridView.setVisibility(View.GONE);
        }

        if(!appContext.getInstanceState().isEmpty()) {
            titleView.setText(appContext.getInstanceState().getString(TITLE_CONTENT));
            descriptionView.setText(appContext.getInstanceState().getString(DESCRIPTION_CONTENT));
            priceView.setText(appContext.getInstanceState().getString(PRICE_CONTENT));
            spinner.setSelection(appContext.getInstanceState().getInt(CATEGORY_CONTENT));
        }

        Button submitButton = findViewById(R.id.confirm_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleView.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.title_not_empty),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(priceView.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.price_not_empty),Toast.LENGTH_SHORT).show();
                    return;
                }

                refreshLayout.setRefreshing(true);
                String title = titleView.getText().toString();
                String location = locationView.getText().toString();
                String description = descriptionView.getText().toString();
                double price = Double.valueOf(priceView.getText().toString());
                String subCategory = (String)spinner.getSelectedItem();

                ProductItemsDO productItemsDo = appContext.getItemsDO();
                Intent fromIntent = getIntent();
                int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
                if(jump_from == AppConstant.PHOTO_ACTIVITY){
                    productItemsDo = new ProductItemsDO();
                    productItemsDo.setItemId(UUID.randomUUID().toString());
                    productItemsDo.setCreatedBy(appContext.getUser().getUserId());
                    productItemsDo.setViewCount(0.0);
                    productItemsDo.setReplyCount(0.0);
                    productItemsDo.setThumbUpCount(0.0);
                    Set<String> userIds = new HashSet<String>();
                    userIds.add("dummy");
                    productItemsDo.setThumbUpUserIds(userIds);
                    Set<String> favoriteIds = new HashSet<String>();
                    favoriteIds.add("dummy");
                    productItemsDo.setFavoriteUserIds(favoriteIds);
                    productItemsDo.setCreatedByName(appContext.getUser().getUserName());
                    productItemsDo.setStatus(ItemStatus.publish.toString());
                }

                if(appContext.getUser().getUserPicUri() != null) {
                    productItemsDo.setCreatedByAvatar(appContext.getUser().getUserPicUri().toString());
                }else{
                    productItemsDo.setCreatedByAvatar(appContext.getUser().getUserPic());
                }

                productItemsDo.setCategory(category);
                productItemsDo.setSubcategory(subCategory);
                productItemsDo.setPrice(price);
                productItemsDo.setTitle(title);
                productItemsDo.setSubcategoryPosition(Double.valueOf(spinner.getSelectedItemPosition()));
                productItemsDo.setDescription(description);
                productItemsDo.setLatitude(LocationHolder.getInstance().getLatitude());
                productItemsDo.setLongtitude(LocationHolder.getInstance().getLongitude());
                productItemsDo.setLocation(location);
                productItemsDo.setLastModificationTime(TimeUtil.formatYYYYMMDDhhmmss(new Date()));
                appContext.destroyInstanceState();
                loadIntoS3(productItemsDo);
            }
        });
    }

    public void saveState(){
        appContext.getInstanceState().putString(TITLE_CONTENT, titleView.getText().toString());
        appContext.getInstanceState().putString(DESCRIPTION_CONTENT, descriptionView.getText().toString());
        appContext.getInstanceState().putString(PRICE_CONTENT, priceView.getText().toString());
        appContext.getInstanceState().putInt(CATEGORY_CONTENT, spinner.getSelectedItemPosition());
    }

    private void loadIntoS3(final ProductItemsDO itemDo){
        final List<String> s3urls = new ArrayList<>();
        final List<String> files = new ArrayList<>();
        for(String each: mFiles) {
            final File file = new File(each);
            final String key = "public" + "/" + file.getName();
            files.add(file.getName());
            s3urls.add(S3Service.S3_PREFIX + key);
        }

        S3Service.getInstance(getApplicationContext()).upload(s3urls, files, true,
                (x)->{
                    itemDo.setPics(s3urls);
                    itemDo.setThumbPic(s3urls.get(0));
                    itemDo.setOriginalFiles(files);
                    insertToDB(itemDo);
                });

    }

    private void insertToDB(ProductItemsDO productItemsDO){
        appContext.getUserDO().getPublishItems().add(productItemsDO.getItemId());
        new CommonAyncTask<UserProfileDO, Void, Void>(
                UserProfileService.getInstance()::insertOrUpdate, appContext.getUserDO()).run();
        new CommonAyncTask<ProductItemsDO, Void, Void>(ProductItemService.getInstance() :: save, productItemsDO).with(
                (x) ->{
                    refreshLayout.setRefreshing(false);
                    Intent intent = new Intent(PublishFormActivity.this, PublishSuccessActivity.class);
                    PublishFormActivity.this.startActivity(intent);
                }
        ).run();
    }
}
