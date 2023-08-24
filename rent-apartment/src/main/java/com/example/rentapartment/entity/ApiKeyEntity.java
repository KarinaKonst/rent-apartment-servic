package com.example.rentapartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "api_key")
public class ApiKeyEntity {
    @Id
    @SequenceGenerator(name = "api_keySequence", sequenceName = "api_key_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_keySequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;
}
