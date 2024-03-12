package com.whydev.saysno.discount.service.strategy;
import com.whydev.saysno.discount.model.Money;
public interface DayDiscountStrategy {
    Money applyDiscount(Money price);

}