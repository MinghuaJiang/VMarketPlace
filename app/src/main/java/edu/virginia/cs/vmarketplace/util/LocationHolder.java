package edu.virginia.cs.vmarketplace.util;

/**
 * Created by cutehuazai on 12/26/17.
 */

public class LocationHolder {
    private static LocationHolder holder = new LocationHolder();

    private double longitude;
    private double latitude;
    private String location;

    private LocationHolder(){

    }

    public static LocationHolder getInstance(){
        return holder;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
