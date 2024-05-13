package com.durgaprabhu.interview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
public class AddressDetails {

    @Id
    @Column(name = "ADDRESS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressID;

    @Column(name = "LOCALITY")
    private  String locality;

    @Column(name = "ADDRESS_FIRST_LINE")
    private String addressFirstLine;

    @Column(name = "POST_CODE")
    private String postCode;

    @Column(name = "PREMISES")
    private String premises;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CREATED_TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTimestamp;

    @Column(name = "UPDATED_TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedTimestamp;
}
