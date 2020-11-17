package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface ISearchService {

    void saveCompanies(JsonNode companies);
}
