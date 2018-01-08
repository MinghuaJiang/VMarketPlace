package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfileItem {
    private int mProfileTypeImageResourceId;
    private String profileType;
    private int count;
    private boolean haveSubMenu;

    public enum ProfileType{
        PUBLISH_BY_ME{
            @Override
            public String getMessage() {
                return "Published By Me";
            }
        },
        SOLD_BY_ME{
            @Override
            public String getMessage() {
                return "Sold By Me";
            }
        },
        BOUGHT_BY_ME{
            @Override
            public String getMessage() {
                return "Bought By Me";
            }
        },
        ADDED_TO_FAVIORITE{
            @Override
            public String getMessage() {
                return "Added To Favorite";
            }
        },

        SETTING{
            @Override
            public String getMessage() {
                return "Settings";
            }
        };

        public abstract String getMessage();
    }

    public enum SettingType{
        PROFILE{
            @Override
            public String getMessage() {
                return "Profile Setting";
            }
        },
        SHARE{
            @Override
            public String getMessage() {
                return "Share This App";
            }
        },
        ABOUT{
            @Override
            public String getMessage() {
                return "About This App";
            }
        },
        CACHE_CLEANUP{
            @Override
            public String getMessage() {
                return "Cache Cleanup";
            }
        };

        public abstract String getMessage();
    }

    public ProfileItem(int imageResourceId, String profileType, int count, boolean haveSubMenu){
        this.mProfileTypeImageResourceId = imageResourceId;
        this.profileType = profileType;
        this.count = count;
        this.haveSubMenu = haveSubMenu;
    }

    public int getmProfileTypeImageResourceId() {
        return mProfileTypeImageResourceId;
    }

    public String getProfileType() {
        return profileType;
    }

    public int getCount() {
        return count;
    }

    public void setmProfileTypeImageResourceId(int mProfileTypeImageResourceId) {
        this.mProfileTypeImageResourceId = mProfileTypeImageResourceId;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHaveSubMenu() {
        return haveSubMenu;
    }
}
