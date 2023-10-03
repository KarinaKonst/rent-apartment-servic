package com.example.rentapartment.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "client_info")
public class ClientEntity {

    @Id
    @SequenceGenerator(name = "client_infoSequence", sequenceName = "client_info_sequence", allocationSize = 1, initialValue = 11)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "number_passport")
    private String numberPassport;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "email_address")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "parent_city")
    private String parentCity;
    @Column(name = "count_of_grocery")
    private Integer countOfGrocery;

    @Column(name = "commerce")
    private Boolean commerce;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clientId")
    private List<BookingHistoryEntity> bookingHistoryList;


}

