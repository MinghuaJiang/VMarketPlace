package edu.virginia.cs.vmarketplace.service;

import android.content.Context;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.loader.AsyncCallback;

/**
 * Created by cutehuazai on 12/7/17.
 */

public class S3Service {
    private static S3Service service;
    private TransferUtility utility;
    private Context context;
    private S3Callback callback;

    private S3Service(Context context){
        this.context = context;
        utility = AWSClientFactory.getInstance().getTransferUtility(context);
    }

    public static S3Service getInstance(Context context){
        if(service == null){
            service = new S3Service(context);
        }
        return service;
    }

    public void download(List<String> s3Urls, List<String> downloadedFile, S3Callback callback){
        String baseFileDir = context.getExternalFilesDir(null) + File.separator;
        List<Integer> count = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        for(int i = 0; i < s3Urls.size();i++){
            File file = new File(baseFileDir + downloadedFile.get(i));
            fileList.add(file);
            utility.download(s3Urls.get(i), file, new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if(state == TransferState.COMPLETED){
                        count.add(1);
                        if(count.size() == s3Urls.size()){
                            if(callback != null){
                                callback.runCallback(fileList);
                            }
                        }
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public void upload(List<String> s3Urls, List<String> files, boolean deleteOriginalFile, S3Callback callback){
        String baseFileDir = context.getExternalFilesDir(null) + File.separator;
        List<Integer> count = new ArrayList<>();
        for(int i = 0; i < s3Urls.size();i++){
            final File file = new File(baseFileDir + files.get(i));
            TransferObserver observer = utility.upload(s3Urls.get(i), file);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if(state == TransferState.COMPLETED){
                        if(deleteOriginalFile){
                            file.delete();
                        }
                        count.add(1);
                        if(count.size() == s3Urls.size()){
                            if(callback != null){
                                callback.runCallback(null);
                            }
                        }
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
}
