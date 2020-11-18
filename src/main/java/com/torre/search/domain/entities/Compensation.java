package com.torre.search.domain.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Compensation {
    private final String code;
    private final String currency;
    private final double minAmount;
    private final double maxAmount;
    private final String periodicity;
}
