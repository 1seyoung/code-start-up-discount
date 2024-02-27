package com.whydev.saysno.discount.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.whydev.saysno.discount.model.Money;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponseDto {
    private int id; // 제품 ID
    private Money originPrice; // 원래 가격
    private Money discountPrice; // 할인된 가격
    private String validationErrors;
}
