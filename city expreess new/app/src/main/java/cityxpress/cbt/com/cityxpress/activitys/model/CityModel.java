package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 17-Aug-18.
 */

public class CityModel {
    String cityID,cityName;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public CityModel(String cityID, String cityName) {

        this.cityID = cityID;
        this.cityName = cityName;
    }
}
