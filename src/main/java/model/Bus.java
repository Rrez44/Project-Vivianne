package model;

import ENUMS.ActivityStatus;
import ENUMS.BusType;
import ENUMS.ComfortRating;
import ENUMS.Status;
import INTERFACES.Identifiable;

public class Bus implements Identifiable {

    private String busId;
    private String busModel;
    private String vin;
    private int passangerCapacity;
    private BusType busType;
    private ActivityStatus activityStatus;
    private ComfortRating comfortRating;

    public Bus(String busModel, String vin, int passangerCapacity, BusType busType, ActivityStatus activityStatus, ComfortRating comfortRating) {
        this.busId = generateId();
        this.busModel = busModel;
        this.vin = vin;
        this.passangerCapacity = passangerCapacity;
        this.busType = busType;
        this.activityStatus = activityStatus;
        this.comfortRating = comfortRating;
    }

    public Bus(String busId, String busModel, String vin, int passangerCapacity, BusType busType, ActivityStatus activityStatus, ComfortRating comfortRating) {
        this.busId = busId;
        this.busModel = busModel;
        this.vin = vin;
        this.passangerCapacity = passangerCapacity;
        this.busType = busType;
        this.activityStatus = activityStatus;
        this.comfortRating = comfortRating;
    }

    public String getBusId() {
        return busId;
    }

    public String getBusModel() {
        return busModel;
    }

    public String getVin() {
        return vin;
    }

    public int getPassangerCapacity() {
        return passangerCapacity;
    }

    public BusType getBusType() {
        return busType;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public ComfortRating getComfortRating() {
        return comfortRating;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public void setComfortRating(ComfortRating comfortRating) {
        this.comfortRating = comfortRating;
    }
}
