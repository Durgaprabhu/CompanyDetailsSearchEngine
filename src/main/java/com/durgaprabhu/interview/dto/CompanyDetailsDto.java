package com.durgaprabhu.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CompanyDetailsDto {

    @JsonProperty("company_number")
    private Long companyNumber;

    @JsonProperty("company_type")
    private String companyType;

    @JsonProperty("title")
    private String companyName;

    @JsonProperty("company_status")
    private String companyStatus;

    @JsonProperty("date_of_creation")
    private Date creationDate;

    @JsonProperty("officers")
    private List<OfficerDetailsDto> officerDetailsDtoList;

    @JsonProperty("address")
    private AddressDetailsDto addressDetailsDto;
}
