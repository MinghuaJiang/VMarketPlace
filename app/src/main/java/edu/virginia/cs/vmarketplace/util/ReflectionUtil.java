package edu.virginia.cs.vmarketplace.util;

/**
 * Created by mijian on 3/3/2018.
 */

public class ReflectionUtil {
    public static Object getConstant(Class clazz, String name){
        try {
            return clazz.getField(name).get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
