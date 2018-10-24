package cityxpress.cbt.com.cityxpress.activitys.model;

public class TrainInfoModel {
    String date,atime,dtime,latemin,station;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
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

    public TrainInfoModel(String date, String atime, String dtime, String latemin, String station) {
        this.date = date;
        this.atime = atime;
        this.dtime = dtime;
        this.latemin = latemin;
        this.station = station;
    }
}
