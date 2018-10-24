package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 16-Aug-18.
 */

public class BussinessListModel {
    String businessRegistrationID;

    public String getBusinessRegistrationID() {
        return businessRegistrationID;
    }

    public void setBusinessRegistrationID(String businessRegistrationID) {
        this.businessRegistrationID = businessRegistrationID;
    }

    public String getBusinesspersonName() {
        return businesspersonName;
    }

    public void setBusinesspersonName(String businesspersonName) {
        this.businesspersonName = businesspersonName;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessmobileNo() {
        return businessmobileNo;
    }

    public void setBusinessmobileNo(String businessmobileNo) {
        this.businessmobileNo = businessmobileNo;
    }

    public String getBusinessemailId() {
        return businessemailId;
    }

    public void setBusinessemailId(String businessemailId) {
        this.businessemailId = businessemailId;
    }

    public BussinessListModel(String businessRegistrationID, String businesspersonName, String businessImage, String businessName, String businessmobileNo, String businessemailId) {

        this.businessRegistrationID = businessRegistrationID;
        this.businesspersonName = businesspersonName;
        this.businessImage = businessImage;
        this.businessName = businessName;
        this.businessmobileNo = businessmobileNo;
        this.businessemailId = businessemailId;
    }

    String businesspersonName;
    String businessImage;
    String businessName;
    String businessmobileNo;
    String businessemailId;
}
