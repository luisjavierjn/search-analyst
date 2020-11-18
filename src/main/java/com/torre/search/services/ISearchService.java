package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.torre.search.domain.dto.CompanyDTO;
import com.torre.search.domain.dto.TotalDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISearchService {

    void saveCompanies(JsonNode companies);

    Set<String> getCurrencies();

    Set<String> getTypes();

    List<CompanyDTO> getCompaniesByFilter(String name, String currency, String type);

    TotalDTO getTotals(String name, String currency, String type);
}
