package service;

import ENUMS.Status;
import model.BusLine;
import model.filter.BusLineFilter;
import repository.BusLineRepository;

import java.time.LocalDateTime;
import java.util.List;
//import repository.BusLineRepository;

public class BusLineService {


    public static List getCompany(String from, String to, LocalDateTime timeFrom, LocalDateTime timeTo) {
        return BusLineRepository.getSpecificCompanyBusses(from, to, timeFrom, timeTo);
    }




    public static boolean insertBusLine(BusLine busLine) {

        return BusLineRepository.createBusLine(busLine.getLineId(),busLine.getStatus(),busLine.getStartTime(),busLine.getEndTime(),busLine.getCreator(),LocalDateTime.now(),busLine.getStartLocation(),busLine.getEndLocation(),busLine.getCompanyAssigned(),busLine.getBusModel());

    }


    public static List<BusLine> getBusLine(String from) {
      return   BusLineRepository.searchBusLine(from);
    }



    public static void insertAddStop(String line_id, List<String> stops ) {

        BusLineRepository.insertAddStops(line_id,stops);

    }

    public static List<BusLine> getAllBusLines(BusLineFilter filter) {
        return BusLineRepository.getByFilter(filter);
    }


//    public static List<BusLine> getCompanyBusLines(CompanyBusLineFilter filter) {
//        return BusLineRepository.getByFilter(filter);
//    }

    public static void updateBusLineStatus(BusLine busLine, Status status){
        BusLineRepository.updateBusLineStatus(busLine, status);
    }




}
