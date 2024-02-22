package com.whydev.saysno.discount.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiscountRequestDto {
    @Valid
    private Product product;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Product {
        @Min(1)
        private int id;

        @Positive
        @JsonProperty("orign_price") // JSON 필드명과 자바 필드명 매핑
        private double originPrice;

        @Min(10) // 카테고리 최솟값 10
        private int category;
    }
}
