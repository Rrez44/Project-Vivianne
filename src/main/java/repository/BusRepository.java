package repository;

import ENUMS.ActivityStatus;
import ENUMS.BusType;
import ENUMS.ComfortRating;
import databaseConnection.DatabaseUtil;
import model.Bus;
import model.BusLine;
import model.Company;
import model.filter.BusLineFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {

    public static Bus getBusFromResult(ResultSet rs) throws SQLException {
        String busId = rs.getString("bus_id");
        String bus_model = rs.getString("bus_model");
        String VIN = rs.getString("vin");
        int pCapacity = rs.getInt("passenger_capacity");
        BusType busType = BusType.valueOf(rs.getString("bus_type"));
        ActivityStatus activityStatus = ActivityStatus.valueOf(rs.getString("activity_status"));
        ComfortRating comfortRating = ComfortRating.valueOf(rs.getString("comfort_rating"));
        return new Bus(busId, bus_model,VIN,pCapacity,busType,activityStatus, comfortRating);
    }

    public static boolean createBus(String busId,String busModel, String vin, int passengerCapacity, BusType busType, ActivityStatus activityStatus, ComfortRating comfortRating, Company company) {
        Bus creationBus = new Bus(busId,busModel, vin, passengerCapacity, busType, activityStatus, comfortRating);
        String query = "INSERT INTO buses(bus_id, bus_model, vin , passenger_capacity, bus_type, activity_status, comfort_rating) values (?,?,?,?,?,?,?)";
        String assignBusToCompany = "INSERT INTO company_buses(company_id, bus_id) values(?,?)"; // we also need to create the junction between bus and company as it is a required relation
        Connection conn = DatabaseUtil.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            PreparedStatement stmt2 = conn.prepareStatement(assignBusToCompany);
            stmt.setString(1, creationBus.getBusId());
            stmt.setString(2, creationBus.getBusModel());
            stmt.setString(3, creationBus.getVin());
            stmt.setInt(4, creationBus.getPassangerCapacity());
            stmt.setString(5, creationBus.getBusType().name());
            stmt.setString(6, creationBus.getActivityStatus().name());
            stmt.setString(7, creationBus.getComfortRating().name());
            stmt.execute();
            stmt.close();
            stmt2.setString(1, company.getCompanyId());
            stmt2.setString(2, creationBus.getBusId());
            stmt2.execute();
            stmt2.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting new bus: " + e.getMessage());
        }

    }

    public static List<Bus> loadInitial(Company company) {
        List<Bus> buses = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        String query = "select * from buses where bus_id in (SELECT bus_id from company_buses where company_id = ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, company.getCompanyId());
            stmt.execute();
            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                buses.add(getBusFromResult(results));
            }
            stmt.close();
            return buses;
        } catch (SQLException se) {
            throw new RuntimeException("Error loading buses: " + se.getMessage());
        }
    }
    public static void updateBus(model.Bus bus){
        Connection conn = DatabaseUtil.getConnection();
        String query = "UPDATE BUSES set activity_status = ? , comfort_rating = ? where bus_id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bus.getActivityStatus().name());
            stmt.setString(2, bus.getComfortRating().name());
            stmt.setString(3, bus.getBusId());
            stmt.execute();
            stmt.close();
        }catch (SQLException se){
            throw new RuntimeException("Error updating bus: " + se.getMessage());
        }
    }
    public static Bus getBusByVin(String vin){
        Connection conn = DatabaseUtil.getConnection();
        String query = "SELECT * FROM buses where vin = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, vin.trim());
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            if (result.next()){
                return getBusFromResult(result);
            }
            return null;
        }catch (SQLException se){
            throw new RuntimeException("Error loading bus "+ se.getMessage());
        }
    }
        public static Bus getBusById(String Id){
        Connection conn = DatabaseUtil.getConnection();
        String query = "SELECT * FROM buses where bus_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Id.trim());
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            if (result.next()){
                return getBusFromResult(result);
            }
            return null;
        }catch (SQLException se){
            throw new RuntimeException("Error loading bus "+ se.getMessage());
        }
    }

    public static Bus getBusByModelNumer(String modelNumber){
        Connection conn = DatabaseUtil.getConnection();
        String query = "SELECT * FROM buses where bus_model = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, modelNumber.trim());
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            if (result.next()){
                return getBusFromResult(result);
            }
            return null;
        }catch (SQLException se){
            throw new RuntimeException("Error loading bus "+ se.getMessage());
        }
    }


//    public static List<Bus> getByFilter(model.filter.Bus filter){
//        List<Bus> Bus = new ArrayList<>();
//        String query = "SELECT * FROM" +
//                " company_buses" +
//                " INNER JOIN companies ON companies.company_id = company_buses.company_id" +
//                " INNNER JOIN buses ON company.bus_id = buses.bus_id" +
//                 " WHERE 1 = 1 "; // Added space after 1 = 1
//        String filterQuery = filter.buildQuery();
//        query += filterQuery;
//        Connection conn = DatabaseUtil.getConnection();
//        try (
//                PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//
//            }
//            return Bus;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static List<Bus> getByFilter(model.filter.Bus filter) {
        List<Bus> busList = new ArrayList<>();
        String query = "SELECT * FROM" +
                " company_buses" +
                " INNER JOIN companies ON companies.company_id = company_buses.company_id" +
                " INNER JOIN buses ON company_buses.bus_id = buses.bus_id" + // Fixed typo here
                " WHERE 1 = 1 "; // Added space after 1 = 1
        String filterQuery = filter.buildQuery();
        query += filterQuery;
        Connection conn = DatabaseUtil.getConnection();
        try (
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
             busList.add(getBusFromResult(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busList;
    }




}
