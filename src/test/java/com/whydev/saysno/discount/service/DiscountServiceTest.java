package com.whydev.saysno.discount.service;

import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.service.DiscountService;
import com.whydev.saysno.discount.service.strategy.ClothingDiscountStrategy;
import com.whydev.saysno.discount.service.strategy.DiscountStrategy;
import com.whydev.saysno.discount.service.strategy.ElectronicsDiscountStrategy;
import com.whydev.saysno.discount.service.strategy.FridayDiscountStrategy;
import com.whydev.saysno.discount.service.strategy.NoDiscountStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    private DiscountService discountService;
    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        // 금요일 설정
        LocalDate friday = LocalDate.of(2024, 2, 23);
        fixedClock = Clock.fixed(friday.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        discountService = new DiscountService(fixedClock);
    }

    @Test
    void calculateDiscount_FridayDiscount() {
        DiscountRequestDto requestDto = new DiscountRequestDto(new DiscountRequestDto.Product(1, 1000, 30)); // 카테고리 30은 할인 없음
        DiscountResponseDto responseDto = discountService.calculateDiscount(requestDto);

        // 금요일 10% 할인 확인
        assertEquals(900, responseDto.getDiscountPrice()); // 원가 1000에서 10% 할인
    }

    @Test
    void calculateDiscount_ClothingDiscountOnFriday() {
        DiscountRequestDto requestDto = new DiscountRequestDto(new DiscountRequestDto.Product(1, 1000, 10)); // 의류 카테고리
        DiscountResponseDto responseDto = discountService.calculateDiscount(requestDto);

        // 금요일 + 의류 5% 추가 할인 확인
        // 예상 할인가 계산 필요
        double expectedDiscountPrice = 1000 * 0.9 * 0.95; // 금요일 10% + 의류 5%
        assertEquals(expectedDiscountPrice, responseDto.getDiscountPrice());
    }

    @Test
    void calculateDiscount_ElectronicsDiscountOnFriday() {
        DiscountRequestDto requestDto = new DiscountRequestDto(new DiscountRequestDto.Product(1, 1000, 20)); // 전자기기 카테고리
        DiscountResponseDto responseDto = discountService.calculateDiscount(requestDto);

        // 전자기기 할인 (1만원 또는 5% 중 더 큰 할인) + 금요일 10% 할인 확인
        // 예상 할인가 계산 필요
        double expectedDiscountPrice = 1000 * 0.9 * 0.95; // 5% 할인이 더 크므로 이것과 금요일 할인을 더함
        assertEquals(expectedDiscountPrice, responseDto.getDiscountPrice());
    }

    @Test
    void calculateDiscount_NoDiscount() {
        // 금요일이 아닌 날짜로 설정
        LocalDate notFriday = LocalDate.of(2024, 2, 22);
        fixedClock = Clock.fixed(notFriday.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        discountService = new DiscountService(fixedClock);

        DiscountRequestDto requestDto = new DiscountRequestDto(new DiscountRequestDto.Product(1, 1000, 30)); // 할인 없는 카테고리
        DiscountResponseDto responseDto = discountService.calculateDiscount(requestDto);

        // 할인 없어야 함
        assertEquals(1000, responseDto.getDiscountPrice());
    }
}
