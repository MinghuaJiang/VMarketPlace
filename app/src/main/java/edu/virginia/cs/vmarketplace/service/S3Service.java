package edu.virginia.cs.vmarketplace.service;

import android.content.Context;
import android.os.Environment;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;

/**
 * Created by cutehuazai on 12/7/17.
 */

public class S3Service {
    private static S3Service service;
    private TransferUtility utility;
    private Context context;
    public static final String S3_PREFIX = "S3://";
    private S3Callback callback;

    private S3Service(Context context) {
        this.context = context;
        utility = AWSClientFactory.getInstance().getTransferUtility(context);
    }

    public static S3Service getInstance(Context context) {
        if (service == null) {
            service = new S3Service(context);
        }
        return service;
    }

    public void download(String s3Url, S3Callback callback) {
        List<String> s3Urls = new ArrayList<>();
        s3Urls.add(s3Url);
        List<String> fileList = new ArrayList<>();
        fileList.add(UUID.randomUUID() + ".png");
        download(s3Urls, fileList, callback);
    }

    public void download(String s3Url, String downloadedFile, S3Callback callback) {
        List<String> s3Urls = new ArrayList<>();
        s3Urls.add(s3Url);
        List<String> fileList = new ArrayList<>();
        fileList.add(downloadedFile);
        download(s3Urls, fileList, callback);
    }

    public void download(List<String> s3Urls, List<String> downloadedFile, S3Callback callback) {
        String baseFileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator;
        List<Integer> count = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < s3Urls.size(); i++) {
            File file = new File(baseFileDir + downloadedFile.get(i));
            fileList.add(file);
            utility.download(s3Urls.get(i).substring(S3_PREFIX.length()), file, new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state == TransferState.COMPLETED) {
                        count.add(1);
                        if (count.size() == s3Urls.size()) {
                            if (callback != null) {
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

    public void upload(List<String> s3Urls, List<String> files, boolean deleteOriginalFile, S3Callback callback) {
        String baseFileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator;
        List<Integer> count = new ArrayList<>();
        for (int i = 0; i < s3Urls.size(); i++) {
            final File file = new File(baseFileDir + files.get(i));
            TransferObserver observer = utility.upload(s3Urls.get(i).substring(S3_PREFIX.length()), file);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state == TransferState.COMPLETED) {
                        if (deleteOriginalFile) {
                            file.delete();
                        }
                        count.add(1);
                        if (count.size() == s3Urls.size()) {
                            if (callback != null) {
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
