package service;

import ENUMS.AreaCode;
import model.Company;
import repository.CompanyRepository;

import java.util.List;

public class CompanyService {

    public static List<model.Company> loadAllCompanies(){
        return CompanyRepository.initialLoad();
    }

    public static List<Company> searchCompanies(String cName){
        return CompanyRepository.searchCompanies(cName);
    }

    public static model.Company getCompanyFromId(String id){
        return CompanyRepository.getCompanyFromId(id);
    }

    public static boolean createCompany(String companyName, AreaCode areaCode, String description){
        return CompanyRepository.createCompany(companyName,areaCode,description);
    }
    public static Company getCompanyFromName(String name) {
        return CompanyRepository.getCompanyFromName(name);
    }
    public static boolean updateCompany(Company c) throws RuntimeException {
        return CompanyRepository.updateCompany(c);
    }

}
