package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfileItem {
    private int mProfileTypeImageResourceId;
    private ProfileType profileType;
    private int count;

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

    public ProfileItem(int imageResourceId, ProfileType profileType, int count){
        this.mProfileTypeImageResourceId = imageResourceId;
        this.profileType = profileType;
        this.count = count;
    }

    public int getmProfileTypeImageResourceId() {
        return mProfileTypeImageResourceId;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public int getCount() {
        return count;
    }
}
