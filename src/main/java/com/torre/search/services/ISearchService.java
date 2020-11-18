package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Set;

public interface ISearchService {

    void saveCompanies(JsonNode companies);

    Set<String> getCurrencies();

    Set<String> getTypes();
}
