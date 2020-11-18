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
}
