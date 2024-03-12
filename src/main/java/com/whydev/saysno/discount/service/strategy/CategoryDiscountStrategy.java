package com.whydev.saysno.discount.service.strategy;

import com.whydev.saysno.discount.model.Money;

public interface CategoryDiscountStrategy {
    Money calculateDiscount(Money originPrice);
}