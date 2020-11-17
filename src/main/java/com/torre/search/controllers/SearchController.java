package com.torre.search.controllers;

import com.torre.search.domain.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/search")
public class SearchController {

    public static final String INFO_RETRIEVED_SUCCESSFULLY = "Info retrieved successfully";

    @Value("${torre.url.opportunities}")
    private String urlOpportunities;

    @GetMapping(value = "/{offset}/{size}/{aggregate}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse<String> getShowById(@PathVariable int offset,
                                           @PathVariable int size,
                                           @PathVariable boolean aggregate) {
        RestTemplate restTemplate = new RestTemplate();
        String torreResourceUrl = urlOpportunities.replace("{offset}",Integer.toString(offset))
                .replace("{size}",Integer.toString(size))
                .replace("{aggregate}",Boolean.toString(aggregate));
        String requestJsonBody = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(requestJsonBody,headers);

        return new ApiResponse<>(HttpStatus.OK.value(),INFO_RETRIEVED_SUCCESSFULLY,
                restTemplate.postForEntity(torreResourceUrl, entity, String.class).getBody());
    }
}
