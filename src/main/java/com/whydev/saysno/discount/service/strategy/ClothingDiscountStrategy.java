package com.whydev.saysno.discount.service.strategy;

import com.whydev.saysno.discount.model.Money;
import com.whydev.saysno.discount.service.DiscountRateLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ClothingDiscountStrategy implements CategoryDiscountStrategy {

    private final DiscountRateLoader discountRateLoader;

    @Autowired
    public ClothingDiscountStrategy(DiscountRateLoader discountRateLoader) {
        this.discountRateLoader = discountRateLoader;
    }

    @Override
    public Money calculateDiscount(Money originPrice) {
        int discountPercent = discountRateLoader.getDiscountRate("CLOTHING");
        BigDecimal discountRate = BigDecimal.valueOf(discountPercent).divide(new BigDecimal("100"));
        return originPrice.multiply(discountRate);
    }
}
