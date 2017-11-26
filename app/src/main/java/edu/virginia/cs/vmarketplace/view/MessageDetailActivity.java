package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.bassaer.chatmessageview.model.User;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;

import java.util.Random;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.AppUser;
import edu.virginia.cs.vmarketplace.util.IntentUtil;

public class MessageDetailActivity extends AppCompatActivity {
    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        final ChatView mChatView = findViewById(R.id.chat_view);
        final ViewGroup.LayoutParams layoutParams = mChatView.getLayoutParams();
        //AppUser id
        int myId = 0;
        //AppUser icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //AppUser name
        String myName = "Michael";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = "Emily";

        final User me = new User(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);

        mChatView.setAutoScroll(true);
        mChatView.setOptionIcon(R.drawable.add_24p);
        mChatView.setOptionButtonColor(Color.BLACK);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.message_me));
        mChatView.setLeftBubbleColor(ContextCompat.getColor(this, R.color.message_you));
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.click));
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
                    isOpen = true;
                    layoutParams.height = 1000;
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
                            .build();
                    //Set to chat view
                    mChatView.send(message);
                    //Reset edit text
                    mChatView.setInputText("");

                    //Receive message
                    final Message receivedMessage = new Message.Builder()
                            .setUser(you)
                            .setRightMessage(false)
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
}
