package com.whydev.saysno.discount.service.strategy;

import com.whydev.saysno.discount.dto.request.DiscountRequestDto;

public interface DiscountStrategy {

    boolean isSatisfied(DiscountRequestDto discountRequestDto);

    double calculateDiscount(double originPrice);
}
