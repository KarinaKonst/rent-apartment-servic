package com.example.rentapartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "renter_info")
public class RenterEntity {
    @Id
    @SequenceGenerator(name = "renter_infoSequence", sequenceName = "renter_info_sequence", allocationSize = 1, initialValue = 11)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "renter_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number_passport")
    private String numberPassport;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "email_address")
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "renter_rating")
    private String renterRating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "renterEntity")
    private List<ApartmentEntity> apartmentEntityList;

}
