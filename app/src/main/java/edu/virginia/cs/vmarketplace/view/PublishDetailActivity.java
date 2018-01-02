package edu.virginia.cs.vmarketplace.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ThumbupDO;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.CommentService;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.ThumbupService;
import edu.virginia.cs.vmarketplace.service.UserProfileService;
import edu.virginia.cs.vmarketplace.service.loader.CommonAyncTask;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.service.login.AppUser;
import edu.virginia.cs.vmarketplace.model.CommentsDO;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.LocationUtil;
import edu.virginia.cs.vmarketplace.util.TimeUtil;
import edu.virginia.cs.vmarketplace.view.adapter.CommentsDOAdapter;
import edu.virginia.cs.vmarketplace.view.adapter.DetailImageAdapter;

public class PublishDetailActivity extends AppCompatActivity{
    private AppContext context;
    private TextView username;
    private CircleImageView userpic;
    private NonScrollGridView gridView;
    private NonScrollGridView commentView;
    private ScrollView scrollView;
    private ImageView thumbup;
    private ImageView comment;
    private ImageView favorite;
    private DetailImageAdapter adapter;
    private CommentsDOAdapter commentsDOAdapter;
    private TextView replyCount;
    private ProductItemsDO itemsDO;
    private String respondId;
    private CommonLoaderCallback<String, CommentsDO> callback;
    private AppUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        user = AppContextManager.getContextManager().getAppContext().getUser();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        TextView titleView = findViewById(R.id.toolbar_title);

        context = AppContextManager.getContextManager().getAppContext();
        itemsDO = context.getItemsDO();
        username = findViewById(R.id.username);
        userpic = findViewById(R.id.user_pic);

        thumbup = findViewById(R.id.thumb_up);
        comment = findViewById(R.id.comment);
        favorite = findViewById(R.id.favorite);
        final UserProfileDO userProfileDO = context.getUserDO();
        if(userProfileDO.getFavoriteItems().contains(itemsDO.getItemId())){
            favorite.setImageResource(R.drawable.favorite_yellow_24dp);
        }else{
            favorite.setImageResource(R.drawable.favorite_24dp);
        }

        if(userProfileDO.getThumbItems().contains(itemsDO.getItemId())){
            thumbup.setImageResource(R.drawable.like_fill);
        }else{
            thumbup.setImageResource(R.drawable.like);
        }



        scrollView = findViewById(R.id.scrollview);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY >= 325){
                    titleView.setText("$"+ itemsDO.getPrice());
                }else{
                    titleView.setText("");
                }
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userProfileDO.getFavoriteItems().contains(itemsDO.getItemId())){
                    favorite.setImageResource(R.drawable.favorite_24dp);
                    userProfileDO.getFavoriteItems().remove(itemsDO.getItemId());
                    new CommonAyncTask<UserProfileDO, Void, Void>(
                            UserProfileService.getInstance()::insertOrUpdate, userProfileDO).run();
                    Toast.makeText(getApplicationContext(), "Unfavorite the item successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    userProfileDO.getFavoriteItems().add(itemsDO.getItemId());
                    favorite.setImageResource(R.drawable.favorite_yellow_24dp);
                    new CommonAyncTask<UserProfileDO, Void, Void>(
                            UserProfileService.getInstance()::insertOrUpdate, userProfileDO).run();
                    Toast.makeText(getApplicationContext(), "Favorite the item successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final LinearLayout users = findViewById(R.id.users);
        final CircleImageView user1 = findViewById(R.id.user1);
        final CircleImageView user2 = findViewById(R.id.user2);
        final CircleImageView user3 = findViewById(R.id.user3);
        final TextView likeCount = findViewById(R.id.like);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishDetailActivity.this, LikeHistoryActivity.class);
                startActivity(intent);
            }
        });
        if(itemsDO.getThumbUpCount() == 0){
            users.setVisibility(View.GONE);
        }else{
            users.setVisibility(View.VISIBLE);
            likeCount.setText("like" + itemsDO.getThumbUpCount().intValue());
        }

        thumbup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userProfileDO.getThumbItems().contains(itemsDO.getItemId())){
                    thumbup.setImageResource(R.drawable.like);
                    userProfileDO.getThumbItems().remove(itemsDO.getItemId());
                    itemsDO.getThumbUpUserIds().remove(userProfileDO.getUserId());
                    itemsDO.setThumbUpCount(itemsDO.getThumbUpCount() - 1);

                    if(itemsDO.getThumbUpCount() == 0) {
                        users.setVisibility(View.GONE);
                    }else{
                        likeCount.setText("like" + itemsDO.getThumbUpCount().intValue());
                    }

                    new CommonAyncTask<Void, Void, Void>(
                            ()->{
                                ThumbupDO thumbupDO = ThumbupService.getInstance().findThumbupByKey(new String[]{itemsDO.getItemId(), context.getUser().getUserId()});
                                ThumbupService.getInstance().delete(thumbupDO);
                                return null;
                            }).with(
                            (x)->{
                                new CommonAyncTask<String, Void, List<ThumbupDO>>(
                                        ThumbupService.getInstance()::findThumbupByItemId,
                                        itemsDO.getItemId()).with((y)-> {
                                    context.setThumbupDO(y);
                                    updateThumbupAvatars(user1, user2, user3, y);
                                }).run();
                            }).run();
                    new CommonAyncTask<UserProfileDO, Void, Void>(
                            UserProfileService.getInstance()::insertOrUpdate, userProfileDO).run();
                    new CommonAyncTask<ProductItemsDO, Void, Void>(
                            ProductItemService.getInstance()::save, itemsDO).run();
                    Toast.makeText(getApplicationContext(), "Unlike the item successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    userProfileDO.getThumbItems().add(itemsDO.getItemId());
                    itemsDO.setThumbUpCount(itemsDO.getThumbUpCount() + 1);
                    users.setVisibility(View.VISIBLE);
                    likeCount.setText("like" + itemsDO.getThumbUpCount().intValue());
                    itemsDO.getThumbUpUserIds().add(userProfileDO.getUserId());
                    thumbup.setImageResource(R.drawable.like_fill);
                    ThumbupDO thumbupDO = new ThumbupDO();
                    thumbupDO.setItemId(itemsDO.getItemId());
                    thumbupDO.setThumbupTime(TimeUtil.formatYYYYMMDDhhmmss(new Date()));
                    thumbupDO.setThumbupById(userProfileDO.getUserId());
                    thumbupDO.setThumbupByName(userProfileDO.getUserName());
                    thumbupDO.setThumbupByAvatar(userProfileDO.getAvatar());
                    new CommonAyncTask<ThumbupDO, Void, Void>(
                            ThumbupService.getInstance()::insertOrUpdate, thumbupDO).with(
                                    (x)-> {
                        new CommonAyncTask<String, Void, List<ThumbupDO>>(
                                ThumbupService.getInstance()::findThumbupByItemId,
                                itemsDO.getItemId()).with((y)-> {
                            context.setThumbupDO(y);
                            updateThumbupAvatars(user1, user2, user3, y);
                        }).run();
                    }).run();
                    new CommonAyncTask<UserProfileDO, Void, Void>(
                            UserProfileService.getInstance()::insertOrUpdate, userProfileDO).run();
                    new CommonAyncTask<ProductItemsDO, Void, Void>(
                            ProductItemService.getInstance()::save, itemsDO).run();
                    Toast.makeText(getApplicationContext(), "Like the item successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton hide = findViewById(R.id.keyboard_down);

        final RelativeLayout before = findViewById(R.id.before_chat);
        final RelativeLayout after = findViewById(R.id.after_chat);
        final EditText editText = findViewById(R.id.input);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                before.setVisibility(View.GONE);
                after.setVisibility(View.VISIBLE);
                respondId = null;
                editText.setHint("");
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                after.setVisibility(View.GONE);
                before.setVisibility(View.VISIBLE);
            }
        });

        Button button = findViewById(R.id.buy);

        new CommonAyncTask<String, Void, List<ThumbupDO>>(
                ThumbupService.getInstance()::findThumbupByItemId,
                itemsDO.getItemId()).with((x)-> {
            context.setThumbupDO(x);
            updateThumbupAvatars(user1, user2, user3, x);
        }).run();

        Intent fromIntent = getIntent();
        int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
        if(jump_from == AppConstant.PUBLISH_BY_ME){
            AppUser user = context.getUser();
            username.setText(user.getUserName());
            if(user.getUserPic() == null && user.getUserPicUri() == null) {
                userpic.setImageResource(R.drawable.placeholder);
            }else if(user.getUserPicUri() != null){
                Picasso.with(this).load(user.getUserPicUri()).fit().placeholder(R.drawable.placeholder).into(userpic);
            }else{
                Picasso.with(this).load(user.getUserPic()).fit().placeholder(R.drawable.placeholder).into(userpic);
            }
            button.setVisibility(View.GONE);
            thumbup.setVisibility(View.GONE);
            favorite.setVisibility(View.GONE);
        }else{
            username.setText(itemsDO.getCreatedByName());
            if(itemsDO.getCreatedByAvatar() == null) {
                userpic.setImageResource(R.drawable.placeholder);
            }else if(itemsDO.getCreatedByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(
                        itemsDO.getCreatedByAvatar(), (x)->{
                            Picasso.with(this).load(x.get(0)).fit().placeholder(R.drawable.placeholder).into(userpic);
                        });
            }else{
                Picasso.with(this).load(itemsDO.getCreatedByAvatar()).fit().placeholder(R.drawable.placeholder).into(userpic);
            }
            button.setVisibility(View.VISIBLE);
            thumbup.setVisibility(View.VISIBLE);
            favorite.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PublishDetailActivity.this, MessageDetailActivity.class);
                    intent.putExtra(AppConstant.JUMP_FROM, AppConstant.PUBLISH_DETAIL);
                    context.setItemsDO(itemsDO);
                    startActivity(intent);
                }
            });
        }
        final Button sendButton = findViewById(R.id.send);
        replyCount = findViewById(R.id.comment_tab);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(PublishDetailActivity.this, "No input found!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String text = editText.getText().toString();
                CommentsDO commentsDO = new CommentsDO();
                if(respondId == null) {
                    commentsDO.setComment(text);
                }else{
                    commentsDO.setComment(editText.getHint().toString() + " " + text);
                }
                commentsDO.setCommentId(UUID.randomUUID().toString());
                commentsDO.setItemId(itemsDO.getItemId());
                commentsDO.setCommentBy(user.getUserId());
                commentsDO.setCommentByName(user.getUserName());
                if(user.getUserPicUri() != null) {
                    commentsDO.setCommentByAvatar(user.getUserPicUri().toString());
                }else{
                    commentsDO.setCommentByAvatar(user.getUserPic());
                }
                commentsDO.setCommentTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                commentsDO.setRespondId(respondId);
                editText.setHint("");
                editText.setText("");
                new CommonAyncTask<CommentsDO, Void, ProductItemsDO>((x)->{
                    CommentService.getInstance().insertOrUpdate(x);
                    itemsDO.setReplyCount(itemsDO.getReplyCount() + 1);
                    ProductItemService.getInstance().save(itemsDO);
                    return itemsDO;
                }, commentsDO).with((x)->{
                        if(x.getReplyCount().intValue() > 0){
                            replyCount.setText("Comments " + x.getReplyCount().intValue());
                        }
                        getSupportLoaderManager().restartLoader(0, null, callback).forceLoad();
                    }).run();
                Toast.makeText(getApplicationContext(), "Comments added successfully", Toast.LENGTH_SHORT).show();
            }
        });

        TextView timeView = findViewById(R.id.publish_time);
        timeView.setText(TimeUtil.getRelativeTimeFromNow(itemsDO.getLastModificationTime()));

        TextView locationView = findViewById(R.id.publish_location);
        locationView.setText("@" + LocationUtil.getAddressAndZipCode(itemsDO.getLocation()));

        TextView priceView = findViewById(R.id.price_container);
        if(itemsDO.getPrice() == null){
            priceView.setVisibility(View.GONE);
        }else{
            priceView.setVisibility(View.VISIBLE);
            priceView.setText("$" + itemsDO.getPrice());
        }

        TextView title = findViewById(R.id.title_container);
        title.setText(itemsDO.getTitle());

        TextView description = findViewById(R.id.description_container);
        if(itemsDO.getDescription() == null){
            description.setVisibility(View.GONE);
        }else{
            description.setVisibility(View.VISIBLE);
            description.setText(itemsDO.getDescription());
        }

        gridView = findViewById(R.id.pic_container);
        adapter = new DetailImageAdapter(this, itemsDO.getPics(),itemsDO.getOriginalFiles());
        gridView.setAdapter(adapter);
        gridView.setFocusable(false);

        commentView = findViewById(R.id.user_comment);
        commentsDOAdapter = new CommentsDOAdapter(this, new ArrayList<CommentsDO>());
        commentView.setAdapter(commentsDOAdapter);

        commentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CommentsDO commentsDO = commentsDOAdapter.getItem(position);
                if(commentsDO.getCommentBy().equals(AppContextManager.
                        getContextManager().getAppContext().getUser().getUserId())){
                    new AlertDialog.Builder(PublishDetailActivity.this)
                            .setTitle("Delete Comment")
                            .setMessage("Do you really want to delete it?")
                            .setIcon(R.drawable.warning)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    new CommonAyncTask<CommentsDO,Void,Void>((x)-> {
                                        CommentService.getInstance().deleteComments(x);
                                        itemsDO.setReplyCount(itemsDO.getReplyCount() - 1);
                                        ProductItemService.getInstance().save(itemsDO);
                                    }, commentsDO).
                                            with((x)->{
                                                Toast.makeText(PublishDetailActivity.this, "Comments deleted successfully", Toast.LENGTH_SHORT).show();
                                                if(itemsDO.getReplyCount().intValue() > 0){
                                                    replyCount.setText("Comments " + itemsDO.getReplyCount().intValue());
                                                }else{
                                                    replyCount.setText("Comments");
                                                }
                                                getSupportLoaderManager().restartLoader(0, null, callback).forceLoad();
                                            }).run();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }else{
                    respondId = commentsDO.getCommentBy();
                    TextView userName = parent.findViewById(R.id.user_name);
                    editText.setHint("@" + userName.getText());
                    before.setVisibility(View.GONE);
                    after.setVisibility(View.VISIBLE);
                }
            }
        });

        callback = new CommonLoaderCallback<String, CommentsDO>(
                commentsDOAdapter,
                CommentService.getInstance()::findCommentsByItemId,
                itemsDO.getItemId());

        getSupportLoaderManager().restartLoader(0, null,
                callback).forceLoad();

        if(itemsDO.getReplyCount().intValue() > 0){
            replyCount.setText("Comments " + itemsDO.getReplyCount().intValue());
        }

        itemsDO.setViewCount(itemsDO.getViewCount() + 1);
        TextView viewCount = findViewById(R.id.view);
        viewCount.setText("view"+ itemsDO.getViewCount().intValue());
        new CommonAyncTask<ProductItemsDO, Void, Void>(ProductItemService.getInstance()::save, itemsDO).run();
    }

    private void updateThumbupAvatars(CircleImageView user1, CircleImageView user2, CircleImageView user3, List<ThumbupDO> list) {
        if(itemsDO.getThumbUpCount() == 0){
            user1.setVisibility(View.GONE);
            user2.setVisibility(View.GONE);
            user3.setVisibility(View.GONE);
        }else if(itemsDO.getThumbUpCount() == 1) {
            user3.setVisibility(View.VISIBLE);
            user1.setVisibility(View.GONE);
            user2.setVisibility(View.GONE);
            ThumbupDO thumbupDO = list.get(0);
            if(thumbupDO.getThumbupByAvatar() == null){
                user3.setImageResource(R.drawable.placeholder);
            }else if(thumbupDO.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(thumbupDO.getThumbupByAvatar(),
                        (x)->{
                    Picasso.with(getApplicationContext()).load(x.get(0)).fit().into(user3);
                        });
            }else{
                Picasso.with(getApplicationContext()).load(thumbupDO.getThumbupByAvatar()).fit().into(user3);
            }
        }else if(itemsDO.getThumbUpCount() == 2){
            user3.setVisibility(View.VISIBLE);
            user2.setVisibility(View.VISIBLE);
            user1.setVisibility(View.GONE);
            ThumbupDO thumbupDO = list.get(1);
            if(thumbupDO.getThumbupByAvatar() == null){
                user3.setImageResource(R.drawable.placeholder);
            }else if(thumbupDO.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(thumbupDO.getThumbupByAvatar(),
                        (x)->{
                            Picasso.with(getApplicationContext()).load(x.get(0)).fit().into(user3);
                        });
            }else{
                Picasso.with(getApplicationContext()).load(thumbupDO.getThumbupByAvatar()).fit().into(user3);
            }

            ThumbupDO thumbupDO2 = list.get(0);
            if(thumbupDO2.getThumbupByAvatar() == null){
                user2.setImageResource(R.drawable.placeholder);
            }else if(thumbupDO2.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(thumbupDO2.getThumbupByAvatar(),
                        (x)->
                        {
                            Picasso.with(getApplicationContext()).load(x.get(0)).fit().into(user2);
                        });
            }else{
                Picasso.with(getApplicationContext()).load(thumbupDO2.getThumbupByAvatar()).fit().into(user2);
            }
        }else{
            user3.setVisibility(View.VISIBLE);
            user2.setVisibility(View.VISIBLE);
            user1.setVisibility(View.VISIBLE);

            ThumbupDO thumbupDO = list.get(2);
            if(thumbupDO.getThumbupByAvatar() == null){
                user3.setImageResource(R.drawable.placeholder);
            }else if(thumbupDO.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(thumbupDO.getThumbupByAvatar(),
                        (x)->{
                            Picasso.with(getApplicationContext()).load(x.get(0)).fit().into(user3);
                        });
            }else{
                Picasso.with(getApplicationContext()).load(thumbupDO.getThumbupByAvatar()).fit().into(user3);
            }

            ThumbupDO thumbupDO2 = list.get(1);
            if(thumbupDO2.getThumbupByAvatar() == null){
                user2.setImageResource(R.drawable.placeholder);
            }else if(thumbupDO2.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(thumbupDO2.getThumbupByAvatar(),
                        (x)->
                        {
                            Picasso.with(getApplicationContext()).load(x.get(0)).fit().into(user2);
                        });
            }else{
                Picasso.with(getApplicationContext()).load(thumbupDO2.getThumbupByAvatar()).fit().into(user2);
            }

            ThumbupDO thumbupDO3 = list.get(0);
            if(thumbupDO3.getThumbupByAvatar() == null){
                user1.setImageResource(R.drawable.placeholder);
            }else if(thumbupDO3.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
                S3Service.getInstance(getApplicationContext()).download(thumbupDO3.getThumbupByAvatar(),
                        (x)->
                        {
                            Picasso.with(getApplicationContext()).load(x.get(0)).fit().into(user1);
                        });
            }else{
                Picasso.with(getApplicationContext()).load(thumbupDO3.getThumbupByAvatar()).fit().into(user1);
            }
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        Intent fromIntent = getIntent();
        int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
        Intent intent = null;
        if(jump_from == AppConstant.PUBLISH_BY_ME){
            intent = new Intent(this, ProfilePublishActivity.class);
        }else if(jump_from == AppConstant.HOME_PAGE){
            intent = new Intent(this, MainActivity.class);
        }else if(jump_from == AppConstant.MY_FAVORITE){
            intent = new Intent(this, ProfileFavoriteActivity.class);
        }
        return intent;
    }
}
