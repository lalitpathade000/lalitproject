package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 28-Aug-18.
 */

public class LatLongModel {
    String lat;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public LatLongModel(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    String lon;
}
