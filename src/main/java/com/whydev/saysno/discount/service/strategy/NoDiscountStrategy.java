package com.whydev.saysno.discount.service.strategy;

import com.whydev.saysno.discount.model.Money;

public class NoDiscountStrategy implements CategoryDiscountStrategy {
    @Override
    public Money calculateDiscount(Money originPrice) {
        return originPrice; // 할인 적용 안 함
    }
}