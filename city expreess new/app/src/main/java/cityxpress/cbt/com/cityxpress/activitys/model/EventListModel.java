package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 24-Aug-18.
 */

public class EventListModel  {
    String EventID;
    String NameOfEvent;
    String EventDate;

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getNameOfEvent() {
        return NameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        NameOfEvent = nameOfEvent;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getImageUpload() {
        return ImageUpload;
    }

    public void setImageUpload(String imageUpload) {
        ImageUpload = imageUpload;
    }

    public EventListModel(String eventID, String nameOfEvent, String eventDate, String imageUpload) {
        EventID = eventID;
        NameOfEvent = nameOfEvent;
        EventDate = eventDate;
        ImageUpload = imageUpload;
    }

    String ImageUpload ;

}
