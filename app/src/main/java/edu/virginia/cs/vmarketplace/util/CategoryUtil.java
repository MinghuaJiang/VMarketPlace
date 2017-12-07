package edu.virginia.cs.vmarketplace.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cutehuazai on 12/7/17.
 */

public class CategoryUtil {
    public static List<String> getSubCategory(String category){
        List<String> result = new ArrayList();
        if(category.equals("Second Hand")) {
            result.add("Appliance");
            result.add("Bicycle");
            result.add("Book");
            result.add("Car");
            result.add("PC/Laptop");
            result.add("Digital Device");
            result.add("Furniture");
            result.add("Game Device");
            result.add("Kitchenware");
            result.add("Other");
        }else if(category.equals("Sublease")){
            result.add("Apartment");
            result.add("House");
        }else if(category.equals("Ride")){
            result.add("One-way Trip");
            result.add("Round Trip");
        }

        return result;
    }
}
