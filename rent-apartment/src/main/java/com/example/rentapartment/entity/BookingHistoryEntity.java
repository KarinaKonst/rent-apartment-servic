package com.example.rentapartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "booking_history")
public class BookingHistoryEntity {
    @Id
    @SequenceGenerator(name = "booking_historySequence", sequenceName = "booking_history_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_historySequence")
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productId;
    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private ApartmentEntity apartmentId;

    @Column(name = "date_start")
    private LocalDate dateStart;
    @Column(name = "date_end")
    private LocalDate dateEnd;
    @Column(name = "date_registration_booking")
    private LocalDate dateRegistrationBooking;

    public void setDateRegistrationBooking(LocalDate dateRegistrationBooking) {
        this.dateRegistrationBooking = LocalDate.now();
    }


}
