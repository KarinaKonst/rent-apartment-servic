package com.example.rentapartment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@AllArgsConstructor
@NoArgsConstructor
public class GeacoderResponseDto {
    @JsonProperty(value = "results")
    private List<GeacoderListValue> resultList;

}
