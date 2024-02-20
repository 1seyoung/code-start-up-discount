package com.whydev.saysno.discount.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DiscountController.class)
class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountService discountService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        DiscountResponseDto mockResponse = new DiscountResponseDto(1, 1000.0, 900.0);
        when(discountService.calculateDiscount(any())).thenReturn(mockResponse);
    }
    @Test
    void calculateDiscount() throws Exception {
        DiscountRequestDto requestDto = new DiscountRequestDto(new DiscountRequestDto.Product(1, 1000.0, 10));

        mockMvc.perform(post("/api/discounts/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.originPrice").value(1000.0))
                .andExpect(jsonPath("$.discountPrice").value(900.0));
    }
}
