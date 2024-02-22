package com.whydev.saysno.discount.service.strategy;

import com.whydev.saysno.discount.dto.request.DiscountRequestDto;

public class ClothingDiscountStrategy implements DiscountStrategy {
    @Override
    public boolean isSatisfied(DiscountRequestDto discountRequestDto) {
        if(discountRequestDto.getProduct().getCategory() == 10){
            return true;
        }
        return false;
    }

    @Override
    public double calculateDiscount(double originPrice) {
        double discountedPrice = originPrice * 0.95;
        return Math.round(discountedPrice * 10) / 10.0; // 의류에 대한 5% 할인, 소수점 첫째 자리에서 반올림
    }
}