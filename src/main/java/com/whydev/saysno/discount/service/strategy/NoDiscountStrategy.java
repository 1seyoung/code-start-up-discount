package com.whydev.saysno.discount.service.strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double originPrice) {
        // 할인이 적용되지 않으므로 원가를 그대로 반환
        return originPrice;
    }
}
