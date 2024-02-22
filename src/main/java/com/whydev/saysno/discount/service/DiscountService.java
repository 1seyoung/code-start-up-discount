package com.whydev.saysno.discount.service;

import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.service.strategy.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountService.class);
    private final Map<Integer, DiscountStrategy> discountStrategies;
    private final Clock clock;
    private final DiscountStrategy fridayDiscountStrategy;

    public DiscountService(Clock clock) {
        this.clock = clock;
        this.fridayDiscountStrategy = new FridayDiscountStrategy();
        discountStrategies = new HashMap<>();
        discountStrategies.put(10, new ClothingDiscountStrategy());
        discountStrategies.put(20, new ElectronicsDiscountStrategy());
    }

    public DiscountResponseDto calculateDiscount(DiscountRequestDto requestDto) {

        DiscountRequestDto.Product product = requestDto.getProduct();
        double originPrice = product.getOriginPrice();

        log.info("Calculating discount for product id: {}, category: {}, original price: {}", product.getId(), product.getCategory(), originPrice);

        double discountPrice = originPrice;

        // 비즈니스 로직
        if (isFriday()) {
            discountPrice = fridayDiscountStrategy.calculateDiscount(discountPrice);
            log.info("Applied Friday discount: {}", discountPrice);
        }

        List<DiscountStrategy> discountStrategies = new ArrayList<>();
        // OCP open-closed principle
        double newDiscountPrice = 0;
        for (DiscountStrategy strategy : discountStrategies) {
            // SRP single responsibility principle
            if (strategy.isSatisfied(requestDto)) {
                newDiscountPrice += strategy.calculateDiscount(newDiscountPrice);
                log.info("Applied discount strategy: {}", strategy.getClass().getSimpleName());
            }
        }

        return new DiscountResponseDto(product.getId(), originPrice, discountPrice);
    }

    private boolean isFriday() {
        return LocalDate.now(clock).getDayOfWeek() == DayOfWeek.FRIDAY;
    }
}
