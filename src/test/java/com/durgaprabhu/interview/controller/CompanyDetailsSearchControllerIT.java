package com.durgaprabhu.interview.controller;

import com.durgaprabhu.interview.CompanyDetailsSearchEngine;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompanyDetailsSearchEngine.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyDetailsSearchControllerIT {

    @LocalServerPort
    private int port;
    private static final String COMPANY_DETAILS_SEARCH_URI = "/DurgaprabhuSearchAPI/Companies/v1/search";
    private static final String LOCALHOST = "http://localhost:";
    private static final String X_API_KEY = "x-api-key";
    private static final String X_API_KEY_VALUE = "localtest";
    TestRestTemplate restTemplate;
    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        restTemplate = new TestRestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(X_API_KEY, X_API_KEY_VALUE);
    }

    @Test
    void shouldRetrieveCompanyDetailsSearchResultWhenCompanyNumberIsFound() throws JSONException {
        JSONObject companySearchDetailsRequestBody = createCompanySearchDetailsRequestBody(2001L);
        HttpEntity<String> entity = new HttpEntity<>(companySearchDetailsRequestBody.toString(), httpHeaders);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(createURLWithPort(), entity, Object.class);
        Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(((HashMap<?,?>)responseEntity.getBody()).get("items").toString().contains("company_type=LTD"));
    }

    @Test
    void shouldRetrieveCompanyDetailsSearchResultWhenCompanyNumberIsFoundWithActiveStatus() throws JSONException {
        JSONObject companySearchDetailsRequestBody = createCompanySearchDetailsRequestBody(2000L);
        httpHeaders.set("status", "ACTIVE");
        HttpEntity<String> entity = new HttpEntity<>(companySearchDetailsRequestBody.toString(), httpHeaders);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(createURLWithPort(), entity, Object.class);
        Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(((HashMap<?,?>)responseEntity.getBody()).get("items").toString().contains("company_type=LTD"));
    }

    @Test
    void shouldRetrieveCompanyDetailsSearchResultWhenCompanyNameIsFound() throws JSONException {
        JSONObject companySearchDetailsRequestBody = createCompanySearchDetailsRequestBodyWithNameOnly("XYZ");
        HttpEntity<String> entity = new HttpEntity<>(companySearchDetailsRequestBody.toString(), httpHeaders);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(createURLWithPort(), entity, Object.class);
        Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(((HashMap<?,?>)responseEntity.getBody()).get("items").toString().contains("company_type=LTD"));
    }

    @Test
    void shouldRetrieveCompanyDetailsSearchResultWhenCompanyNameIsFoundWithActiveStatus() throws JSONException {
        JSONObject companySearchDetailsRequestBody = createCompanySearchDetailsRequestBodyWithNameOnly("ABC");
        httpHeaders.set("status", "ACTIVE");
        HttpEntity<String> entity = new HttpEntity<>(companySearchDetailsRequestBody.toString(), httpHeaders);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(createURLWithPort(), entity, Object.class);
        Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(((HashMap<?,?>)responseEntity.getBody()).get("items").toString().contains("company_type=LTD"));
    }

    @Test
    void shouldNotRetrieveCompanyDetailsSearchResultWhenCompanyNameIsNotFound() throws JSONException {
        JSONObject companySearchDetailsRequestBody = createCompanySearchDetailsRequestBodyWithNameOnly("ABCDDDD");
        HttpEntity<String> entity = new HttpEntity<>(companySearchDetailsRequestBody.toString(), httpHeaders);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(createURLWithPort(), entity, Object.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotRetrieveCompanyDetailsSearchResultWhenCompanyNumberIsNotFound() throws JSONException {
        JSONObject companySearchDetailsRequestBody = createCompanySearchDetailsRequestBody(2010L);
        HttpEntity<String> entity = new HttpEntity<>(companySearchDetailsRequestBody.toString(), httpHeaders);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(createURLWithPort(), entity, Object.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private JSONObject createCompanySearchDetailsRequestBody(Long companyNumber) throws JSONException {
        JSONObject companySearchDetailsRequestBody = new JSONObject();
        companySearchDetailsRequestBody.put("companyNumber",companyNumber);
        companySearchDetailsRequestBody.put("companyName", "ABC");
        return companySearchDetailsRequestBody;
    }

    private  JSONObject createCompanySearchDetailsRequestBodyWithNameOnly(String companyName) throws JSONException {
        JSONObject companySearchDetailsRequestBody = new JSONObject();
        companySearchDetailsRequestBody.put("companyName", companyName);
        return companySearchDetailsRequestBody;
    }

    private String createURLWithPort() {
        return LOCALHOST + port + COMPANY_DETAILS_SEARCH_URI;
    }
}
