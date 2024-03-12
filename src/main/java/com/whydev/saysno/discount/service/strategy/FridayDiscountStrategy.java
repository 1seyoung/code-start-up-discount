package com.whydev.saysno.discount.service.strategy;

import com.whydev.saysno.discount.model.Money;

import java.math.BigDecimal;

public class FridayDiscountStrategy implements DayDiscountStrategy {
    @Override
    public Money applyDiscount(Money price) {
        double fridayDiscountRate = 0.1; // 금요일 할인율: 10%
        return price.multiply(BigDecimal.valueOf(1 - fridayDiscountRate));
    }
}