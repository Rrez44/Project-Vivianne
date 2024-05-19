package service;

import controller.Company;
import repository.CompanyRepository;

import java.util.List;

public class CompanyService {

    public static List<model.Company> loadAllCompanies(){
        return CompanyRepository.initialLoad();
    }


}
