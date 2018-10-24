package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 18-Aug-18.
 */

public class CityServiceModel  {
    String cityServiceID;

    public String getCityServiceID() {
        return cityServiceID;
    }

    public void setCityServiceID(String cityServiceID) {
        this.cityServiceID = cityServiceID;
    }

    public String getCityServiceName() {
        return cityServiceName;
    }

    public void setCityServiceName(String cityServiceName) {
        this.cityServiceName = cityServiceName;
    }

    String cityServiceName;

    public CityServiceModel(String cityServiceID, String cityServiceName) {
        this.cityServiceID = cityServiceID;
        this.cityServiceName = cityServiceName;
    }
}
