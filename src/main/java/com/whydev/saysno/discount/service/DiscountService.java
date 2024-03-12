package com.whydev.saysno.discount.service;

import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.model.Category;
import com.whydev.saysno.discount.model.Money;

import com.whydev.saysno.discount.service.strategy.CategoryDiscountStrategy;
import com.whydev.saysno.discount.service.strategy.FridayDiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountService.class);
    private final Clock clock;
    private final Map<String, CategoryDiscountStrategy> discountStrategies;
    private final FridayDiscountStrategy fridayDiscountStrategy;
    private final DiscountRateLoader discountRateLoader;



    // 생성자에서 각 카테고리 할인 전략을 주입받습니다.
    @Autowired
    public DiscountService(Clock clock, FridayDiscountStrategy fridayDiscountStrategy, DiscountRateLoader discountRateLoader, List<CategoryDiscountStrategy> strategies) {
        this.clock = clock;
        this.fridayDiscountStrategy = fridayDiscountStrategy;
        this.discountRateLoader = discountRateLoader;
        this.discountStrategies = strategies.stream()
                .collect(Collectors.toMap(strategy -> strategy.getClass().getSimpleName(), Function.identity()));
    }

    public DiscountResponseDto calculateDiscount(DiscountRequestDto requestDto) {
        // Validation Task
        List<String> validationErrors = requestDto.validate();
        if (!validationErrors.isEmpty()) {
            log.error("Validation errors: {}", validationErrors);
            return new DiscountResponseDto(-1, Money.ZERO, Money.ZERO, String.join(", ", validationErrors));
        }

        DiscountRequestDto.Product product = requestDto.getProduct();
        Money originPrice = new Money(BigDecimal.valueOf(product.getOriginPrice()));

        log.info("Calculating discount for product id: {}, category: {}, original price: {}", product.getId(), product.getCategory(), originPrice);

        Money discountPrice = applyDayDiscount(originPrice);
        discountPrice = applyCategoryDiscount(discountPrice, product.getCategory());

        return new DiscountResponseDto(product.getId(), originPrice, discountPrice, null);
    }


    private Money applyDayDiscount(Money price) {
        if (isFriday()) {
            return fridayDiscountStrategy.applyDiscount(price);
        }
        return price;
    }

    private Money applyCategoryDiscount(Money price, int category) {
        String categoryKey = Category.getCategoryKey(category);
        CategoryDiscountStrategy strategy = discountStrategies.get(categoryKey);
        if (strategy != null) {
            return strategy.calculateDiscount(price);
        }
        return price;
    }


    private boolean isFriday() {
        return LocalDate.now(clock).getDayOfWeek() == DayOfWeek.FRIDAY;
    }
}