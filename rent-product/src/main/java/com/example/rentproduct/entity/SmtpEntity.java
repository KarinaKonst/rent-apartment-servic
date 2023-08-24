package com.example.rentproduct.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="smtp")
public class SmtpEntity {
    @Id
    @SequenceGenerator(name = "smtpSequence", sequenceName = "smtp_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "smtpSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "host")
    private String host;
    @Column(name = "port")
    private int port;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;

}
