package model.filter;

import java.time.LocalDateTime;

abstract  class Filter {
    public abstract String buildQuery();
}

public class BusLineFilter extends Filter{

    private String from;
    private String to;
    private String status;
    private String company;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String companyName;
    private String lineId;
    private LocalDateTime date;



    private int page;
    private int size;

    public BusLineFilter(String from, String to,String status,String company,LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.from = from;
        this.to = to;
        this.status =status;
        this.company =company;
        this.dateFrom =dateFrom;
        this.dateTo= dateTo;
    }


        public BusLineFilter(String company,String lineId, LocalDateTime date) {

            this.company = company;
            this.lineId = lineId;
            this.date = date;

        }

    public String buildQuery(){
        String query = "";


        if(this.from != null){
            query += " AND start_location like '"+ this.from +"%'";
        }

        if(this.to != null){
            query += " AND end_location like '" + this.to +"%'";
        }

        if(this.status != null){
            query += " AND status = '" + this.status +"'";
        }
        if(this.company != null){
            query += " AND companies.company_name LIKE '" + this.company +"%'";
        }

        if(this.dateFrom != null){
            query += " AND start_time >= '"+ this.dateFrom +"'";
        }

        if(this.dateTo != null){
            query += " AND end_time <= '" + this.dateTo +"' order by end_time DESC ";
        }

        if(this.lineId != null){
                query += " AND line_id like '" + this.lineId +"%'";
        }
        if(this.date != null){
                query += " AND start_time >= '" + this.date +"'";
        }


        return query;
    }








}