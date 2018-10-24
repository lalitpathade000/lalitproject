package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 17-Aug-18.
 */

public class SubCategory {
    public SubCategory(String subCategoryID, String subCategoryName) {
        this.subCategoryID = subCategoryID;
        this.subCategoryName = subCategoryName;
    }

    String subCategoryID;

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    String subCategoryName;

}
