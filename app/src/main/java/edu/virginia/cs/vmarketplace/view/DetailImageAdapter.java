package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;

/**
 * Created by cutehuazai on 12/4/17.
 */

public class DetailImageAdapter extends ArrayAdapter<String>{
    private TransferUtility utility;
    private List<String> originalFileList;
    public DetailImageAdapter(@NonNull Context context, @NonNull List<String> objects, List<String> originalFileList) {
        super(context, 0, objects);
        utility = AWSClientFactory.getInstance().getTransferUtility(getContext());
        this.originalFileList = originalFileList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.detail_image, parent
                    , false);
        }
        String key = getItem(position);
        String fileStr = originalFileList.get(position);
        final ImageView view = listView.findViewById(R.id.image);
        view.setImageResource(R.drawable.place_holder_96p);
        final File file = new File(fileStr);
        utility.download(key, file, new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if(state == TransferState.COMPLETED){
                    Picasso.with(getContext()).load(file).placeholder(R.drawable.place_holder_96p).
                            fit().into(view);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            }

            @Override
            public void onError(int id, Exception ex) {
                view.setImageResource(R.drawable.place_holder_96p);
            }
        });

        return listView;
    }
}
