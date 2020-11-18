package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.torre.search.domain.Company;
import com.torre.search.repositories.SearchRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private SearchRepositoryInMemory searchRepositoryInMemory;

    @Override
    public void saveCompanies(JsonNode companies) {
        searchRepositoryInMemory.save(companies);
        List<Company> companyList = searchRepositoryInMemory.findAll();
        System.out.println(companyList);
    }

}
