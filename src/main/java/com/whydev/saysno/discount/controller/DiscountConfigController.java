package com.whydev.saysno.discount.controller;

import com.whydev.saysno.discount.service.DiscountRateLoader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/config")
public class DiscountConfigController {

    private final DiscountRateLoader discountRateLoader;
    private static final Logger log = (Logger) LoggerFactory.getLogger(DiscountConfigController.class);

    @Autowired
    public DiscountConfigController(DiscountRateLoader discountRateLoader) {
        this.discountRateLoader = discountRateLoader;
    }

    @GetMapping("/reload-discounts")
    public ResponseEntity<String> reloadDiscountRates() {
        log.info("Reloading discount rates...");
        discountRateLoader.loadDiscountRates("classpath:discounts.yml");
        log.info("Discount rates reloaded successfully.");
        return ResponseEntity.ok("Discount rates reloaded successfully.");
    }
}
