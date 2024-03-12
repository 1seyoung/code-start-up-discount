package com.whydev.saysno.discount.service.strategy;
import com.whydev.saysno.discount.model.Money;
import com.whydev.saysno.discount.service.DiscountRateLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ElectronicsDiscountStrategy implements CategoryDiscountStrategy {
    private final DiscountRateLoader discountRateLoader;
    @Autowired
    public ElectronicsDiscountStrategy(DiscountRateLoader discountRateLoader) {
        this.discountRateLoader = discountRateLoader;
    }

    @Override
    public Money calculateDiscount(Money originPrice) {
        int discountPercent = discountRateLoader.getDiscountRate("ELECTRONICS");
        BigDecimal discountRate = BigDecimal.valueOf(discountPercent).divide(new BigDecimal("100"));

        Money percentDiscount = originPrice.multiply(discountRate); // 비율 할인
        Money amountDiscount = originPrice.subtract(new Money(new BigDecimal("10000"))); // 금액 할인

        // 비율 할인과 금액 할인 중 더 큰 할인을 적용합니다.
        return percentDiscount.compareTo(amountDiscount) > 0 ? percentDiscount : amountDiscount;
    }
}
