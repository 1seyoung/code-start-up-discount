package com.whydev.saysno.discount.service;

import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.service.strategy.*;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
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
        if (isFriday()) {
            discountPrice = fridayDiscountStrategy.calculateDiscount(discountPrice);
            log.info("Applied Friday discount: {}", discountPrice);
        }

        DiscountStrategy categoryStrategy = discountStrategies.getOrDefault(product.getCategory(), new NoDiscountStrategy());
        discountPrice = categoryStrategy.calculateDiscount(discountPrice);

        log.info("Final discounted price: {}", discountPrice);

        return new DiscountResponseDto(product.getId(), originPrice, discountPrice);
    }

    private boolean isFriday() {
        return LocalDate.now(clock).getDayOfWeek() == DayOfWeek.FRIDAY;
    }
}
