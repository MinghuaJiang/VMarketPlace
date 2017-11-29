package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.bassaer.chatmessageview.model.User;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;

import java.util.Random;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.model.AppUser;
import edu.virginia.cs.vmarketplace.util.IntentUtil;
import edu.virginia.cs.vmarketplace.view.fragments.MessageFragment;

public class MessageDetailActivity extends AppCompatActivity {
    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar =
                 findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(intent.getStringExtra(AppConstant.SELLER_NAME));

        final ChatView mChatView = findViewById(R.id.chat_view);
        final ViewGroup.LayoutParams layoutParams = mChatView.getLayoutParams();
        //AppUser id
        int myId = 0;
        //User name
        String myName = AppContextManager.getContextManager().getAppContext().getUser().getUsername();
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.place_holder_64p);

        int yourId = 1;
        String yourName = intent.getStringExtra(AppConstant.SELLER_NAME);
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);

        final User me = new User(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);

        mChatView.setAutoScroll(true);
        mChatView.setOptionIcon(R.drawable.add_24p);
        mChatView.setOptionButtonColor(Color.BLACK);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.message_me));
        mChatView.setLeftBubbleColor(ContextCompat.getColor(this, R.color.message_you));
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.separator));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setSendButtonColor(Color.BLACK);
        mChatView.setRightMessageTextColor(Color.BLACK);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.BLACK);
        mChatView.setSendTimeTextColor(Color.BLACK);
        mChatView.setDateSeparatorColor(Color.BLACK);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        mChatView.setOnClickOptionButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    mChatView.setOptionIcon(R.drawable.close_24p);
                    mChatView.hideKeyboard();
                    isOpen = true;
                    layoutParams.height = 460;
                    mChatView.setLayoutParams(layoutParams);
                } else {
                    mChatView.setOptionIcon(R.drawable.add_24p);
                    isOpen = false;
                    layoutParams.height = -1;
                    mChatView.setLayoutParams(layoutParams);
                }
            }
        });


        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message
                if (!mChatView.getInputText().isEmpty()) {
                    Message message = new Message.Builder()
                            .setUser(me)
                            .setRightMessage(true)
                            .setMessageText(mChatView.getInputText())
                            .hideIcon(false)
                            .setUsernameVisibility(false)
                            .build();
                    //Set to chat view
                    mChatView.send(message);
                    //Reset edit text
                    mChatView.setInputText("");

                    //Receive message
                    final Message receivedMessage = new Message.Builder()
                            .setUser(you)
                            .setRightMessage(false)
                            .setUsernameVisibility(false)
                            .hideIcon(false)
                            .setMessageText(ChatBot.INSTANCE.talk(me.getName(), message.getMessageText()))
                            .build();

                    // This is a demo bot
                    // Return within 3 seconds
                    int sendDelay = (new Random().nextInt(4) + 1) * 1000;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mChatView.receive(receivedMessage);
                        }
                    }, sendDelay);
                }
            }
        });
    }


    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
        Intent intent = getIntent();
        int jumpFrom = intent.getIntExtra(AppConstant.JUMP_FROM, 0);
        if(jumpFrom == AppConstant.MAIN_ACTIVITY) {
            return IntentUtil.jumpWithTabRecorded(AppConstant.TAB_MESSAGE, this, MainActivity.class);
        }else{
            return new Intent(this, SoldActivity.class);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
