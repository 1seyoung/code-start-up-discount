package com.whydev.saysno.discount.model;

public class Category {
    public static final int CLOTHING = 10;
    public static final int ELECTRONICS = 20;
    public static final int FOOD = 30;

    // 숫자 카테고리 값을 받아서 해당하는 문자열 키를 반환합니다.
    public static String getCategoryKey(int categoryValue) {
        switch (categoryValue) {
            case CLOTHING:
                return "CLOTHING";
            case ELECTRONICS:
                return "ELECTRONICS";
            case FOOD:
                return "FOOD";
            default:
                throw new IllegalArgumentException("Unknown category value: " + categoryValue);
        }
    }
}
