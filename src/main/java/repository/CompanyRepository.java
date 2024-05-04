package repository;

import ENUMS.AreaCode;
import databaseConnection.DatabaseUtil;
import model.Company;
import service.DateFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


}
