package model;

import ENUMS.ActivityStatus;
import ENUMS.AreaCode;
import ENUMS.ActivityStatus;
import INTERFACES.Identifiable;

import java.util.ArrayList;

public class Company implements Identifiable {
    private String companyId;
    private String companyName;
    private String description;
    private AreaCode areaCode;
    private ActivityStatus companyStatus;
    private ArrayList<Bus> companyBuses;


    public Company(String companyName, AreaCode areaCode, String Description) {
        this.companyId = generateId();
        this.companyName = companyName;
        this.areaCode = areaCode;
        this.companyStatus = ActivityStatus.ACTIVE;
        this.companyBuses = new ArrayList<>();
        this.description = Description;
    }



    //  ################  GETTER SETTER   ################
    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public AreaCode getAreaCode() {
        return areaCode;
    }

    public ActivityStatus getCompanyStatus() {
        return companyStatus;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Bus> getCompanyBuses() {
        return companyBuses;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAreaCode(AreaCode areaCode) {
        this.areaCode = areaCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompanyStatus(ActivityStatus companyStatus) {
        this.companyStatus = companyStatus;
    }

    //  ################  end   ################




}
