package service;

import ENUMS.ActivityStatus;
import ENUMS.BusType;
import ENUMS.ComfortRating;
import model.Bus;
import model.Company;
import repository.BusRepository;

import java.util.List;

public class BusService {



    public static boolean createBus(String busId, String busModel, String vin, int passengerCapacity, BusType busType, ActivityStatus activityStatus, ComfortRating comfortRating, Company company){
        return BusRepository.createBus(busId,busModel,vin,passengerCapacity,busType,activityStatus,comfortRating,company);
    }

    public static List<model.Bus> loadInitial(model.Company cmp){
        return BusRepository.loadInitial(cmp);
    }

    public static void updateBus(model.Bus bus){
        BusRepository.updateBus(bus);
    }
    public static model.Bus getBusByVin(String vin){
        return BusRepository.getBusByVin(vin);
    }
    public static Bus getBusByModelNumer(String modelNumber){
        return BusRepository.getBusByModelNumer(modelNumber);
    }

    public static  List<model.Bus> getBusList(model.filter.Bus filter){

        return BusRepository.getByFilter(filter);


    }


}
