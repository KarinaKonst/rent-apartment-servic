package com.example.rentapartment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@AllArgsConstructor
@NoArgsConstructor
public class Components {

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "town")
    private String town;
}
