package model.filter;

import java.time.LocalDateTime;

abstract  class FilterBus {
    public abstract String buildQuery();
}


public class Bus extends FilterBus{



    private String busVin;


    private int page;
    private int size;

    public Bus(String busVin) {

        this.busVin = busVin;


    }



    public String buildQuery(){
        String query = "";


        if(this.busVin != null){
            query += " AND vin like '" + this.busVin +"%'";
        }


        return query;
    }








}