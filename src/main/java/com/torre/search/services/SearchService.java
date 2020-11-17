package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.torre.search.repositories.SearchRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchService implements ISearchService {

    @Autowired
    private SearchRepositoryInMemory showRepositoryInMemory;

    @Override
    public void saveCompanies(JsonNode companies) {
        showRepositoryInMemory.save(companies);
    }


}
