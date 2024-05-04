package repository;

import ENUMS.ActivityStatus;
import ENUMS.BusType;
import ENUMS.ComfortRating;
import databaseConnection.DatabaseUtil;
import model.Bus;
import model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BusRepository {
    public static boolean createBus( String busModel, String vin, int passengerCapacity, BusType busType, ActivityStatus activityStatus, ComfortRating comfortRating, Company company){
        Bus creationBus = new Bus(busModel, vin, passengerCapacity, busType, activityStatus, comfortRating);
        String query = "INSERT INTO buses(bus_id, bus_model, vin , passenger_capacity, bus_type, activity_status, comfort_rating) values (?,?,?,?,?,?,?)";
        String assignBusToCompany = "INSERT INTO company_buses(company_id, bus_id) values(?,?)"; // we also need to create the junction between bus and company as it is a required relation
        Connection conn = DatabaseUtil.getConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            PreparedStatement stmt2 = conn.prepareStatement(assignBusToCompany);
            stmt.setString(1,creationBus.getBusId());
            stmt.setString(2,creationBus.getBusModel());
            stmt.setString(3,creationBus.getVin());
            stmt.setInt(4,creationBus.getPassangerCapacity());
            stmt.setString(5,creationBus.getBusType().name());
            stmt.setString(6,creationBus.getActivityStatus().name());
            stmt.setString(7,creationBus.getComfortRating().name());
            stmt.execute();
            stmt.close();
            stmt2.setString(1, company.getCompanyId());
            stmt2.setString(2, creationBus.getBusId());
            stmt2.execute();
            stmt2.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error inserting new bus: " + e.getMessage());
        }

    }

}
