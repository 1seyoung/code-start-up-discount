package com.whydev.saysno.discount.model;

import java.math.BigDecimal;


public record Money(BigDecimal value) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.value.multiply(multiplier));
    }

    public Money subtract(Money other) {
        BigDecimal resultValue = this.value.subtract(other.value());
        return new Money(resultValue);
    }

    public int compareTo(Money other) {
        return this.value.compareTo(other.value);
    }
}