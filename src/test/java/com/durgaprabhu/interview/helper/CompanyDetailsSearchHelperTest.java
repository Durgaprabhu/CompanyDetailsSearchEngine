package com.durgaprabhu.interview.helper;

import com.durgaprabhu.interview.dto.CompanySearchResultDto;
import com.durgaprabhu.interview.entity.AddressDetails;
import com.durgaprabhu.interview.entity.CompanyDetails;
import com.durgaprabhu.interview.entity.OfficerDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

class CompanyDetailsSearchHelperTest {


    @Test
    void shouldConvertCompanyDetailsToAnExpectedDTOObject(){
        AddressDetails addressDetails = getAddressDetails();
        OfficerDetails officerDetails = getOfficerDetails(addressDetails);
        CompanyDetails companyDetails = getCompanyDetails(addressDetails, officerDetails);
        CompanyDetailsSearchHelper companyDetailsSearchHelper = new CompanyDetailsSearchHelper();
        CompanySearchResultDto companySearchResultDto = companyDetailsSearchHelper.createCompanySearchResultDto(companyDetails);
        Assertions.assertEquals(1, companySearchResultDto.totalResults);
        Assertions.assertEquals(companyDetails.getAddressDetails().getCountry(),companySearchResultDto.getCompanyDetailsDtoList().get(0).getAddressDetailsDto().getCountry());
    }

    private static CompanyDetails getCompanyDetails(AddressDetails addressDetails, OfficerDetails officerDetails) {
        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setCompanyType("type");
        companyDetails.setAddressDetails(addressDetails);
        companyDetails.setCompanyName("company");
        companyDetails.setCompanyStatus("sttaus");
        companyDetails.setOfficerDetails(List.of(officerDetails));
        companyDetails.setCompanyStatus("stat");
        return companyDetails;
    }

    private static OfficerDetails getOfficerDetails(AddressDetails addressDetails) {
        OfficerDetails officerDetails = new OfficerDetails();
        officerDetails.setAddressDetails(addressDetails);
        officerDetails.setOfficerRole("role");
        officerDetails.setOfficerID(1234L);
        officerDetails.setAppointedOn(new Date(2020,12,12));
        officerDetails.setFirstName("first");
        officerDetails.setLastName("last");
        return officerDetails;
    }

    private static AddressDetails getAddressDetails() {
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setAddressFirstLine("firstLine");
        addressDetails.setAddressID(123L);
        addressDetails.setCountry("country");
        addressDetails.setLocality("locality");
        addressDetails.setPremises("premises");
        addressDetails.setPostCode("postcode");
        return addressDetails;
    }
}