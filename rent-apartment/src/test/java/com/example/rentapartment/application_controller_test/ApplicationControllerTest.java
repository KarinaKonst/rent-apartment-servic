package com.example.rentapartment.application_controller_test;

import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.integration_api.RestTemplateManagerService;
import com.example.rentapartment.model.FeedbackModel;
import com.example.rentapartment.repository.ApartmentRepository;
import com.example.rentapartment.service.IntegrationManager;
import com.example.rentapartment.service.RentApartmentService;
import com.example.rentapartment.service.ValidateUserToken;
import jakarta.ws.rs.core.MediaType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.rentapartment.constant.ConstantProject.*;
import static com.example.rentapartment.test_configuration.TestConfig.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @MockBean
    private RestTemplateManagerService restTemplateManagerService;
    @MockBean
    private ValidateUserToken validateUserToken;
    @MockBean
    private IntegrationManager integrationManager;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RentApartmentService rentApartmentService;

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

    @Test
    public void  choiceApartmentTest() throws Exception{
        doNothing().when(validateUserToken).checkValidateSession(any());
        doNothing().when(integrationManager).throwInfoOnRentProduct(1L);

        mockMvc.perform(MockMvcRequestBuilders.get(API+CHOICE_APARTMENT)
                        .header("auth_token","f5695729-2df7-47ff-ac22-859bd8c6e602|2033-12-14T21:42:36.472628800")
                        .param("id","1")
                        .param("start","2023-12-06")
                        .param("end","2023-12-07"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city")
                        .value("Пенза"));
        revertConditionDbApartmentTable(1L);

    }

    @Test
    public void getInfoByStreetTest() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(API+GET_INFO_BY_STREET)
                .param("street","Измайлова"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Результат поиска"));

    }
    @Test
    public void getInfoByPriceAndNumberOfRoomsTest() throws Exception{
        mockMvc.perform((MockMvcRequestBuilders.get(API+GET_APARTMENT_BY_PRICE_AND_NUMBER_OF_ROOMS)
                .param("price","2000"))
                .param("numberOfRooms","2"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Результат поиска"));
    }
    @Test
    public void feedbsckTest() throws Exception{
        FeedbackModel feedbackModel=getFeedbackModel();

        doNothing().when(validateUserToken).checkValidateSession(any());

        mockMvc.perform(MockMvcRequestBuilders.post(API+FEEDBACK)
                .header("auth_token","f5695729-2df7-47ff-ac22-859bd8c6e602|2033-12-14T21:42:36.472628800")
                .content(asJsonString(feedbackModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string(Matchers.notNullValue()));


    }
    private void revertConditionDbApartmentTable(Long apartmentId){
        ApartmentEntity apartmentEntity = apartmentRepository.findById(apartmentId).get();
        apartmentEntity.setAvailability(true);
        apartmentRepository.save(apartmentEntity);
    }
private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
