package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.torre.search.domain.Company;
import com.torre.search.repositories.SearchRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<String> getCurrencies() {
        List<Company> companies = searchRepositoryInMemory.findAll();
        Set<String> currencies = new HashSet<>();
        companies.forEach(c -> currencies.add(c.getCompensation().getCurrency()));
        return currencies;
    }

    @Override
    public Set<String> getTypes() {
        List<Company> companies = searchRepositoryInMemory.findAll();
        Set<String> types = new HashSet<>();
        companies.forEach(c -> types.add(c.getType()));
        return types;
    }

}
