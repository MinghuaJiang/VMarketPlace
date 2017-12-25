package edu.virginia.cs.vmarketplace.util;

/**
 * Created by cutehuazai on 12/14/17.
 */

public class LocationUtil {

    public static String getAddressAndZipCode(String address){
        return address.substring(0, address.indexOf(",")) + address.substring(address.lastIndexOf("美") - 6
                , address.lastIndexOf("美"));
    }
}
