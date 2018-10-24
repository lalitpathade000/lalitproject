package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 8/13/2018.
 */

public class TrainRoutModel {

    String TrainATime;

    public String getTrainATime() {
        return TrainATime;
    }

    public void setTrainATime(String trainATime) {
        TrainATime = trainATime;
    }

    public String getTrainschdep() {
        return Trainschdep;
    }

    public void setTrainschdep(String trainschdep) {
        Trainschdep = trainschdep;
    }

    public String getTraindistance() {
        return Traindistance;
    }

    public void setTraindistance(String traindistance) {
        Traindistance = traindistance;
    }

    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public String getTrainLat() {
        return TrainLat;
    }

    public void setTrainLat(String trainLat) {
        TrainLat = trainLat;
    }

    public String getTrainLon() {
        return TrainLon;
    }

    public void setTrainLon(String trainLon) {
        TrainLon = trainLon;
    }

    String Trainschdep;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TrainRoutModel(String trainATime, String trainschdep, String traindistance, String trainName, String trainLat, String trainLon, int id, String code) {
        TrainATime = trainATime;
        Trainschdep = trainschdep;
        Traindistance = traindistance;
        TrainName = trainName;
        TrainLat = trainLat;
        TrainLon = trainLon;
        this.id=id;
        this.code=code;

    }

    String Traindistance;
    String TrainName;
    String TrainLat;
    String TrainLon;
    String code;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
