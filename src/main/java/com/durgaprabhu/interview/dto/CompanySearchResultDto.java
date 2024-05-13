package com.durgaprabhu.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CompanySearchResultDto {

    @JsonProperty("total_results")
    public int totalResults;

    @JsonProperty("items")
    public List<CompanyDetailsDto> companyDetailsDtoList;

}
