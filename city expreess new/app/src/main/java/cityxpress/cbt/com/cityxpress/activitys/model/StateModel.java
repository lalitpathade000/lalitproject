package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 17-Aug-18.
 */

public class StateModel {
    String stateID,stateName;

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public StateModel(String stateID, String stateName) {

        this.stateID = stateID;
        this.stateName = stateName;
    }
}
