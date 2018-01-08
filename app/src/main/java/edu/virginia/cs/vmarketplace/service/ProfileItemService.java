package edu.virginia.cs.vmarketplace.service;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.service.dao.ProfileItemDao;

/**
 * Created by cutehuazai on 1/4/18.
 */

public class ProfileItemService {
    public static ProfileItemService service = new ProfileItemService();

    public static ProfileItemService getInstance(){
        return service;
    }

    private ProfileItemDao dao;

    private ProfileItemService(){
        dao = new ProfileItemDao();
    }

    public List<ProfileItem> getProfileItems(){
        return dao.getProfileItems();
    }

    public List<ProfileItem> getSettingItems(){
        return dao.getSettingItems();
    }
}
