package com.torre.search.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.torre.search.domain.entities.Company;
import com.torre.search.domain.entities.Compensation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchRepositoryInMemory {

    private static final List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return companies;
    }

    public void clean() {
        companies.clear();
    }

    public void save(JsonNode root) {
        if (root.isArray()) {
            for (final JsonNode objNode : root) {

                if(objNode.get("compensation") == null) continue;
                JsonNode moneyNode = objNode.get("compensation").get("data");
                if(moneyNode == null) continue;

                if(chkNull(moneyNode.get("currency")).toLowerCase().equals(""))
                    continue;

                if(!chkNull(objNode.get("status")).toLowerCase().contains("open"))
                    continue;

                List<String> locations = new ArrayList<>();
                for (JsonNode jsonNode : objNode.get("locations")) {
                    locations.add(jsonNode.asText());
                }

                Compensation compensation = Compensation.builder()
                        .code(chkNull(moneyNode.get("code")))
                        .currency(chkNull(moneyNode.get("currency")))
                        .minAmount(chkDouble(moneyNode.get("minAmount")))
                        .maxAmount(chkDouble(moneyNode.get("maxAmount")))
                        .periodicity(chkNull(moneyNode.get("periodicity")))
                        .build();

                companies.add(Company.builder()
                        .id(chkNull(objNode.get("id")))
                        .objective(chkNull(objNode.get("objective")))
                        .type(chkNull(objNode.get("type")))
                        .locations(locations)
                        .remote(Boolean.parseBoolean(chkNull(objNode.get("remote"))))
                        .status("open")
                        .compensation(compensation)
                        .build());
            }
        }
    }

    public String chkNull(JsonNode jsonNode) {
        return jsonNode != null ? jsonNode.asText() : "";
    }

    public double chkDouble(JsonNode jsonNode) {
        if (jsonNode == null) {
            return 0;
        }
        try {
            return Double.parseDouble(jsonNode.asText());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
}
