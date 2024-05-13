package com.durgaprabhu.interview.controller;

import com.durgaprabhu.interview.entity.CompanyDetails;
import com.durgaprabhu.interview.helper.CompanyDetailsSearchHelper;
import com.durgaprabhu.interview.service.CompanyDetailsSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/Companies")
public class CompanyDetailsSearchController {

    private final static Logger logger = LoggerFactory.getLogger(CompanyDetailsSearchController.class);

    private CompanyDetailsSearchHelper companyDetailsSearchHelper;
    private final static String COMPANY_NUMBER = "companyNumber";
    private final static String COMPANY_NAME = "companyName";

    private CompanyDetailsSearchService companyDetailsSearchService;

    @Autowired
    public CompanyDetailsSearchController(CompanyDetailsSearchService companyDetailsSearchService,
                                          CompanyDetailsSearchHelper companyDetailsSearchHelper){
        this.companyDetailsSearchService = companyDetailsSearchService;
        this.companyDetailsSearchHelper = companyDetailsSearchHelper;
    }

    @PostMapping(value = "/v1/search", produces="application/json", consumes = "application/json")
    public ResponseEntity<Object> retrieveCompanyDetails(@RequestBody Map<String,Object> searchRequestBody, @RequestParam(name = "status", required = false) String companyStatus){
        logger.trace("retrieveCompanyDetails method");

        if(searchRequestBody.containsKey(COMPANY_NUMBER)){
            return searchByCompanyNumber(searchRequestBody.get(COMPANY_NUMBER), companyStatus);
        }

        if(searchRequestBody.containsKey(COMPANY_NAME)){
            return searchByCompanyName(searchRequestBody.get(COMPANY_NAME), companyStatus);
        }
        return prepareErrorEntityObject("{\"Error\": \"Bad search key\"}");
    }


    /**
     * Action search by company number
     * @param companyNumberObject
     * @param companyStatus
     * @return
     */
    private ResponseEntity<Object> searchByCompanyNumber(Object companyNumberObject, String companyStatus) {
        logger.trace("searchByCompanyNumber method, Company Number: " + companyNumberObject);
        boolean doCompanyStatusPresent = Objects.nonNull(companyStatus) && !companyStatus.isBlank();

        try{
            long companyNumber = Long.parseLong(companyNumberObject.toString());

            if(0 != companyNumber){
                Optional<CompanyDetails> companyDetails;
                if(doCompanyStatusPresent)
                    companyDetails = Optional.ofNullable(companyDetailsSearchService.getCompanyRecordByCompanyNumberAndCompanyStatus(companyNumber, companyStatus));
                else
                    companyDetails = companyDetailsSearchService.getCompanyRecordByCompanyNumber(companyNumber);

                return prepareReturnEntityObject(companyDetails);
            }

        }catch(NumberFormatException ex){
            logger.trace("Company Number in invalid format ");
        }

        return prepareErrorEntityObject("{\"Error\": \"Invalid Company Number\"}");
    }

    /**
     * Action search by company name
     * @param companyNameObject
     * @param companyStatus
     * @return
     */
    private ResponseEntity<Object> searchByCompanyName(Object companyNameObject, String companyStatus) {
        logger.trace("searchByCompanyName method, company name : " + companyNameObject);
        boolean doCompanyStatusPresent = Objects.nonNull(companyStatus) && !companyStatus.isBlank();

        if(Objects.nonNull(companyNameObject) && !companyNameObject.toString().isBlank()){

            CompanyDetails companyDetails;
            if(doCompanyStatusPresent)
                companyDetails =  companyDetailsSearchService.getCompanyRecordByCompanyNameAndCompanyStatus(companyNameObject.toString(), companyStatus);
            else
                companyDetails = companyDetailsSearchService.getCompanyRecordByCompanyName(companyNameObject.toString());

            return prepareReturnEntityObject(Optional.ofNullable(companyDetails));
        }

        return prepareErrorEntityObject("{\"Error\": \"Invalid Company Name\"}");
    }

    /**
     * Prepare Valid Entity Object
     * @param companyDetails
     * @return
     */
    private ResponseEntity<Object> prepareReturnEntityObject(Optional<CompanyDetails> companyDetails){
        if(companyDetails.isPresent()){
            //companyDetailsSearchHelper = new CompanyDetailsSearchHelper();
            return new ResponseEntity<>(companyDetailsSearchHelper.createCompanySearchResultDto(companyDetails.get()), HttpStatus.FOUND);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    /**
     * Prepare Error Entity Object
     * @param errorMessage
     * @return
     */
    private ResponseEntity<Object> prepareErrorEntityObject(String errorMessage){
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
