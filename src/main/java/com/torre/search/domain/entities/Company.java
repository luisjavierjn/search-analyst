package com.torre.search.domain.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Company {
    private final String id;
    private final String objective;
    private final String type;
    private final List<String> locations;
    private final boolean remote;
    private final String status; // only Open
    private final Compensation compensation;
}
