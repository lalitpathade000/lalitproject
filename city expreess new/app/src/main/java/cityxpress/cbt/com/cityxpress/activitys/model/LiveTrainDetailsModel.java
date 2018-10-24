package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 06-Sep-18.
 */

public class LiveTrainDetailsModel {
    String date,actTime,depart,latemin,station;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getLatemin() {
        return latemin;
    }

    public void setLatemin(String latemin) {
        this.latemin = latemin;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public LiveTrainDetailsModel(String date, String actTime, String depart, String latemin, String station) {

        this.date = date;
        this.actTime = actTime;
        this.depart = depart;
        this.latemin = latemin;
        this.station = station;
    }
}
