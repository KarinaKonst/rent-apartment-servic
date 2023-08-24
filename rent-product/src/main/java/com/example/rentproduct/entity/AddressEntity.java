package com.example.rentproduct.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="address_apartment")
public class AddressEntity {
    @Id
    @SequenceGenerator(name="address_apartmentSequence", sequenceName="address_apartment_sequence", allocationSize = 1,initialValue =20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_apartmentSequence")
    @Column(name = "id")
    private Long id;

    @Column(name="city")
    private  String city;

    @Column(name="street")
    private String street;

    @Column(name="number_house")
    private  String numberHouse;


    @Column(name="number_apartment")
    private  String numberApartment;
    @OneToOne
    @JoinColumn(name="apartment_id")
    private ApartmentEntity apartmentEntity;


}
