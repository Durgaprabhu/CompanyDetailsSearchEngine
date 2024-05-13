package com.durgaprabhu.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class OfficerDetailsDto {

    @JsonProperty("name")
    private String fullName;

    @JsonProperty("officer_role")
    private String officerRole;

    @JsonProperty("appointed_on")
    private Date appointedOn;

    @JsonProperty("address")
    private AddressDetailsDto addressDetailsDto;

}
