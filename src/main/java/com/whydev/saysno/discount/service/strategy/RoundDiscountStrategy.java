package com.whydev.saysno.discount.service.strategy;


public class RoundDiscountStrategy implements DiscountStrategy {
    private static final double FRIDAY_DISCOUNT_RATE = 0.9;

    @Override
    public double calculateDiscount(double originPrice) {
        double discountedPrice = originPrice * FRIDAY_DISCOUNT_RATE; // 금요일 10% 할인
        return Math.round(discountedPrice * 10) / 10.0; // 소수점 첫째 자리에서 반올림
    }
}

