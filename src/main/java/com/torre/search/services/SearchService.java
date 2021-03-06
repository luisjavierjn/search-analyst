package com.torre.search.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.torre.search.domain.dto.CompanyDTO;
import com.torre.search.domain.entities.Company;
import com.torre.search.domain.dto.TotalDTO;
import com.torre.search.repositories.SearchRepositoryInMemory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private SearchRepositoryInMemory searchRepositoryInMemory;

    @Autowired
    private ModelMapper modelMapper;

    private CompanyDTO convertToDto(Company company) {
        CompanyDTO companyDto = modelMapper.map(company, CompanyDTO.class);
        companyDto.setCode(company.getCompensation().getCode());
        companyDto.setCurrency(company.getCompensation().getCurrency());
        companyDto.setMinAmount(company.getCompensation().getMinAmount());
        companyDto.setMaxAmount(company.getCompensation().getMaxAmount());
        companyDto.setPeriodicity(company.getCompensation().getPeriodicity());
        return companyDto;
    }

    @Override
    public void saveCompanies(JsonNode companies) {
        searchRepositoryInMemory.clean();
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

    @Override
    public TotalDTO getTotals(String name, String currency, String type) {
        List<Company> companies = searchRepositoryInMemory.findAll();

        long tripleIntersection = companies.stream()
                .filter(c -> c.getObjective().toLowerCase().contains(name.toLowerCase()) &&
                        c.getCompensation().getCurrency().equals(currency) &&
                        c.getType().equals(type))
                .count();

        long nameCurrencyIntersection = 0;
        long nameTypeIntersection = 0;
        long currencyTypeIntersection = 0;

        if(tripleIntersection == 0) {
            nameCurrencyIntersection = companies.stream()
                    .filter(c -> c.getObjective().toLowerCase().contains(name.toLowerCase()) &&
                            c.getCompensation().getCurrency().equals(currency))
                    .count();

            nameTypeIntersection = companies.stream()
                    .filter(c -> c.getObjective().toLowerCase().contains(name.toLowerCase()) &&
                            c.getType().equals(type))
                    .count();

            currencyTypeIntersection = companies.stream()
                    .filter(c -> c.getCompensation().getCurrency().equals(currency) &&
                            c.getType().equals(type))
                    .count();
        }

        return TotalDTO.builder()
                .totalCompanies(companies.size())
                .totalCompaniesByName(companies.stream()
                        .filter(c -> c.getObjective().toLowerCase().contains(name.toLowerCase()))
                        .count())
                .totalCompaniesByCurrency(companies.stream()
                        .filter(c -> c.getCompensation().getCurrency().equals(currency))
                        .count())
                .totalCompaniesByType(companies.stream()
                        .filter(c -> c.getType().equals(type))
                        .count())
                .tripleIntersections(tripleIntersection)
                .nameCurrencyIntersections(nameCurrencyIntersection)
                .nameTypeIntersections(nameTypeIntersection)
                .currencyTypeIntersections(currencyTypeIntersection)
                .build();
    }

    @Override
    public List<CompanyDTO> getCompaniesByFilter(String name, String currency, String type) {
        List<Company> companies = searchRepositoryInMemory.findAll();

        return companies.stream()
                .filter(c -> c.getObjective().toLowerCase().contains(name.toLowerCase()) ||
                          c.getCompensation().getCurrency().equals(currency) ||
                          c.getType().equals(type))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
