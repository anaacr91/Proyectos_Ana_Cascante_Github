package com.Selenium_Java.dto;

public record ManufacturerWithProductDataDTO(
        Long manufacturerId,
        String manufacturerName,
        Long productsCount,
        Double productsSumTotalPrice
) {
}
