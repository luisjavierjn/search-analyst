package com.torre.search.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalDTO {
    private final long totalCompanies;
    private final long totalCompaniesByName;
    private final long totalCompaniesByCurrency;
    private final long totalCompaniesByType;
    private final long tripleIntersections;
    private final long nameCurrencyIntersections;
    private final long nameTypeIntersections;
    private final long currencyTypeIntersections;
}
