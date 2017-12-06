package edu.virginia.cs.vmarketplace.service.login;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class AppContextManager {
    private String currentUser;
    private Map<String, AppContext> userMap;

    private static AppContextManager manager = new AppContextManager();

    public static AppContextManager getContextManager(){
        return manager;
    }

    private AppContextManager(){
        userMap = new HashMap<>();
    }

    public AppContext getAppContext(){
        AppContext context = userMap.get(currentUser);
        if(context == null){
            context = new AppContext();
            context.loadContext(currentUser);
            userMap.put(currentUser, context);
        }
        return userMap.get(currentUser);
    }


    public void loadCurrentUser(String user){
        currentUser = user;
        getAppContext();
    }
}
