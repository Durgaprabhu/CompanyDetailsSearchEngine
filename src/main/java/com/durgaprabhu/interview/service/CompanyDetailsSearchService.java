package com.durgaprabhu.interview.service;

import com.durgaprabhu.interview.entity.CompanyDetails;

import java.util.Optional;

public interface CompanyDetailsSearchService {

    Optional<CompanyDetails> getCompanyRecordByCompanyNumber(Long companyNumber);

    CompanyDetails getCompanyRecordByCompanyName(String companyName);

    CompanyDetails getCompanyRecordByCompanyNumberAndCompanyStatus(Long companyNumber, String companyStatus);

    CompanyDetails getCompanyRecordByCompanyNameAndCompanyStatus(String companyName, String companyStatus);
}
