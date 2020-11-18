package com.torre.search.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyDTO {
    private String id;
    private String objective;
    private String type;
    private List<String> locations;
    private boolean remote;
    private String code;
    private String currency;
    private double minAmount;
    private double maxAmount;
    private String periodicity;
}
