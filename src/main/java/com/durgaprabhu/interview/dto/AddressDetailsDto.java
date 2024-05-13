package com.durgaprabhu.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressDetailsDto {

    @JsonProperty("locality")
    private String locality;

    @JsonProperty("postal_code")
    private String postCode;

    @JsonProperty("premises")
    private String premises;

    @JsonProperty("address_line_1")
    private String address;

    @JsonProperty("country")
    private String country;

}
