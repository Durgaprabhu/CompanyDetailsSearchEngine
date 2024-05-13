package com.durgaprabhu.interview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFFICER")
public class OfficerDetails {

    @Id
    @Column(name = "OFFICER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long officerID;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "OFFICER_ROLE")
    private String officerRole;

    @Column(name = "APPOINTED_ON")
    private Date appointedOn;

    @Column(name = "RESIGNED_ON")
    private Date resignedOn;

    @Column(name = "CREATED_TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTimestamp;

    @Column(name = "UPDATED_TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedTimestamp;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID")
    private AddressDetails addressDetails;

}
