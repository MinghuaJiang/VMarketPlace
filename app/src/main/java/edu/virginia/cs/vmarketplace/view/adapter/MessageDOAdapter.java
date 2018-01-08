package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bassaer.chatmessageview.models.Message;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.MessageDO;
import edu.virginia.cs.vmarketplace.service.S3Service;


/**
 * Created by cutehuazai on 11/24/17.
 */

public class MessageDOAdapter extends ArrayAdapter<MessageDO> {
    public MessageDOAdapter(@NonNull Context context, @NonNull List<MessageDO> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.message_list_item, parent
                    ,false);
        }

        MessageDO currentItem = getItem(position);

        CircleImageView imageView = listView.findViewById(R.id.image);
        if(currentItem.getSellerAvatar() == null) {
           imageView.setImageResource(R.drawable.place_holder_24p);
        }else if(currentItem.getSellerAvatar().startsWith(S3Service.S3_PREFIX)){
            S3Service.getInstance(getContext()).download(currentItem.getSellerAvatar(),
                    (x)-> Picasso.with(getContext()).load(x.get(0))
                            .placeholder(R.drawable.place_holder_96p).fit().into(imageView));
        }else{
            Picasso.with(getContext()).load(currentItem.getSellerAvatar())
                    .placeholder(R.drawable.place_holder_96p).fit().into(imageView);
        }
        TextView typeView = listView.findViewById(R.id.name);
        typeView.setText(currentItem.getSellerName());

        TextView messageView = listView.findViewById(R.id.message);
        if(currentItem.getLastUpdateMessageType().equals(Message.Type.PICTURE.name())) {
            messageView.setText("Picture");
        }else if(currentItem.getLastUpdateMessageType().equals(Message.Type.MAP)) {
            messageView.setText("Map");
        }else{
            messageView.setText(currentItem.getLastUpdateMessage());
        }
        TextView timeView = listView.findViewById(R.id.time);
        timeView.setText(currentItem.getLastUpdateTime());

        ImageView picView = listView.findViewById(R.id.thumbpic);

        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + currentItem.getItemOriginalPicFile());
        if(!file.exists()){
            S3Service.getInstance(getContext()).download(currentItem.getItemThumbPic(), file.getName(),
                    (x) ->  Picasso.with(getContext()).load(x.get(0))
                            .placeholder(R.drawable.place_holder_96p).fit().into(picView));
        }else{
            Picasso.with(getContext()).load(file)
                    .placeholder(R.drawable.place_holder_96p).fit().into(picView);
        }
        return listView;
    }
}
