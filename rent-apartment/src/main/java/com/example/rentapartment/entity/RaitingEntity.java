package com.example.rentapartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "raiting_info")

public class RaitingEntity {
    @Id
    @SequenceGenerator(name = "raiting_infoSequence", sequenceName = "raiting_info_sequence", allocationSize = 1, initialValue = 22)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "raiting_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "raiting")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name="apartment_id")
    private ApartmentEntity apartment;

}
