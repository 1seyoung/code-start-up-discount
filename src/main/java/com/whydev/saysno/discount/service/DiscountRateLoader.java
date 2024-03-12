package com.whydev.saysno.discount.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class DiscountRateLoader {
    private Map<String, Integer> discountRates = new HashMap<>();

    @Value("${discounts.config-path}")
    private String configPath;

    @PostConstruct
    public void init() {
        loadDiscountRates(configPath);  // 설정 파일에서 로드한 경로를 사용
    }
    public void loadDiscountRates(String resourcePath) {
        Yaml yaml = new Yaml();
        try (InputStream in = new ClassPathResource(resourcePath).getInputStream()) {
            Map<String, Integer> newDiscountRates = new HashMap<>();
            Map<String, Object> yamlData = yaml.load(in);

            if (yamlData != null) {
                Object discountsObject = yamlData.get("discounts");
                if (discountsObject instanceof Map<?, ?> discounts) {
                    for (Map.Entry<?, ?> entry : discounts.entrySet()) {
                        if (entry.getKey() instanceof String key && entry.getValue() instanceof Number value) {
                            newDiscountRates.put(key, value.intValue()); // 소수점을 고려하지 않은 캐스팅. 필요에 따라 조정이 필요함.
                        } else {
                            throw new IllegalArgumentException("Invalid type for discount rates key or value");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Invalid type for discount rates, expected Map<?, ?>");
                }
            }

            this.discountRates = newDiscountRates;
        } catch (Exception e) {
            System.err.println("Error reading the discount rates file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public int getDiscountRate(String category) {
        return discountRates.getOrDefault(category.toUpperCase(), 0);
    }
}

// Compare this snippet from src/main/resources/discounts.yml: