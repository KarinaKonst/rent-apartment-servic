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
@Table(name="apartment_info")
public class ApartmentEntity {

    @Id
    @SequenceGenerator(name="apartment_infoSequence", sequenceName="apartment_info_sequence", allocationSize = 1,initialValue =20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="apartment_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name="number_of_rooms")
    private  String numberOfRooms;

    @Column(name="price")
    private String price;
    @Column(name="availability")
    private Boolean availability;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "apartmentEntity")
    private AddressEntity addressEntity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "apartment")
    private List<RaitingEntity> raitingEntityList;

    @ManyToOne
    @JoinColumn(name="current_tenant")
    private ClientEntity currentTenant;
    @ManyToOne
    @JoinColumn(name="owner")
    private ClientEntity  owner;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "apartmentId")
    private List<BookingHistoryEntity> bookingHistoryList;
}
