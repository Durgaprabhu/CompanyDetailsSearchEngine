package com.durgaprabhu.interview.controller;

import com.durgaprabhu.interview.dto.CompanyDetailsDto;
import com.durgaprabhu.interview.dto.CompanySearchResultDto;
import com.durgaprabhu.interview.entity.CompanyDetails;
import com.durgaprabhu.interview.helper.CompanyDetailsSearchHelper;
import com.durgaprabhu.interview.service.CompanyDetailsSearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CompanyDetailsSearchControllerTest {

    private CompanyDetails companyDetails;
    private CompanySearchResultDto companySearchResultDto;
    private CompanyDetailsDto companyDetailsDto;
    private CompanyDetailsSearchController companyDetailsSearchController;

    private CompanyDetailsSearchHelper companyDetailsSearchHelper;

    private CompanyDetailsSearchService companyDetailsSearchService;

    private final static String COMPANY_NUMBER = "companyNumber";

    private final static String COMPANY_NAME = "companyName";


    @BeforeEach
    void setUp() {
        companyDetails = new CompanyDetails();
        companyDetailsDto = new CompanyDetailsDto();
        companySearchResultDto = new CompanySearchResultDto();
        companyDetailsSearchHelper = Mockito.mock(CompanyDetailsSearchHelper.class);
        companyDetailsSearchService = Mockito.mock(CompanyDetailsSearchService.class);
        companyDetailsSearchController = new CompanyDetailsSearchController(companyDetailsSearchService, companyDetailsSearchHelper);
    }

    @Test
    void shouldReturnCompanyDetailsObjectWhenCompanyNumberAndStatusIsProvided() {
        companyDetails.setCompanyType("LTD");
        companyDetailsDto.setCompanyName("Test");
        companySearchResultDto.setCompanyDetailsDtoList(List.of(companyDetailsDto));
        Mockito.when(companyDetailsSearchService.getCompanyRecordByCompanyNumberAndCompanyStatus(1000L, "ACTIVE")).thenReturn(companyDetails);
        Mockito.when(companyDetailsSearchHelper.createCompanySearchResultDto(companyDetails)).thenReturn(companySearchResultDto);
        Map<String, Object> searchRequestBody = Map.of(COMPANY_NUMBER, "1000");
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(searchRequestBody, "ACTIVE");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assertions.assertEquals(companyDetailsDto.getCompanyName(), ((CompanySearchResultDto) responseEntity.getBody()).getCompanyDetailsDtoList().get(0).getCompanyName());
    }


    @Test
    void shouldReturnCompanyDetailsObjectWhenCompanyNameAndStatusIsProvided() {
        companyDetails.setCompanyType("LTD");
        companyDetailsDto.setCompanyNumber(2000L);
        companySearchResultDto.setCompanyDetailsDtoList(List.of(companyDetailsDto));
        Mockito.when(companyDetailsSearchService.getCompanyRecordByCompanyNameAndCompanyStatus("TESTLTD", "ACTIVE")).thenReturn(companyDetails);
        Mockito.when(companyDetailsSearchHelper.createCompanySearchResultDto(companyDetails)).thenReturn(companySearchResultDto);
        Map<String, Object> searchRequestBody = Map.of(COMPANY_NAME, "TESTLTD");
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(searchRequestBody, "ACTIVE");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assertions.assertEquals(companyDetailsDto.getCompanyNumber(), ((CompanySearchResultDto) responseEntity.getBody()).getCompanyDetailsDtoList().get(0).getCompanyNumber());
    }

    @Test
    void shouldReturnCompanyDetailsObjectWhenCompanyNumberIsProvidedAndStatusIsNull() {
        companyDetails.setCompanyType("LTD");
        companyDetailsDto.setCompanyName("Test");
        companySearchResultDto.setCompanyDetailsDtoList(List.of(companyDetailsDto));
        Mockito.when(companyDetailsSearchService.getCompanyRecordByCompanyNumber(1000L)).thenReturn(Optional.ofNullable(companyDetails));
        Mockito.when(companyDetailsSearchHelper.createCompanySearchResultDto(companyDetails)).thenReturn(companySearchResultDto);
        Map<String, Object> searchRequestBody = Map.of(COMPANY_NUMBER, "1000");
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(searchRequestBody, null);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assertions.assertEquals(companyDetailsDto.getCompanyName(), ((CompanySearchResultDto) responseEntity.getBody()).getCompanyDetailsDtoList().get(0).getCompanyName());
    }

    @Test
    void shouldReturnCompanyDetailsObjectWhenCompanyNameIsProvidedAndStatusIsNull() {
        companyDetails.setCompanyType("LTD");
        companyDetailsDto.setCompanyNumber(2000L);
        companySearchResultDto.setCompanyDetailsDtoList(List.of(companyDetailsDto));
        Mockito.when(companyDetailsSearchService.getCompanyRecordByCompanyName("TESTLTD")).thenReturn(companyDetails);
        Mockito.when(companyDetailsSearchHelper.createCompanySearchResultDto(companyDetails)).thenReturn(companySearchResultDto);
        Map<String, Object> searchRequestBody = Map.of(COMPANY_NAME, "TESTLTD");
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(searchRequestBody, null);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assertions.assertEquals(companyDetailsDto.getCompanyNumber(), ((CompanySearchResultDto) responseEntity.getBody()).getCompanyDetailsDtoList().get(0).getCompanyNumber());
    }

    @Test
    void shouldReturnNotFoundWhenCompanyNameIsNotPresent() {
        Mockito.when(companyDetailsSearchService.getCompanyRecordByCompanyName("TESTLTD")).thenReturn(null);
        Map<String, Object> searchRequestBody = Map.of(COMPANY_NAME, "TESTLTD");
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(searchRequestBody, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenCompanyNumberIsNotPresent() {
        Mockito.when(companyDetailsSearchService.getCompanyRecordByCompanyNumber(2000L)).thenReturn(Optional.empty());
        Map<String, Object> searchRequestBody = Map.of(COMPANY_NUMBER, "2000");
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(searchRequestBody, null);
        Assertions.assertEquals( HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenNoSearchKeyIsProvided() {
        ResponseEntity<Object> responseEntity = companyDetailsSearchController.retrieveCompanyDetails(Map.of(), null);
        Assertions.assertEquals( HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }
}