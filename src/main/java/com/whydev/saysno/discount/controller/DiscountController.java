package com.whydev.saysno.discount.controller;

import com.whydev.saysno.discount.service.DiscountRateLoader;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.whydev.saysno.discount.dto.request.DiscountRequestDto;
import com.whydev.saysno.discount.dto.response.DiscountResponseDto;
import com.whydev.saysno.discount.service.DiscountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 할인 계산을 위한 REST 컨트롤러.
 */
@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
    private static final Logger log = LoggerFactory.getLogger(DiscountController.class);

    private final DiscountService discountService;
//    private final DiscountRateLoader discountRateLoader; // DiscountRateLoader 인스턴스를 추가합니다.

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
//        this.discountRateLoader = discountRateLoader; // 생성자를 통해 DiscountRateLoader 주입
    }

    @PostMapping("/calculate")
    public ResponseEntity<DiscountResponseDto> calculateDiscount(@Valid @RequestBody DiscountRequestDto requestDto) {
        log.info("Received discount calculation request: {}", requestDto);
        try {
            DiscountResponseDto responseDto = discountService.calculateDiscount(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("Error calculating discount", e);
            // 예외 상황에 대한 기본적인 처리. 세부적인 예외 유형에 따라 적절한 HTTP 상태 코드 반환 필요.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
