package com.whydev.saysno.discount.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
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
    //@NotNull
    private Product product;
    public List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (product != null) {
            errors.addAll(product.validate());
        } else {
            errors.add("Product 정보가 필요합니다.");
        }

        return errors;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Product {
        private int id;
        @JsonProperty("orign_price") // JSON 필드명과 자바 필드명 매핑
        private double originPrice;
        private int category;

        public List<String> validate() {
            List<String> errors = new ArrayList<>();

            if (id < 1) {
                errors.add("[ID ERROR]Product ID는 1 이상이어야 함 .");
            }
            if (originPrice <= 0) {
                errors.add("[OriginPrice ERROR]Product 원가는 양수");
            }
            if (category < 10 || category % 10 != 0) {
                errors.add("[CATEGORY ERROR]Product 카테고리는 10 이상이며 10 단위.");
            }

            return errors;
        }
    }
}
