package com.whydev.saysno.discount.service.strategy;

public class ElectronicsDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double originPrice) {

        double percentDiscount = originPrice * 0.95; // 5% 할인
        double amountDiscount = originPrice - 10000; // 10,000원 할인
        double maxDiscount = Math.max(percentDiscount, amountDiscount); // 더 큰 할인 적용
        return Math.round(maxDiscount * 10) / 10.0; // 소수점 첫째 자리에서 반올림
    }
}
