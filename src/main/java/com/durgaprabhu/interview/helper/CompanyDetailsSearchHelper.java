package com.durgaprabhu.interview.helper;

import com.durgaprabhu.interview.controller.CompanyDetailsSearchController;
import com.durgaprabhu.interview.dto.AddressDetailsDto;
import com.durgaprabhu.interview.dto.CompanyDetailsDto;
import com.durgaprabhu.interview.dto.CompanySearchResultDto;
import com.durgaprabhu.interview.dto.OfficerDetailsDto;
import com.durgaprabhu.interview.entity.AddressDetails;
import com.durgaprabhu.interview.entity.CompanyDetails;
import com.durgaprabhu.interview.entity.OfficerDetails;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CompanyDetailsSearchHelper {

    private final static Logger logger = LoggerFactory.getLogger(CompanyDetailsSearchController.class);

    private ModelMapper modelMapper;

    /**
     * Create Company Search Result Data transfer object
     * @param companyDetails
     * @return
     */
    public CompanySearchResultDto createCompanySearchResultDto(CompanyDetails companyDetails){
        logger.trace("createCompanySearchResultDto method");

        modelMapper = new ModelMapper();
        CompanySearchResultDto companySearchResultDto = new CompanySearchResultDto();

        List<CompanyDetailsDto> companyDetailsDtoList = createCompanyDetailsDtoList(companyDetails);
        companySearchResultDto.setTotalResults(companyDetailsDtoList.size());
        companySearchResultDto.setCompanyDetailsDtoList(companyDetailsDtoList);

        return companySearchResultDto;
    }

    /**
     * Create Address Details Data transfer object
     * @param addressDetails
     * @return
     */
    private AddressDetailsDto createAddressDetailsDto(AddressDetails addressDetails) {
        logger.trace("createAddressDetailsDto method");

        AddressDetailsDto addressDetailsDto = modelMapper.map(addressDetails, AddressDetailsDto.class);
        return addressDetailsDto;
    }

    /**
     * Create Company Details Data transfer object
     * @param companyDetails
     * @return
     */
    private List<CompanyDetailsDto> createCompanyDetailsDtoList(CompanyDetails companyDetails){
        logger.trace("createCompanyDetailsDtoList method");

        List<CompanyDetailsDto> companyDetailsDtoList = new ArrayList<>();
        CompanyDetailsDto companyDetailsDto = modelMapper.map(companyDetails, CompanyDetailsDto.class);

        companyDetailsDto.setAddressDetailsDto(createAddressDetailsDto(companyDetails.getAddressDetails()));
        companyDetailsDto.setOfficerDetailsDtoList(createOfficerDetailsDtoList(companyDetails.getOfficerDetails()));

        companyDetailsDtoList.add(companyDetailsDto);

        return companyDetailsDtoList;
    }

    /**
     * Create Officer Details Data transfer object
     * @param officerDetailsList
     * @return
     */
    private List<OfficerDetailsDto> createOfficerDetailsDtoList(List<OfficerDetails> officerDetailsList){
        logger.trace("createOfficerDetailsDtoList method");

        List<OfficerDetailsDto> officerDetailsDtoList = new ArrayList<>();
        List<OfficerDetails> currentOfficerDetailsList = officerDetailsList.stream().filter(officerDetails -> Objects.isNull(officerDetails.getResignedOn())).toList();
        for (OfficerDetails officerDetails: currentOfficerDetailsList) {
            OfficerDetailsDto officerDetailsDto = modelMapper.map(officerDetails, OfficerDetailsDto.class);
            officerDetailsDto.setAddressDetailsDto(createAddressDetailsDto(officerDetails.getAddressDetails()));
            officerDetailsDto.setFullName(officerDetails.getLastName() +"," +officerDetails.getFirstName());
            officerDetailsDtoList.add(officerDetailsDto);
        }
        return officerDetailsDtoList;
    }
}
