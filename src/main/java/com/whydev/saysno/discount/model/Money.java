package com.whydev.saysno.discount.model;

import java.math.BigDecimal;

public class Money {

    public static final Money ZERO = new Money(BigDecimal.ZERO);
    private final BigDecimal value;

    public Money(BigDecimal value) {
        this.value = value;
    }

    public Money multiply(double scalar) {
        BigDecimal resultValue = this.value.multiply(BigDecimal.valueOf(scalar));
        return new Money(resultValue);
    }

    public Money subtract(Money other) {
        BigDecimal resultValue = this.value.subtract(other.getValue());
        return new Money(resultValue);
    }

    public BigDecimal getValue() {
        return value;
    }
}
