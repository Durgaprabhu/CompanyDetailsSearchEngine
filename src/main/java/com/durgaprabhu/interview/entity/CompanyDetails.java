package com.durgaprabhu.interview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMPANY")
public class CompanyDetails {

    @Id
    @Column(name = "REGISTRATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyNumber;

    @Column(name= "COMPANY_TYPE")
    private String companyType;

    @Column(name= "COMPANY_NAME")
    private String companyName;

    @Column(name = "COMPANY_STATUS")
    private String companyStatus;

    @Column(name = "INCORPORATION_DATE")
    private Date creationDate;

    @Column(name = "CREATED_TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTimestamp;

    @Column(name = "UPDATED_TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedTimestamp;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID")
    private AddressDetails addressDetails;

    @OneToMany
    @JoinColumn(name = "OFFICER_COMPANY_ID")
    private List<OfficerDetails> officerDetails;
}
