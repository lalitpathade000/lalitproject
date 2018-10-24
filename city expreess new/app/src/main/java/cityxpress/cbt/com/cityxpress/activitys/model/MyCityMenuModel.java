package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 14-Aug-18.
 */

public class MyCityMenuModel {
    String id,menuname,menuimage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenuimage() {
        return menuimage;
    }

    public void setMenuimage(String menuimage) {
        this.menuimage = menuimage;
    }

    public MyCityMenuModel(String id, String menuname, String menuimage) {
        this.id = id;
        this.menuname = menuname;
        this.menuimage = menuimage;
    }
}
