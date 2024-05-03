package model;

import ENUMS.Status;
import INTERFACES.Identifiable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BusLine implements Identifiable {
    private String lineId;
    private Status status; // ENUMERATOR LOCATED IN ENUMS/STATUS
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User creator;
    private LocalDateTime creationTime;
    private HashMap<String, LocalDateTime> stops;
    private Company companyAssigned;
    private Bus busModel;
    private int passengerCapacity;
    private String startLocation;
    private String endLocation;


    public BusLine(LocalDateTime startTime, LocalDateTime endTime, User creator,  int passengerCapacity, String startLocation,  String endLocation, Company company, Bus bus) {
        this.lineId = generateId();
        this.status = Status.ACTIVE;
        this.startTime = startTime;
        this.endTime = endTime;
        this.creator = creator;
        this.creationTime = LocalDateTime.now();
        this.stops = new HashMap<>();
        this.passengerCapacity = passengerCapacity;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.companyAssigned = company;
        this.busModel = bus;
    }


    public String getLineId() {
        return lineId;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public User getCreator() {
        return creator;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public HashMap<String, LocalDateTime> getStops() {
        return stops;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public String getStartLocation() {return startLocation;}

    public String getEndLocation() {return endLocation;}

    public Company getCompanyAssigned() {
        return companyAssigned;
    }

    public Bus getBusModel() {
        return busModel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void addStop(String coordinates, LocalDateTime stopTime){
        stops.put(coordinates,stopTime);
    }


}
