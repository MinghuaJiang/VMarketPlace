package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.AppContext;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.model.AppUser;
import edu.virginia.cs.vmarketplace.model.nosql.CommentsDO;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;
import edu.virginia.cs.vmarketplace.util.IntentUtil;

public class PublishDetailActivity extends AppCompatActivity {
    private AppContext context;
    private TextView username;
    private CircleImageView userpic;
    private NonScrollGridView gridView;
    private ImageView thumbup;
    private ImageView comment;
    private ImageView favorite;
    private DetailImageAdapter adapter;
    private String respondId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        respondId = null;
        setContentView(R.layout.activity_publish_detail);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(R.string.publish_detail);

        context = AppContextManager.getContextManager().getAppContext();
        final ProductItemsDO itemsDO = context.getItemsDO();
        username = findViewById(R.id.username);
        userpic = findViewById(R.id.user_pic);

        thumbup = findViewById(R.id.thumb_up);
        comment = findViewById(R.id.comment);
        favorite = findViewById(R.id.favorite);

        ImageButton hide = findViewById(R.id.keyboard_down);

        final RelativeLayout before = findViewById(R.id.before_chat);
        final RelativeLayout after = findViewById(R.id.after_chat);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                before.setVisibility(View.GONE);
                after.setVisibility(View.VISIBLE);
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

        Intent fromIntent = getIntent();
        int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
        if(jump_from == AppConstant.PUBLISH_BY_ME){
            AppUser user = context.getUser();
            username.setText(user.getUserName());
            if(user.getUserPic() == null && user.getUserPicUri() == null) {
                userpic.setImageResource(R.drawable.place_holder_96p);
            }else if(user.getUserPicUri() != null){
                Picasso.with(this).load(user.getUserPicUri()).fit().placeholder(R.drawable.place_holder_96p).into(userpic);
            }else{
                Picasso.with(this).load(user.getUserPic()).fit().placeholder(R.drawable.place_holder_96p).into(userpic);
            }
            button.setVisibility(View.GONE);
            thumbup.setVisibility(View.GONE);
            favorite.setVisibility(View.GONE);
        }else{
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
        final EditText editText = findViewById(R.id.input);
        final Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(PublishDetailActivity.this, "No input found!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String text = editText.getText().toString();
                CommentsDO commentsDO = new CommentsDO();
                commentsDO.setComment(text);
                commentsDO.setCommentId(UUID.randomUUID().toString());
                commentsDO.setItemId(itemsDO.getItemId());
                commentsDO.setCommentBy(AppContextManager.getContextManager().getAppContext().getUser().getUserName());
                commentsDO.setCommentTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                commentsDO.setResponseId(respondId);
                new CommentsUpdateTask(PublishDetailActivity.this, itemsDO).execute(commentsDO);
            }
        });

        TextView locationView = findViewById(R.id.publish_location);
        locationView.setText("Published at " + itemsDO.getLocationName().substring(itemsDO.getLocationName().indexOf(",") + 1
        , itemsDO.getLocationName().lastIndexOf("美")));

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

        TextView viewCount = findViewById(R.id.view);
        viewCount.setText("view "+ itemsDO.getViewCount().intValue());

        TextView replyCount = findViewById(R.id.comment_tab);

        if(itemsDO.getReplyCount().intValue() > 0){
            replyCount.setText("Comments " + itemsDO.getReplyCount().intValue());
        }

        itemsDO.setViewCount(itemsDO.getViewCount() + 1);
        new ViewCountUpdateTask().execute(itemsDO);
        AppContextManager.getContextManager().getAppContext().setItemsDO(null);
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        Intent fromIntent = getIntent();
        int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
        Intent intent = null;
        if(jump_from == AppConstant.PUBLISH_BY_ME){
            intent = new Intent(this, ProfilePublishActivity.class);
        }else if(jump_from == AppConstant.HOME_PAGE){

        }
        AppContextManager.getContextManager().getAppContext().setItemsDO(null);
        return intent;
    }

    class ViewCountUpdateTask extends AsyncTask<ProductItemsDO, Void, Void> {
        private DynamoDBMapper mapper;
        public ViewCountUpdateTask(){
            mapper = AWSClientFactory.getInstance().getDBMapper();
        }

        @Override
        protected Void doInBackground(ProductItemsDO... productItemsDOS) {
            mapper.save(productItemsDOS[0]);
            return null;
        }
    }

    class CommentsUpdateTask extends AsyncTask<CommentsDO, Void, Void>{
        private DynamoDBMapper mapper;
        private Context context;
        private ProductItemsDO itemDo;
        public CommentsUpdateTask(Context context, ProductItemsDO itemDo){
            mapper = AWSClientFactory.getInstance().getDBMapper();
            this.context = context;
            this.itemDo = itemDo;
        }

        @Override
        protected Void doInBackground(CommentsDO... commentsDOS) {
            mapper.save(commentsDOS[0]);
            itemDo.setReplyCount(itemDo.getReplyCount() + 1);
            mapper.save(itemDo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(PublishDetailActivity.this, "Comments added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
