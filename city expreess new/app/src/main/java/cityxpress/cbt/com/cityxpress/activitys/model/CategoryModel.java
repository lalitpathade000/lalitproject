package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 17-Aug-18.
 */

public class CategoryModel {
    public CategoryModel(String categoryid, String categoryName) {
        this.categoryid = categoryid;
        this.categoryName = categoryName;
    }

    String categoryid;

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    String categoryName;
}
