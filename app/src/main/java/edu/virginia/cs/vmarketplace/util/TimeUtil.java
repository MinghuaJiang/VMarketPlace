package edu.virginia.cs.vmarketplace.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cutehuazai on 12/24/17.
 */

public class TimeUtil {
    public static String formatYYYYMMDDhhmmss(Date date){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }

    public static String getRelativeTimeFromNow(String time){
        System.out.println(time);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time);
            Date now = new Date();
            long diff = now.getTime() - date.getTime();
            System.out.println(diff);
            if(diff < 30 * 60 * 1000){
                return "just now";
            }else if(diff < 60 * 60 * 1000){
                return "30 min ago";
            }else if(diff < 24 * 60 * 60 * 1000){
                int index = 1;
                for(int i = 1;i <= 24;i++){
                    if(diff < i * 60 * 60 * 1000){
                        index = i - 1;
                        break;
                    }
                }
                if(index == 1){
                    return "1 hour ago";
                }
                return index + " hours ago";
            }else if(diff < 90 * 24 * 60 * 60 * 1000L){
                int index = 1;
                for(int i = 1;i <= 90;i++){
                    if(diff < i * 24 * 60 * 60 * 1000L){
                        index = i - 1;
                        break;
                    }
                }
                if(index == 1){
                    return "1 day ago";
                }
                return index + " days ago";
            }else{
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
