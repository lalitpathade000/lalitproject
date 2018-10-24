package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 15-Aug-18.
 */

public class SubCategoryPojo {
    String submenuid, submenuname, submenuimg;

    public String getsubmenuid() {
        return submenuid;
    }

    public void setsubmenuid(String submenuid) {
        this.submenuid = submenuid;
    }

    public String getsubmenuname() {
        return submenuname;
    }

    public void setsubmenuname(String submenuname) {
        this.submenuname = submenuname;
    }

    public String getsubmenuimg() {
        return submenuimg;
    }

    public void setsubmenuimg(String submenuimg) {
        this.submenuimg = submenuimg;
    }

    public SubCategoryPojo(String submenuid, String submenuname, String submenuimg) {

        this.submenuid = submenuid;
        this.submenuname = submenuname;
        this.submenuimg = submenuimg;
    }
}
