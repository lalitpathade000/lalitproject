package cityxpress.cbt.com.cityxpress.activitys.model;

/**
 * Created by admin on 24-Aug-18.
 */

public class EventModel {
    public String getNameOfEvent() {
        return NameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        NameOfEvent = nameOfEvent;
    }

    public String getOrganiser() {
        return Organiser;
    }

    public void setOrganiser(String organiser) {
        Organiser = organiser;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getOrganiserContactNo() {
        return OrganiserContactNo;
    }

    public void setOrganiserContactNo(String organiserContactNo) {
        OrganiserContactNo = organiserContactNo;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public String getImageUpload() {
        return ImageUpload;
    }

    public void setImageUpload(String imageUpload) {
        ImageUpload = imageUpload;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getCharges() {
        return Charges;
    }

    public void setCharges(String charges) {
        Charges = charges;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public EventModel( String eventID,String nameOfEvent, String organiser, String venue, String organiserContactNo, String eventDate, String eventTime, String imageUpload, String charges, String description) {

        NameOfEvent = nameOfEvent;
        Organiser = organiser;
        Venue = venue;
        OrganiserContactNo = organiserContactNo;
        EventDate = eventDate;
        EventTime = eventTime;
        ImageUpload = imageUpload;
        EventID = eventID;
        Charges = charges;
        Description = description;
    }

    String NameOfEvent, Organiser, Venue, OrganiserContactNo, EventDate, EventTime, ImageUpload, EventID, Charges, Description;

}