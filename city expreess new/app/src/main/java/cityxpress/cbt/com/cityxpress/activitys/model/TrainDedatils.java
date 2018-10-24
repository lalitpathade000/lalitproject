package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 8/13/2018.
 */

public class TrainDedatils {
    String TrainNo;

    public String getTrainNo() {
        return TrainNo;
    }

    public void setTrainNo(String trainNo) {
        TrainNo = trainNo;
    }

    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public String getTrainDTime() {
        return TrainDTime;
    }

    public void setTrainDTime(String trainDTime) {
        TrainDTime = trainDTime;
    }

    public String getTrainATime() {
        return TrainATime;
    }

    public void setTrainATime(String trainATime) {
        TrainATime = trainATime;
    }

    public String getTrainTravel() {
        return TrainTravel;
    }

    public void setTrainTravel(String trainTravel) {
        TrainTravel = trainTravel;
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

    public String getTrainTOLat() {
        return TrainTOLat;
    }

    public void setTrainTOLat(String trainTOLat) {
        TrainTOLat = trainTOLat;
    }

    public String getTrainTOLon() {
        return TrainTOLon;
    }

    public void setTrainTOLon(String trainTOLon) {
        TrainTOLon = trainTOLon;
    }

    public TrainDedatils(String trainNo, String trainName, String trainDTime, String trainATime, String trainTravel, String trainLat, String trainLon, String trainTOLat, String trainTOLon) {
        TrainNo = trainNo;
        TrainName = trainName;
        TrainDTime = trainDTime;
        TrainATime = trainATime;
        TrainTravel = trainTravel;
        TrainLat = trainLat;
        TrainLon = trainLon;
        TrainTOLat = trainTOLat;
        TrainTOLon = trainTOLon;
    }

    String TrainName;
    String TrainDTime;
    String TrainATime;
    String TrainTravel;
    String TrainLat;
    String TrainLon;
    String TrainTOLat;
    String TrainTOLon;
}
