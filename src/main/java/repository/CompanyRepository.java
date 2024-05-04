package repository;

import ENUMS.ActivityStatus;
import ENUMS.AreaCode;
import databaseConnection.DatabaseUtil;
import model.Company;
import service.DateFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository
{
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
                String companyId = results.getString("company_id");
                String companyName = results.getString("company_name");
                AreaCode areaCode = AreaCode.valueOf(results.getString("area_code"));
                ActivityStatus activityStatus = ActivityStatus.valueOf(results.getString("company_status"));
                String description = results.getString("description");
                companies.add(new Company(companyId,companyName,description,areaCode,activityStatus));
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
                String companyId = results.getString("company_id");
                String companyName = results.getString("company_name");
                AreaCode areaCode = AreaCode.valueOf(results.getString("area_code"));
                ActivityStatus activityStatus = ActivityStatus.valueOf(results.getString("company_status"));
                String description = results.getString("description");
                companies.add(new Company(companyId,companyName,description,areaCode,activityStatus));
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



}
