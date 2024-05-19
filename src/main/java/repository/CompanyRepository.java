package repository;

import ENUMS.ActivityStatus;
import ENUMS.AreaCode;
import databaseConnection.DatabaseUtil;
import model.Company;
import service.DateFormatter;
import model.BusLine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository
{

    public static Company getCompanyFromResult(ResultSet result) throws SQLException {
        String companyId = result.getString("company_id");
        String companyName = result.getString("company_name");
        AreaCode areaCode = AreaCode.valueOf(result.getString("area_code"));
        ActivityStatus activityStatus = ActivityStatus.valueOf(result.getString("company_status"));
        String description = result.getString("description");
        return new Company(companyId,companyName,description,areaCode,activityStatus);
    }



    public static boolean createCompany(String companyName, AreaCode areaCode,String description){
        Company company = new Company(companyName,areaCode,description);
        String query = "INSERT INTO companies(company_id,company_name, area_code, company_status, description) values(?,?,?,?,?)";
        Connection conn = DatabaseUtil.getConnection();
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, company.getCompanyId());
            preparedStatement.setString(2, company.getCompanyName());
            preparedStatement.setString(3, company.getAreaCode().toString());
            preparedStatement.setString(4, company.getCompanyStatus().name());
            preparedStatement.setString(5, company.getDescription());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        }
        catch (SQLException e ){
            throw new RuntimeException("Error inserting new company: " + e.getMessage());
        }

    }

    public static List<Company> initialLoad(){
        Connection conn = DatabaseUtil.getConnection();
        String query = "SELECT * FROM companies";
        List<Company> companies = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            while (results.next()){
                companies.add(getCompanyFromResult(results));
            }
            stmt.close();
            return companies;
        }
        catch (SQLException se ){
            throw new RuntimeException("Error Loading companies: " + se.getMessage());
        }
    }

    public static List<Company> searchCompanies(String cName){
        Connection conn = DatabaseUtil.getConnection();
        List<Company> companies = new ArrayList<>();
        String query = "SELECT * FROM COMPANIES WHERE company_name like ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println("%" + cName + "%");
            stmt.setString(1, "%" + cName + "%");
            ResultSet results = stmt.executeQuery();
            while (results.next()){
                companies.add(getCompanyFromResult(results));
            }
            if (companies.isEmpty()){
                throw new RuntimeException("No companies found with this name");
            }
            stmt.close();
            return companies;

        }
        catch (SQLException se){
            throw new RuntimeException("Failed to find companies: " + se.getMessage());
        }
    }
    public static Company searchCompany(String cName){
    Connection conn = DatabaseUtil.getConnection();

    String query = "SELECT * FROM COMPANIES WHERE company_name like ?";
    try{
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" + cName + "%");
        ResultSet results = stmt.executeQuery();
        if (results.next()){
            return getCompanyFromResult(results);
        }
        stmt.close();
        throw new RuntimeException("No companies found with this name");
    }
    catch (SQLException se){
        throw new RuntimeException("Failed to find companies: " + se.getMessage());
        }
    }
    
    public static Company getCompanyFromId(String id){
        Connection conn = DatabaseUtil.getConnection();
        String query = "Select * from companies where company_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id.trim());
            stmt.execute();
            ResultSet result = stmt.getResultSet();

            if (!result.next()) {
                throw new RuntimeException("No company found with ID: " + id);
            }
            return getCompanyFromResult(result);

        }
        catch (SQLException se){
            throw new RuntimeException(se.getMessage());
        }
    }
    public static boolean updateCompany(Company c) throws RuntimeException{
        Connection conn = DatabaseUtil.getConnection();
        String query = "UPDATE companies SET company_name = ?, area_code = ?, company_status = ?, description = ? WHERE company_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,c.getCompanyName());
            stmt.setString(2,c.getAreaCode().name());
            stmt.setString(3,c.getCompanyStatus().name());
            stmt.setString(4,c.getDescription());
            stmt.setString(5,c.getCompanyId());
            stmt.execute();
            stmt.close();
            return true;
        }
        catch (SQLException se){
            throw new RuntimeException("Error updating company: " + se.getMessage());
        }
    }



}
