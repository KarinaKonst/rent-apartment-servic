package com.example.rentapartment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@Table(name="total_rating")
@NoArgsConstructor
public class TotalRatingEntity {
    @Id
    @SequenceGenerator(name="total_ratingSequence", sequenceName="total_rating_sequence", allocationSize = 1,initialValue =1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="total_ratingSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "total_rating")
    private Integer totalRating;

    @OneToOne
    @JoinColumn(name="apartment_id")
    private ApartmentEntity apartmentEntity;

    public TotalRatingEntity(Integer totalRating, ApartmentEntity apartmentEntity) {
        this.totalRating = totalRating;
        this.apartmentEntity = apartmentEntity;
    }
}
