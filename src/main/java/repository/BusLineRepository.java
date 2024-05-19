package repository;

import ENUMS.AreaCode;
import ENUMS.Status;
import databaseConnection.DatabaseUtil;
import model.*;
import service.DateFormatter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static repository.CompanyRepository.getCompanyFromResult;


public class BusLineRepository {
    public static boolean createBusLine(String lineId,Status status,LocalDateTime startTime, LocalDateTime endTime, User creator,LocalDateTime creationTime, String startLocation, String endLocation, Company company, Bus bus) {
        BusLine creationBusLine = new BusLine(lineId,status,startTime, endTime, creator,creationTime,null ,startLocation, endLocation, company, bus);
        String query = "INSERT INTO company_lines(line_id, status,start_time, end_time,creator_user_id, creation_time, start_location, end_location, company_assigned_id, bus_model_id) values(?,?,?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseUtil.getConnection();
        try {

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(UUID.randomUUID()));
            preparedStatement.setString(2, String.valueOf(Status.ACTIVE));
            preparedStatement.setString(3, DateFormatter.SQLformat(creationBusLine.getStartTime()));
            preparedStatement.setString(4, DateFormatter.SQLformat(creationBusLine.getEndTime()));
            preparedStatement.setString(5, creationBusLine.getCreator().getId());
            preparedStatement.setString(6, DateFormatter.SQLformat(creationTime));
            preparedStatement.setString(7, creationBusLine.getStartLocation());
            preparedStatement.setString(8, creationBusLine.getEndLocation());
            preparedStatement.setString(9, creationBusLine.getCompanyAssigned().getCompanyId());
            preparedStatement.setString(10, creationBusLine.getBusModel().getBusId());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating bus line: " + e.getMessage());
        }
    }

    public static BusLine getBusLineFromId(String id){
        Connection conn = DatabaseUtil.getConnection();
        String query = "Select * from company_lines where line_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id.trim());
            stmt.execute();
            ResultSet result = stmt.getResultSet();

            if (!result.next()) {
                throw new RuntimeException("No company found with ID: " + id);
            }
            return getBusLineFromResult(result);

        }
        catch (SQLException se){
            throw new RuntimeException(se.getMessage());
        }
    }




    public static List<BusLine> getSpecificCompanyBusses(String from) {
        String query = "SELECT * FROM company_buses " +
                "INNER JOIN companies ON companies.company_id = company_buses.company_id " +
                "INNER JOIN buses ON buses.bus_id = company_buses.bus_id" +
                " WHERE companies.area_code = ?" +
                " AND  buses.bus_id NOT IN(SELECT bus_model_id FROM company_lines);";

        List<BusLine> busLines = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, from.trim());
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Bus bus = BusRepository.getBusFromResult(results);
                Company company = CompanyRepository.getCompanyFromResult(results);
                BusLine busLine = new BusLine(bus, company);
                busLines.add(busLine);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return busLines;

    }



        public static void insertAddStops(String company_id, String bus_id, HashMap<String, LocalDateTime> stops){

        String query = "INSERT INTO bus_stops(company_id,bus_id,stop_key,stop_value) VALUES(?,?,?,?)";

        Connection con  = DatabaseUtil.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement(query);
            for (Map.Entry<String, LocalDateTime> entry : stops.entrySet()) {
                ps.setString(1, company_id);
                ps.setString(2, bus_id);
                ps.setString(3, entry.getKey());
                ps.setString(4, entry.getValue().toString());
                System.out.println("Executed For Each Stop");

                ps.execute();
            }
            ps.close();
        }catch (SQLException e){
            System.out.println(e);
        }

    }



    public static List<BusLine> searchBusLine(String location){
        List<BusLine> busLines =new ArrayList<>();
        String query = "SELECT * FROM company_lines WHERE start_location=?";
        Connection conn = DatabaseUtil.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, location);
            ResultSet results = ps.executeQuery();
            while (results.next()) {

                  busLines.add( getBusLineFromResult(results));
            }


        }catch (SQLException e){
            System.out.println(e);
        }


        return busLines;


    }

//    public static BusLine getAllLines(){
//        String query ="SELECT * FROM company_lines";
//        Connection conn = DatabaseUtil.getConnection();
//        try{
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet results = preparedStatement.executeQuery();
//
//            while(results.next()) {
//                System.out.println("Linjat:"+results);
//            }
//            return getBusLineFromResult(results);
//
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//        return null;
//    }

    public static AreaCodeStatistic getAreaCodeStatistics(AreaCode startLocation, int weeks) {
        AreaCodeStatistic statistic = null;
        String query = "{CALL GetStatisticsByLocationAndTime(?, ?)}";
        Connection conn = DatabaseUtil.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, startLocation.name());
            stmt.setInt(2, weeks);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String totalLines = rs.getString("totalLines");
                String successRate = rs.getString("successRate");
                String hoursTraveled = rs.getString("hoursTraveled");

                statistic = new AreaCodeStatistic(startLocation, totalLines, successRate, hoursTraveled);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return statistic;
    }

public static List<BusLine> getLineData(String cName) throws RuntimeException{
    List<BusLine> busLines = new ArrayList<>();
    Company companyAssigned = CompanyRepository.searchCompany(cName);
    Connection conn = DatabaseUtil.getConnection();
    String query = "SELECT * FROM company_lines where company_assigned_id = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1,companyAssigned.getCompanyId());
        System.out.println(companyAssigned.getCompanyId());
        stmt.execute();
        ResultSet results = stmt.getResultSet();
        while (results.next()){
            busLines.add(BusLineRepository.getBusLineFromResult(results));
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    System.out.println("i came");
    System.out.println(busLines.size());
    return busLines;
}




    public static BusLine getBusLineFromResult(ResultSet result) throws SQLException {
        try {
            String line_id = result.getString("line_id");
            Status status = Status.valueOf(result.getString("status"));
            LocalDateTime start_time = result.getTimestamp("start_time").toLocalDateTime();
            LocalDateTime end_time = result.getTimestamp("end_time").toLocalDateTime();
            User creator = UserRepository.getById(result.getString("creator_user_id"));
            LocalDateTime creation_time = result.getTimestamp("creation_time").toLocalDateTime();
            String start_location = result.getString("start_location");
            String end_location = result.getString("end_location");
            Company company_assigned_id = CompanyRepository.getCompanyFromId(result.getString("company_assigned_id"));
            Bus bus = BusRepository.getBusById(result.getString("bus_id"));
            return new BusLine(line_id, status, start_time, end_time, creator, creation_time, null,start_location, end_location, company_assigned_id, bus);
        }catch (Exception e){
            System.out.println(e);
            return null;

        }
    }
}




