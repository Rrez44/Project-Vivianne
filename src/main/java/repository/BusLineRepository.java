package repository;

import ENUMS.ActivityStatus;
import ENUMS.AreaCode;
import ENUMS.Status;
import databaseConnection.DatabaseUtil;
import model.Bus;
import model.BusLine;
import model.Company;
import model.User;
import service.DateFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
        String line_id = result.getString("line_id");
        Status status = Status.valueOf(result.getString("status"));
        LocalDateTime start_time = result.getTimestamp("start_time").toLocalDateTime();
        LocalDateTime end_time = result.getTimestamp("end_time").toLocalDateTime();
        User creator = UserRepository.getById(result.getString("creator_user_id"));
        LocalDateTime creation_time = result.getTimestamp("creation_time").toLocalDateTime();
        String start_location = result.getString("start_location");
        String end_location = result.getString("end_location");
        Company company_assigned_id = CompanyRepository.getCompanyFromId(result.getString("company_assigned_id"));
        Bus bus = BusRepository.getBusById("bus_model_id");
        return new BusLine(line_id,status,start_time,end_time,creator,creation_time,start_location,end_location,company_assigned_id,bus);
    }
}
