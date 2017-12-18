package edu.virginia.cs.vmarketplace.util;

/**
 * Created by cutehuazai on 12/14/17.
 */

public class LocationUtil {

    public static String getCityAndZipCode(String address){
        return address.substring(address.indexOf(",") + 1
                , address.lastIndexOf("美"));
    }
}
