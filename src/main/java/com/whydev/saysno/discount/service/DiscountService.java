package com.whydev.saysno.discount.service;

import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.model.Category;
import com.whydev.saysno.discount.model.Money;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountService.class);

    private final Clock clock;

    public DiscountService(Clock clock) {
        this.clock = clock;
    }

    public DiscountResponseDto calculateDiscount(DiscountRequestDto requestDto) {
        //Validation Task
        List<String> validationErrors = requestDto.validate();
        if (!validationErrors.isEmpty()) {
            //Error
            log.error("Validation errors: {}", validationErrors);

            return new DiscountResponseDto(-1, Money.ZERO, Money.ZERO, String.join(", ", validationErrors));
        }
        DiscountRequestDto.Product product = requestDto.getProduct();
        Money originPrice = new Money(BigDecimal.valueOf(product.getOriginPrice()));

        log.info("Calculating discount for product id: {}, category: {}, original price: {}", product.getId(), product.getCategory(), originPrice);

        Money discountPrice = applyFridayDiscount(originPrice);
        discountPrice = applyCategoryDiscount(discountPrice, product.getCategory());

        return new DiscountResponseDto(product.getId(), originPrice, discountPrice, null);
    }

    private Money applyFridayDiscount(Money price) {
        if (isFriday()) {
            // 금요일 할인 적용
            double fridayDiscountRate = 0.1; // 금요일 할인율: 10%
            Money fridayDiscountAmount = price.multiply(fridayDiscountRate);
            return price.subtract(fridayDiscountAmount);
        }
        return price;
    }

    private Money applyCategoryDiscount(Money price, int category) {
        // 카테고리별 할인 적용
        double categoryDiscountRate = Category.getDiscountRate(category);
        Money categoryDiscountAmount = price.multiply(categoryDiscountRate);
        return price.subtract(categoryDiscountAmount);
    }

    private boolean isFriday() {
        return LocalDate.now(clock).getDayOfWeek() == DayOfWeek.FRIDAY;
    }
}
