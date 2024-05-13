package com.durgaprabhu.interview.service;

import com.durgaprabhu.interview.entity.CompanyDetails;
import com.durgaprabhu.interview.repository.CompanyDetailsSearchRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompanyDetailsSearchServiceImpl implements CompanyDetailsSearchService {
    
    private final static Logger logger = LoggerFactory.getLogger(CompanyDetailsSearchServiceImpl.class);

    CompanyDetailsSearchRepo companyDetailsSearchRepository;

    @Autowired
    public CompanyDetailsSearchServiceImpl(CompanyDetailsSearchRepo companyDetailsSearchRepository){
        this.companyDetailsSearchRepository = companyDetailsSearchRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyDetails> getCompanyRecordByCompanyNumber(Long companyNumber){
        logger.trace("getCompanyRecordByCompanyNumber called with : " + companyNumber);
        return companyDetailsSearchRepository.findById(companyNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDetails getCompanyRecordByCompanyName(String companyName){
        logger.trace("getCompanyRecordByCompanyName method called with : " + companyName);
        return companyDetailsSearchRepository.findByCompanyName(companyName);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDetails getCompanyRecordByCompanyNumberAndCompanyStatus(Long companyNumber, String companyStatus) {
        logger.trace("getCompanyRecordByCompanyNumberAndCompanyStatus called with Company Number :{} ,Company Status {} ", companyNumber,companyStatus);
        return companyDetailsSearchRepository.findByCompanyNumberAndCompanyStatus(companyNumber, companyStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDetails getCompanyRecordByCompanyNameAndCompanyStatus(String companyName, String companyStatus) {
        logger.trace("getCompanyRecordByCompanyNumberAndCompanyStatus called with Company Name :{} ,Company Status {} ", companyName,companyStatus);
        return companyDetailsSearchRepository.findByCompanyNameAndCompanyStatus(companyName, companyStatus);
    }

}
