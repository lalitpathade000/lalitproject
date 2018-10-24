package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 18-Aug-18.
 */

public class CityAreaModel {
    public String getCityAreaID() {
        return cityAreaID;
    }

    public void setCityAreaID(String cityAreaID) {
        this.cityAreaID = cityAreaID;
    }

    public String getCityAreaName() {
        return cityAreaName;
    }

    public void setCityAreaName(String cityAreaName) {
        this.cityAreaName = cityAreaName;
    }

    String cityAreaID,cityAreaName;

    public CityAreaModel(String cityAreaID, String cityAreaName) {
        this.cityAreaID = cityAreaID;
        this.cityAreaName = cityAreaName;
    }
}
