package com.whydev.saysno.discount.service.strategy;

public class ClothingDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double originPrice) {
        double discountedPrice = originPrice * 0.95;
        return Math.round(discountedPrice * 10) / 10.0; // 의류에 대한 5% 할인, 소수점 첫째 자리에서 반올림
    }
}