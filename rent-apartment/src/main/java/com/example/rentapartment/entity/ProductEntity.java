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
@Table(name = "promotion_product")
public class ProductEntity {
    @Id
    @SequenceGenerator(name = "promotion_productSequence", sequenceName = "promotion_product_sequence", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_productSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "description")
    private String description;
    @Column(name = "discount")
    private Integer discount;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productId")
    private List<BookingHistoryEntity> bookingHistoryList;
}
