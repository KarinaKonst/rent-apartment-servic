package com.example.interiorclient.application_controller_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.interiorclient.constant.Constant.SEND_PRODUCT;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void sendProductTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post(SEND_PRODUCT)
                .param("id","1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Успешно отправлено"));
    }
//    @PostMapping(SEND_PRODUCT)
//    public void productSend(@RequestParam Long id) {
//        productService.throwInfoToProductService(id);
//    }
//}


}
