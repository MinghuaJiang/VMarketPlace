package edu.virginia.cs.vmarketplace.util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by mijian on 2/3/2018.
 */

public class ShareUtil {
    public static Intent shareContent(PackageManager pm, Intent intent, String type, Set<String> whiteList,
                                    String text, String title){
        intent.setType(type);
        intent.setAction(Intent.ACTION_SEND);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resolveInfos.size(); i++) {
            ResolveInfo ri = resolveInfos.get(i);
            String packageName = ri.activityInfo.packageName;
            Intent intentToAdd = new Intent();
            if(whiteList.contains(packageName)){
                //this is the intent we are interested in
                intentToAdd.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intentToAdd.setAction(Intent.ACTION_SEND);
                intentToAdd.setType(type);
                intentToAdd.putExtra(Intent.EXTRA_TEXT, text);
                intentToAdd.putExtra(Intent.EXTRA_EMAIL, intent.getStringArrayExtra(Intent.EXTRA_EMAIL));
                intentToAdd.putExtra(Intent.EXTRA_SUBJECT, intent.getStringExtra(Intent.EXTRA_SUBJECT));
                //add this intent to the list
                intentList.add(new LabeledIntent(intentToAdd, packageName,
                        ri.loadLabel(pm), ri.icon));
            }
        }

        LabeledIntent[] extraIntents = intentList.toArray(
                new LabeledIntent[intentList.size()]);

        intent.setPackage("com.android.email");
        Intent openChooser = Intent.createChooser(intent,title);
        openChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        return openChooser;
    }

    public static Intent shareToSocialNetwork(PackageManager packageManager, String text, Uri imagePath){
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        Intent intent = new Intent();
        intent.setType("plain/text");
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage("com.android.email");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        Intent wechatIntent = new Intent();
        wechatIntent.setComponent(new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareImgUI"));
        wechatIntent.putExtra(Intent.EXTRA_TEXT, text);
        wechatIntent.setAction(Intent.ACTION_SEND);
        wechatIntent.setType("plain/text");
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(wechatIntent, 0);
        if(resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo ri = resolveInfos.get(0);
            String packageName = ri.activityInfo.packageName;
            //add this intent to the list
            intentList.add(new LabeledIntent(wechatIntent, packageName,
                    ri.loadLabel(packageManager), ri.icon));
        }

        Intent friendIntent = new Intent();
        friendIntent.setComponent(new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        friendIntent.putExtra(Intent.EXTRA_TEXT, text);
        friendIntent.putExtra("Kdescription", text);
        friendIntent.setAction(Intent.ACTION_SEND);
        friendIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        friendIntent.setType("image/png");
        resolveInfos = packageManager.queryIntentActivities(friendIntent, 0);
        if(resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo ri = resolveInfos.get(0);
            String packageName = ri.activityInfo.packageName;
            //add this intent to the list
            intentList.add(new LabeledIntent(friendIntent, packageName,
                    "朋友圈", ri.icon));
        }

        Intent messenger = new Intent();
        messenger.setPackage("com.facebook.orca");
        messenger.setAction(Intent.ACTION_SEND);
        messenger.setType("plain/text");

        resolveInfos = packageManager.queryIntentActivities(messenger, 0);
        if(resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo ri = resolveInfos.get(0);
            String packageName = ri.activityInfo.packageName;
            //add this intent to the list
            intentList.add(new LabeledIntent(messenger, packageName,
                    ri.loadLabel(packageManager), ri.icon));

        }

        Intent openChooser = Intent.createChooser(intent,"Share To");
        openChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new LabeledIntent[0]));
        return openChooser;
    }
}
