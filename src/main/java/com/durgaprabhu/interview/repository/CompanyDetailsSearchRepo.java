package com.durgaprabhu.interview.repository;

import com.durgaprabhu.interview.entity.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDetailsSearchRepo extends JpaRepository<CompanyDetails, Long> {


    CompanyDetails findByCompanyName(String companyName);

    CompanyDetails findByCompanyNumberAndCompanyStatus(Long companyNumber, String companyStatus);

    CompanyDetails findByCompanyNameAndCompanyStatus(String companyName, String companyStatus);
}
