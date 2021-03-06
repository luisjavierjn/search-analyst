package com.torre.search.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torre.search.domain.dto.ApiResponse;
import com.torre.search.domain.dto.CompanyDTO;
import com.torre.search.domain.dto.TotalDTO;
import com.torre.search.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    public static final String INFO_RETRIEVED_SUCCESSFULLY = "Info retrieved successfully";

    @Value("${torre.url.opportunities}")
    private String urlOpportunities;

    @GetMapping(value = "/{offset}/{size}/{aggregate}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse<String> getSearchByFilters(@PathVariable int offset,
                                                  @PathVariable int size,
                                                  @PathVariable boolean aggregate) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String torreResourceUrl = urlOpportunities.replace("{offset}",Integer.toString(offset))
                .replace("{size}",Integer.toString(size))
                .replace("{aggregate}",Boolean.toString(aggregate));
        String requestJsonBody = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJsonBody,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(torreResourceUrl, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(Objects.requireNonNull(response.getBody()));
        searchService.saveCompanies(root.get("results"));

        return new ApiResponse<>(HttpStatus.OK.value(),INFO_RETRIEVED_SUCCESSFULLY, response.getBody());
    }

    @GetMapping(value = "/currencies", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse<Set<String>> getCurrencies() {
        return new ApiResponse<>(HttpStatus.OK.value(),INFO_RETRIEVED_SUCCESSFULLY, searchService.getCurrencies());
    }

    @GetMapping(value = "/types", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse<Set<String>> getTypes() {
        return new ApiResponse<>(HttpStatus.OK.value(),INFO_RETRIEVED_SUCCESSFULLY, searchService.getTypes());
    }

    @GetMapping(value = "t/{name}/{currency}/{type}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse<TotalDTO> getTotals(@PathVariable String name,
                                        @PathVariable String currency,
                                        @PathVariable String type) {
        TotalDTO totals = searchService.getTotals(name,currency,type);
        return new ApiResponse<>(HttpStatus.OK.value(),INFO_RETRIEVED_SUCCESSFULLY, totals);
    }

    @GetMapping(value = "p/{name}/{currency}/{type}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse<List<CompanyDTO>> getCompaniesByFilter(@PathVariable String name,
                                                                          @PathVariable String currency,
                                                                          @PathVariable String type) {
        List<CompanyDTO> companies = searchService.getCompaniesByFilter(name,currency,type);
        return new ApiResponse<>(HttpStatus.OK.value(),INFO_RETRIEVED_SUCCESSFULLY, companies);
    }
}
