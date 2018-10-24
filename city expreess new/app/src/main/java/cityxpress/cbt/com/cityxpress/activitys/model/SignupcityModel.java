package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 8/8/2018.
 */

public class SignupcityModel
{
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    String city;

    public SignupcityModel(String id, String city) {
        this.id = id;
        this.city = city;
    }
}
