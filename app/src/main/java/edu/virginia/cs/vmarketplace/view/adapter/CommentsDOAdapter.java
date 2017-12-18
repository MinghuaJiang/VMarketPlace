package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.CommentsDO;
import edu.virginia.cs.vmarketplace.service.S3Service;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class CommentsDOAdapter extends ArrayAdapter<CommentsDO> {
    public CommentsDOAdapter(@NonNull Context context, @NonNull List<CommentsDO> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.user_comment, parent
                    , false);
        }
        CommentsDO commentsDO = getItem(position);
        ImageView picView = listView.findViewById(R.id.user_pic);
        if(commentsDO.getCommentByAvatar() != null) {
            if(commentsDO.getCommentByAvatar().startsWith(S3Service.S3_PREFIX)) {
                S3Service.getInstance(getContext()).download(commentsDO.getCommentByAvatar(),
                        (x)->{
                            Picasso.with(getContext()).load(x.get(0)).fit().into(picView);
                        });
            }else{
                Picasso.with(getContext()).load(commentsDO.getCommentByAvatar()).fit().into(picView);
            }
        }else{
            picView.setImageResource(R.drawable.place_holder_96p);
        }
        //picView.setImageResource(R.drawable.place_holder_96p);
        TextView userName = listView.findViewById(R.id.user_name);
        userName.setText(commentsDO.getCommentByName());

        TextView comment = listView.findViewById(R.id.comment);
        comment.setText(commentsDO.getComment());

        TextView time = listView.findViewById(R.id.time);
        time.setText(commentsDO.getCommentTime());

        return listView;
    }
}
