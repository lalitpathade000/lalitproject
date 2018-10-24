
package cityxpress.cbt.com.cityxpress.activitys.GSON;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainStatusGsonPojo {

    private Integer responseCode;
    private Train train;
    private CurrentStation currentStation;
    private String startDate;
    private Integer debit;
    private String position;
    private List<Route> route = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public CurrentStation getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(CurrentStation currentStation) {
        this.currentStation = currentStation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this, TrainStatusGsonPojo.class);
        return  json;
    }

}
