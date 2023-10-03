package com.example.rentapartment.application_controller_test;

import com.example.rentapartment.dto.GeacoderResponseDto;
import com.example.rentapartment.integration_api.RestTemplateManagerService;
import com.example.rentapartment.response_object.ResponseObjectList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.rentapartment.constant.ConstantProject.API;
import static com.example.rentapartment.test_configuration.TestConfig.*;
import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.rentapartment.constant.ConstantProject.GET_INFO_BY_CITY;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @MockBean
    private RestTemplateManagerService restTemplateManagerService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findApartmentByLocationTest() throws Exception {
        when(restTemplateManagerService.searchCity(LAT, LON)).thenReturn(prepaireGeacoderObject());

        mockMvc.perform(MockMvcRequestBuilders.get(API+GET_INFO_BY_CITY)
                .param(LAT_NAME,LAT)
                .param(LON_NAME,LON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Лист доступных апартаментов"));

    }


//    @GetMapping(GET_INFO_BY_CITY)
//    public ResponseObjectList getInfoByCity(@RequestParam String latitude, @RequestParam String longitude) {
//
//        return rentApartmentService.getInfoByCity(latitude, longitude);
//    }

}
