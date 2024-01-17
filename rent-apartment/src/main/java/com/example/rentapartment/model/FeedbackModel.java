package com.example.rentapartment.model;

import com.example.rentapartment.dto.AddressDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedbackModel {

    private String message;
    private int rating;
    private Long id;


    public FeedbackModel(String message, int rating, Long id) {
        this.message = message;
        this.rating = rating;
        this.id = id;
    }
}
