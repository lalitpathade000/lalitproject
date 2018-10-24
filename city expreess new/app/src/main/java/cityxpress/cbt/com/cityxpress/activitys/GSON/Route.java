
package cityxpress.cbt.com.cityxpress.activitys.GSON;

import java.util.HashMap;
import java.util.Map;

public class Route {

    private String actarrDate;
    private Station station;
    private String actarr;
    private String scharrDate;
    private Integer day;
    private Integer distance;
    private Boolean hasArrived;
    private String actdep;
    private String status;
    private Boolean hasDeparted;
    private String scharr;
    private Integer latemin;
    private String schdep;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getActarrDate() {
        return actarrDate;
    }

    public void setActarrDate(String actarrDate) {
        this.actarrDate = actarrDate;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getActarr() {
        return actarr;
    }

    public void setActarr(String actarr) {
        this.actarr = actarr;
    }

    public String getScharrDate() {
        return scharrDate;
    }

    public void setScharrDate(String scharrDate) {
        this.scharrDate = scharrDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Boolean getHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(Boolean hasArrived) {
        this.hasArrived = hasArrived;
    }

    public String getActdep() {
        return actdep;
    }

    public void setActdep(String actdep) {
        this.actdep = actdep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getHasDeparted() {
        return hasDeparted;
    }

    public void setHasDeparted(Boolean hasDeparted) {
        this.hasDeparted = hasDeparted;
    }

    public String getScharr() {
        return scharr;
    }

    public void setScharr(String scharr) {
        this.scharr = scharr;
    }

    public Integer getLatemin() {
        return latemin;
    }

    public void setLatemin(Integer latemin) {
        this.latemin = latemin;
    }

    public String getSchdep() {
        return schdep;
    }

    public void setSchdep(String schdep) {
        this.schdep = schdep;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
