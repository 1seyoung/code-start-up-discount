package com.whydev.saysno.discount.model;

public class Category {
    public static final int CLOTHING = 10;
    public static final int ELECTRONICS = 20;
    public static final int FOOD = 30;

    public static double getDiscountRate(int category, double originalPrice) {
        switch (category) {
            case CLOTHING:
                return getClothingDiscountRate();
            case ELECTRONICS:
                return getElectronicsDiscountRate(originalPrice);
            case FOOD:
                return getFoodDiscountRate();
            default:
                return 0.0;
        }
    }

    private static double getClothingDiscountRate() {
        return 0.1; // 10% 할인
    }

    private static double getElectronicsDiscountRate(double originalPrice) {
        // 1만원 할인 또는 5% 할인 중에서 최대 할인
        double discountRate = originalPrice >= 10000 ? 10000 / originalPrice : 0.05;
        return Math.max(discountRate, 0.0);
    }

    private static double getFoodDiscountRate() {
        return 0.2; // 20% 할인
    }
}
