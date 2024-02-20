package com.whydev.saysno.discount.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponseDto {
    private int id; // 제품 ID
    private double originPrice; // 원래 가격
    private double discountPrice; // 할인된 가격
}
