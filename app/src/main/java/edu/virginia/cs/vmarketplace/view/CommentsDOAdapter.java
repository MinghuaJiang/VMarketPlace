package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.CommentsDO;

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
        if(Profile.getCurrentProfile() != null) {
            Picasso.with(getContext()).load(Profile.getCurrentProfile().
                    getProfilePictureUri(160, 160)).fit().into(picView);
        }else{
            picView.setImageResource(R.drawable.place_holder_96p);
        }
        //picView.setImageResource(R.drawable.place_holder_96p);
        TextView userName = listView.findViewById(R.id.user_name);
        userName.setText(commentsDO.getCommentBy());

        TextView comment = listView.findViewById(R.id.comment);
        comment.setText(commentsDO.getComment());

        TextView time = listView.findViewById(R.id.time);
        time.setText(commentsDO.getCommentTime());

        return listView;
    }
}
