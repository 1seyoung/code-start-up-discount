package com.whydev.saysno.discount.model;

public class Category {
    public static final int CLOTHING = 10;
    public static final int ELECTRONICS = 20;
    public static final int FOOD = 30;

    public static double getDiscountRate(int category) {
        return switch (category) {
            case CLOTHING -> 0.1; // 10%
            case ELECTRONICS -> 0.05; // 5%
            case FOOD -> 0.2; // 20%
            default -> 0.0; // 할인 없음
        };
    }
}
