package com.whydev.saysno.discount.repo;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiscountPolicyRepo {
    public List<String> getDiscountPolices() {
        return List.of(
                "name-policy1-rate-1000",
                "category-policy2",
                "date-policy1-rate-1000"
        );
    }
}
