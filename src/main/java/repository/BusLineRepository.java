package repository;

import databaseConnection.DatabaseUtil;
import model.Bus;
import model.BusLine;
import model.Company;
import model.User;
import service.DateFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class BusLineRepository {
    public static boolean createBusLine(LocalDateTime startTime, LocalDateTime endTime, User creator, String startLocation, String endLocation, Company company, Bus bus ){
        BusLine creationBusLine = new BusLine(startTime,endTime,creator,startLocation,endLocation, company, bus);
        String query = "INSERT INTO company_lines(line_id, status,start_time, end_time,creator_user_id, creation_time, start_location, end_location, company_assigned_id, bus_model_id) values(?,?,?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseUtil.getConnection();
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, creationBusLine.getLineId());
            preparedStatement.setString(2, creationBusLine.getStatus().name());
            preparedStatement.setString(3, DateFormatter.SQLformat(creationBusLine.getStartTime()));
            preparedStatement.setString(4, DateFormatter.SQLformat(creationBusLine.getEndTime()));
            preparedStatement.setString(5, creationBusLine.getCreator().getId());
            preparedStatement.setString(6, DateFormatter.SQLformat(creationBusLine.getCreationTime()));
            preparedStatement.setString(7, creationBusLine.getStartLocation());
            preparedStatement.setString(8, creationBusLine.getEndLocation());
            preparedStatement.setString(9, creationBusLine.getCompanyAssigned().getCompanyId());
            preparedStatement.setString(10, creationBusLine.getBusModel().getBusId());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        }
        catch (SQLException e){
            throw new RuntimeException("Error creating bus line: " + e.getMessage());
        }
    }
}
