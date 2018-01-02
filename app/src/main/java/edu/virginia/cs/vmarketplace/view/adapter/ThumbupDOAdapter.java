package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ThumbupDO;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.util.TimeUtil;

/**
 * Created by cutehuazai on 12/28/17.
 */

public class ThumbupDOAdapter extends ArrayAdapter<ThumbupDO> {
    public ThumbupDOAdapter(@NonNull Context context, @NonNull List<ThumbupDO> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.thumbup_item, parent
                    , false);
        }

        ThumbupDO thumbupDO = getItem(position);

        CircleImageView imageView = listView.findViewById(R.id.image);

        if(thumbupDO.getThumbupByAvatar() == null){
            imageView.setImageResource(R.drawable.placeholder);
        }else if(thumbupDO.getThumbupByAvatar().startsWith(S3Service.S3_PREFIX)){
            S3Service.getInstance(getContext()).download(thumbupDO.getThumbupByAvatar(),
                    (x)->{
                        Picasso.with(getContext()).load(x.get(0)).fit().into(imageView);
                    });
        }else{
            Picasso.with(getContext()).load(thumbupDO.getThumbupByAvatar()).fit().into(imageView);
        }

        TextView user = listView.findViewById(R.id.username);
        user.setText(thumbupDO.getThumbupByName());

        TextView time = listView.findViewById(R.id.like_time);
        time.setText(TimeUtil.getRelativeTimeFromNow(thumbupDO.getThumbupTime()));

        return listView;
    }
}
